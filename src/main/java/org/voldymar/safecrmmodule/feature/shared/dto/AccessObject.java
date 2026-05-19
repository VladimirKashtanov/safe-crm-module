package org.voldymar.safecrmmodule.feature.shared.dto;

import java.util.List;


/**
 * Описывает объект, к которому предоставляется доступ.
 * @param id Идентификатор объекта доступа.
 * @param type Тип объекта доступа.
 * @param fields Поля объекта, к которым необходим доступ.
 */
public record AccessObject(

        String id,
        String type,
        List<String> fields
) {
}
