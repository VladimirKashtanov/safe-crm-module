package org.voldymar.safecrmmodule.feature.manage.dto;

/**
 * Описывает DTO запроса политик доступа.
 *
 * @param name           имя политики
 * @param description    описание политики.
 * @param ruleExpression правило политики.
 * @param effect         эффект от применения политики (запретить/разрешить).
 * @param createdAt      время создания политики.
 */
public record PolicyRequest(

        String name,
        String description,
        String ruleExpression,
        String effect
) {
}
