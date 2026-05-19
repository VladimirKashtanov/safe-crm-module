package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.dto.OperationType;


/**
 * Описывает маппер сущности Operation.
 */
@Service
@RequiredArgsConstructor
public class OperationMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperationMapper.class);


    /**
     * Конвертирует строку в объект OperationType.
     * @param value строковое значение для конвертации.
     * @return значение OperationType.
     * @throws IllegalArgumentException при некорректном значении value.
     */
    public OperationType toEnum(
            String value
    ) throws IllegalArgumentException {
        return switch (value) {
            case "CREATE" -> OperationType.CREATE;
            case "READ" -> OperationType.READ;
            case "UPDATE" -> OperationType.UPDATE;
            case "DELETE" -> OperationType.DELETE;
            default -> {
                LOGGER.warn("Unknown operation type: {}", value);
                throw new IllegalArgumentException("Unknown operation type: " + value);
            }
        };
    }
}
