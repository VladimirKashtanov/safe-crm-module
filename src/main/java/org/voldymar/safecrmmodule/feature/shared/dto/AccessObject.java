package org.voldymar.safecrmmodule.feature.shared.dto;

import java.util.List;


/**
 * Описывает DTO объекта доступа.
 * @param id идентификатор объекта доступа.
 * @param type тип объекта доступа.
 * @param fields поля объекта, к которым необходим доступ.
 */
public record AccessObject(

        String id,
        String type,
        List<String> fields
) {
}
