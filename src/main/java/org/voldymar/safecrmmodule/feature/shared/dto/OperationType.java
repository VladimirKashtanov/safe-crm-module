package org.voldymar.safecrmmodule.feature.shared.dto;

/**
 * Описывает тип операции, к которой предоставляется доступ.
 */
public enum OperationType {

    /**
     * Операция создания.
     */
    CREATE,

    /**
     * Операция чтения.
     */
    READ,

    /**
     * Операция изменения.
     */
    UPDATE,

    /**
     * Операция удаления.
     */
    DELETE
}
