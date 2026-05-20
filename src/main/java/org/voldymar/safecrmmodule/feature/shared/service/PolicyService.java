package org.voldymar.safecrmmodule.feature.shared.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.access.dto.AccessType;
import org.voldymar.safecrmmodule.feature.manage.dto.PolicyRequest;
import org.voldymar.safecrmmodule.feature.manage.dto.PolicyResponse;
import org.voldymar.safecrmmodule.feature.shared.db.model.PolicyEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.PolicyRepository;

import java.util.List;


/**
 * Описывает сервис управления политиками доступа.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PolicyService {

    /**
     * Репозиторий управления политиками доступа.
     */
    private final PolicyRepository policyRepository;


    /**
     * Находит все включенные политики с определенным эффектом.
     *
     * @param effect эффект политики (разрешить/запретить).
     * @return список включенных политик с указанным эффектом.
     */
    public List<PolicyEntity> findAllByEnabledTrueAndEffect(
            String effect
    ) {
        return this.policyRepository.findAllByEnabledTrueAndEffect(
                effect
        );
    }


    /**
     * Создает политику доступа.
     *
     * @param request запрос.
     * @return созданную политику.
     */
    public PolicyResponse createOne(
            PolicyRequest request
    ) {
        /* Проверка политики на существование */
        if (policyRepository.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Политика с таким именем уже существует: name=" + request.name()
            );
        }

        /* Проверка корректности эффекта политики */
        if (!AccessType.PERMITTED.name().equalsIgnoreCase(request.effect()) &&
                !AccessType.DENIED.name().equalsIgnoreCase(request.effect())) {
            throw new IllegalArgumentException(
                    "Эффект политики должен быть PERMITTED или DENIED"
            );
        }

        /* Создание политики */
        var policy = PolicyEntity.builder()
                .name(request.name())
                .description(request.description())
                .ruleExpression(request.ruleExpression())
                .effect(request.effect())
                .enabled(true)
                .build();

        /* Сохранение политики */
        policy = this.policyRepository.save(policy);

        /* Формирование ответа */
        return new PolicyResponse(
                policy.getId().toString(),
                policy.getName(),
                policy.getDescription(),
                policy.getRuleExpression(),
                policy.getEffect(),
                policy.getCreatedAt() != null ?
                        policy.getCreatedAt().toString() : null
        );
    }
}
