package org.voldymar.safecrmmodule.feature.manage.dto;

/**
 * Описывает DTO ответа политики доступа.
 * @param id идентификатор политики.
 * @param name имя политики.
 * @param description описание политики.
 * @param ruleExpression правило политики.
 * @param effect эффект от применения политики (разрешить/запретить).
 * @param createdAt время создания политики.
 */
public record PolicyResponse(

        String id,
        String name,
        String description,
        String ruleExpression,
        String effect,
        String createdAt
) {
}
