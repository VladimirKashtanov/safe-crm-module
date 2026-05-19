package org.voldymar.safecrmmodule.feature.shared.dto;

import java.util.Map;


/**
 * Описывает DTO субъекта, который запрашивает доступ.
 * @param userId идентификатор субъекта доступа.
 * @param attributes атрибуты, влияющие на предоставление доступа.
 */
public record AccessSubject(

        String userId,
        Map<String, String> attributes
) {
}
