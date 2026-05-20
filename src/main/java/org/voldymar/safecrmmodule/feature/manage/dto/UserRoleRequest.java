package org.voldymar.safecrmmodule.feature.manage.dto;

import java.util.Set;


/**
 * Описывает DTO запроса роли пользователя.
 * @param name имя роли пользователя.
 * @param description описание роли пользователя.
 * @param parentRoleId идентификатор родительской роли (при наличии).
 * @param permissionIds идентификаторы разрешений.
 */
public record UserRoleRequest(

        String name,
        String description,
        String parentRoleId,
        Set<String> permissionIds
) {
}
