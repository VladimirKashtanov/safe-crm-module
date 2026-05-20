package org.voldymar.safecrmmodule.feature.manage.dto;

import java.util.Set;


/**
 * Описывает DTO ответа роли пользователя.
 * @param id идентификатор роли пользователя.
 * @param name имя роли пользователя.
 * @param description описание роли пользователя.
 * @param parentRoleId идентификатор родительской роли (при наличии).
 * @param permissionIds идентификаторы разрешений.
 * @param createdAt Время создания сущности.
 */
public record UserRoleResponse(

        String id,
        String name,
        String description,
        String parentRoleId,
        Set<String> permissionIds,
        String createdAt
) {
}
