package org.voldymar.safecrmmodule.feature.access.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * Описывает сервис применения политик доступа.
 */
@Service
@RequiredArgsConstructor
public class PolicyEvaluationService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PolicyEvaluationService.class);

    /**
     * Оценивает правило политики доступа.
     * @param rule правило (сущность.атрибут=значение).
     * @param attributes атрибуты.
     * @return true, если правило выполняется, false иначе.
     */
    public boolean evaluate(
            String rule,
            Map<String, Map<String, String>> attributes
    ) {
        /* при пустом правиле доступ разрешается */
        if (rule == null || rule.isBlank()) {
            return true;
        }

        /* Парсинг правила и проверка на корректность */
        String[] parts = rule.split("=", 2);
        if (parts.length != 2) {
            LOGGER.warn("Некорректное правило: {}", rule);
            return false;
        }

        String left = parts[0].trim();
        String expectedValue = parts[1].trim();

        /* Парсинг левой части правила */
        String[] pathParts = left.split("\\.");
        if (pathParts.length != 2) {
            LOGGER.warn("Некорректный формат: {}", left);
            return false;
        }

        String entity = pathParts[0];
        String attribute = pathParts[1];

        /* Получение карты атрибутов */
        Map<String, String> entityAttributes = attributes.get(entity);
        if (entityAttributes == null) {
            return false;
        }

        /* Получение фактического значения атрибута */
        String actualValue = entityAttributes.get(attribute);
        if (actualValue == null) {
            return false;
        }

        /* Сравнение значений */
        return actualValue.equalsIgnoreCase(expectedValue);
    }
}
