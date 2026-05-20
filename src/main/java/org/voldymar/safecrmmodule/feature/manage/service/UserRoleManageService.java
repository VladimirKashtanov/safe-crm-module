package org.voldymar.safecrmmodule.feature.manage.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleRequest;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleResponse;
import org.voldymar.safecrmmodule.feature.shared.db.model.PermissionEntity;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserRoleEntity;
import org.voldymar.safecrmmodule.feature.shared.service.PermissionService;
import org.voldymar.safecrmmodule.feature.shared.service.UserRoleService;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * Описывает сервис, который управляет ролями и связями
 * между ролями и разрешениями.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class UserRoleManageService {

    /**
     * Сервис управления ролями пользователя.
     */
    private final UserRoleService userRoleService;

    /**
     * Сервис управления разрешениями.
     */
    private final PermissionService permissionService;


    /**
     * Создает новую роль пользователя.
     *
     * @param request запрос.
     * @return DTO сгенерированной роли пользователя.
     */
    public UserRoleResponse createOne(
            UserRoleRequest request
    ) {
        /* Проверка на существование роли */
        if (this.userRoleService.existsByName(request.name())) {
            throw new IllegalArgumentException(
                    "Роль с именем '" + request.name() + "' уже существует"
            );
        }

        /* Создание роли */
        UserRoleEntity.UserRoleEntityBuilder builder = UserRoleEntity.builder()
                .name(request.name())
                .description(request.description());

        /* Назначение родительской роли (если указана) */
        if (request.parentRoleId() != null) {
            UserRoleEntity parentRole = this.userRoleService.findById(
                            UUID.fromString(request.parentRoleId())
                    )
                    .orElseThrow(() -> new IllegalArgumentException(
                                    "Родительская роль не найдена: id=" +
                                            request.parentRoleId()
                            )
                    );
            builder.parentRole(parentRole);
        }
        UserRoleEntity role = builder.build();

        /* Сохранение роли */
        role = this.userRoleService.save(role);

        /* Назначение разрешений (если указаны) */
        if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
            Set<PermissionEntity> permissions = new HashSet<>();
            for (String permissionId : request.permissionIds()) {
                PermissionEntity permission = this.permissionService.findById(
                                UUID.fromString(permissionId)
                        )
                        .orElseThrow(() -> new IllegalArgumentException(
                                        "Разрешение не найдено: id=" + permissionId
                                )
                        );
                permissions.add(permission);
            }
            role.setPermissions(permissions);

            /* Сохранение изменений */
            role = this.userRoleService.save(role);
        }

        /* Формирование ответа */
        return new UserRoleResponse(
                role.getId().toString(),
                role.getName(),
                role.getDescription(),
                role.getParentRole() != null ?
                        role.getParentRole().getId().toString() : null,
                role.getPermissions().stream()
                        .map(p -> p.getId().toString())
                        .collect(Collectors.toSet()),
                role.getCreatedAt() != null ?
                        role.getCreatedAt().toString() : null
        );
    }
}
