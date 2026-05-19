package org.voldymar.safecrmmodule.feature.access.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.access.dto.AccessRequest;
import org.voldymar.safecrmmodule.feature.access.dto.AccessType;
import org.voldymar.safecrmmodule.feature.shared.db.model.PermissionEntity;
import org.voldymar.safecrmmodule.feature.shared.db.model.PolicyEntity;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserEntity;
import org.voldymar.safecrmmodule.feature.shared.service.PermissionService;
import org.voldymar.safecrmmodule.feature.shared.service.PolicyService;
import org.voldymar.safecrmmodule.feature.shared.service.UserService;

import java.util.*;


/**
 * Описывает сервис проверки доступа субъекта к объекту.
 */
@Service
@RequiredArgsConstructor
public class AccessService {

    /**
     * Сервис управления пользователями.
     */
    private final UserService userService;

    /**
     * Сервис управления ограничениями пользователя.
     */
    private final PermissionService permissionService;

    /**
     * Сервис управления политиками доступа.
     */
    private final PolicyService policyService;

    /**
     * Сервис применения политик доступа.
     */
    private final PolicyEvaluationService policyEvaluationService;


    /**
     * Проверяет доступ субъекта к объекту.
     *
     * @param request запрос.
     * @return Map с элементами 'decision' и 'reason'.
     */
    public Map<String, String> checkAccess(
            AccessRequest request
    ) {
        var result = new HashMap<String, String>();

        /* Поиск пользователя */
        UserEntity user = this.findUser(request, result);
        if (user == null) {
            return result;
        }

        /* Проверка наличия RBAC-ограничения */
        boolean hasRbacPermission = this.checkRbacPermission(request, user);

        /* Сбор атрибутов для ABAC-анализа */
        var attributes = this.collectAttributes(request, user);

        if (!attributes.get("subject").isEmpty() ||
                !attributes.get("context").isEmpty()) {
            /* ABAC-анализ */
            if (hasRbacPermission) {
                return handleRbacPermitted(attributes, result);
            } else {
                return handleRbacDenied(attributes, result);
            }
        } else if (hasRbacPermission) {
            result.put("decision", AccessType.PERMITTED.name());
            result.put("reason", "Доступ разрешен");
            return result;
        } else {
            result.put("decision", AccessType.DENIED.name());
            result.put("reason", "Доступ запрещен RBAC-моделью");
            return result;

        }
    }


    /**
     * Ищет пользователя согласно параметрам запроса.
     *
     * @param request запрос.
     * @param result  Map для заполнения (в случае, если пользователь не найден).
     * @return объект UserEntity.
     */
    private UserEntity findUser(
            AccessRequest request,
            Map<String, String> result
    ) {
        UUID userId = UUID.fromString(request.subject().userId());
        Optional<UserEntity> userOpt = this.userService.getUserById(userId);

        if (userOpt.isEmpty()) {
            result.put("decision", AccessType.DENIED.name());
            result.put("reason", "Пользователь не найден: id='" + userId + "'");
            return null;
        }
        return userOpt.get();
    }


    /**
     * Проверяет наличие RBAC-ограничений для пользователя.
     *
     * @param request запрос.
     * @param user    пользователь.
     * @return подтверждение наличия/отсутствия RBAC-ограничений.
     */
    private boolean checkRbacPermission(
            AccessRequest request,
            UserEntity user
    ) {
        String objectType = request.object().type();
        String operation = request.operation();

        Set<PermissionEntity> userPermissions =
                this.permissionService.findAllByUserId(user.getId());

        return userPermissions.stream().anyMatch(p ->
                p.getObjectType().equalsIgnoreCase(objectType) &&
                        p.getOperation().equalsIgnoreCase(operation)
        );
    }


    /**
     * Сопоставляет разрешение RBAC-модели с ABAC-моделью.
     *
     * @param attributes атрибуты пользователя.
     * @param result     Map с элементами 'decision' и 'reason'.
     * @return Map с элементами 'decision' и 'reason'.
     */
    private Map<String, String> handleRbacPermitted(
            Map<String, Map<String, String>> attributes,
            Map<String, String> result
    ) {
        /* Поиск запрещающих политик */
        List<PolicyEntity> denyPolicies =
                this.policyService.findAllByEnabledTrueAndEffect(
                        AccessType.DENIED.name()
                );

        /* Если есть хотя бы одна запрещающая политика */
        for (PolicyEntity policy : denyPolicies) {
            if (this.policyEvaluationService.evaluate(
                    policy.getRuleExpression(),
                    attributes)
            ) {
                result.put("decision", AccessType.DENIED.name());
                result.put(
                        "reason",
                        "Доступ запрещен политикой: name='" + policy.getName() + "'"
                );
                return result;
            }
        }

        /* Если ни одна политика не запретила доступ */
        result.put("decision", AccessType.PERMITTED.name());
        result.put("reason", "Доступ разрешен");
        return result;
    }


    /**
     * Сопоставляет запрет RBAC-модели с ABAC-моделью.
     *
     * @param attributes атрибуты пользователя.
     * @param result     Map с элементами 'decision' и 'reason'.
     * @return Map с элементами 'decision' и 'reason'.
     */
    private Map<String, String> handleRbacDenied(
            Map<String, Map<String, String>> attributes,
            Map<String, String> result
    ) {
        /* Поиск разрешающих политик */
        List<PolicyEntity> permitPolicies =
                this.policyService.findAllByEnabledTrueAndEffect(
                        AccessType.PERMITTED.name()
                );

        /* Если есть хотя бы одна разрешающая политика */
        for (PolicyEntity policy : permitPolicies) {
            if (this.policyEvaluationService.evaluate(
                    policy.getRuleExpression(),
                    attributes
            )) {
                result.put("decision", AccessType.PERMITTED.name());
                result.put(
                        "reason",
                        "Доступ разрешен политикой: name='" + policy.getName() + "'"
                );
                return result;
            }
        }

        /* Если ни одна политика не запретила доступ */
        result.put("decision", AccessType.DENIED.name());
        result.put("reason", "Нет прав доступа");
        return result;
    }


    /**
     * Собирает атрибуты пользователя, объекта, операции и контекста.
     *
     * @param request запрос.
     * @param user    пользователь.
     * @return набор атрибутов.
     */
    private Map<String, Map<String, String>> collectAttributes(
            AccessRequest request,
            UserEntity user
    ) {
        Map<String, Map<String, String>> attributes = new HashMap<>();

        /* Сбор атрибутов субъекта */
        Map<String, String> subjectAttributes = new HashMap<>();
        subjectAttributes.put("userId", user.getId().toString());
        if (request.subject() != null) {
            subjectAttributes.putAll(request.subject().attributes());
        }
        attributes.put("subject", subjectAttributes);

        /* Сбор атрибутов контекста */
        Map<String, String> contextAttributes = new HashMap<>();
        if (request.context() != null) {
            contextAttributes.putAll(request.context());
        }
        attributes.put("context", contextAttributes);

        return attributes;
    }
}
