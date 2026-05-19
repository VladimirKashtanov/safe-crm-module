package org.voldymar.safecrmmodule.feature.shared.dto;

import java.util.Map;


/**
 * Описывает субъект, который запрашивает доступ.
 * @param userId Идентификатор субъекта доступа.
 * @param attributes атрибуты, влияющие на предоставление доступа.
 */
public record AccessSubject(

        String userId,
        Map<String, String> attributes
) {
}
