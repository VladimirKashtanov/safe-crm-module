package org.voldymar.safecrmmodule.feature.securityevent.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.access.dto.AccessRequest;
import org.voldymar.safecrmmodule.feature.access.dto.AccessType;
import org.voldymar.safecrmmodule.feature.securityevent.dto.SecurityEvent;
import org.voldymar.safecrmmodule.feature.securityevent.dto.SecurityEventType;
import org.voldymar.safecrmmodule.feature.shared.dto.AccessObject;
import org.voldymar.safecrmmodule.feature.shared.dto.OperationType;
import org.voldymar.safecrmmodule.feature.shared.exception.InvalidAccessObjectException;
import org.voldymar.safecrmmodule.feature.shared.service.OperationMapper;

import java.time.Instant;


/* Описывает сервис генерации событий безопасности */
@Service
@RequiredArgsConstructor
public class AuditService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditService.class);
    private final OperationMapper operationMapper;


    /* Формирует событие безопасности */
    public SecurityEvent createSecurityEvent(
            AccessRequest request,
            String decision,
            String reason
    ) {
        LOGGER.info("Method 'createSecurityEvent' was called");

        String type = AccessType.PERMITTED.name().equals(decision)
                ? SecurityEventType.INFO.name()
                : SecurityEventType.WARN.name();

        return SecurityEvent.builder()
                .type(type)
                .timestamp(Instant.now().toString())
                .message(this.buildMessage(request, decision))
                .decision(decision)
                .reason(reason)
                .subject(request.subject())
                .object(request.object())
                .operation(request.operation())
                .build();
    }


    /* Формирует сообщение для события безопасности */
    private String buildMessage(
            AccessRequest request,
            String decision
    ) {
        AccessObject object = request.object();
        OperationType operation = this.operationMapper.toEnum(request.operation());

        boolean isPermitted = AccessType.PERMITTED.name().equals(decision);

        /* Случай 1. У объекта доступа есть поля */
        if (object.fields() != null && !object.fields().isEmpty()) {
            String fieldList = String.join(", ", object.fields());

            String decisionStr = isPermitted ? "разрешен" : "запрещен";
            return String.format(
                    "Доступ на операцию %s к полям объекта id='%s' %s: %s",
                    operation,
                    object.id(),
                    decisionStr,
                    fieldList
            );
        }

        /* Случай 2. У объекта доступа нет полей, но есть идентификатор */
        if (object.id() != null && !object.id().isEmpty()) {
            String decisionStr = isPermitted ? "разрешен" : "запрещен";
            return String.format(
                    "Доступ на операцию %s к объекту id='%s' %s",
                    operation,
                    object.id(),
                    decisionStr
            );
        }

        /* Случай 3. У объекта нет идентификатора, но операция - CREATE */


        /* Случай 4. Объект доступа не имеет идентификатор */
        LOGGER.warn(
                "Доступ к некорректному объекту доступа запрещен: subjectId='{}'",
                request.subject().userId()
        );
        throw new InvalidAccessObjectException(
                String.format(
                        "Доступ некорректному объекту доступа запрещен: subjectId='%s'",
                        request.subject().userId()
                ),
                request
        );
    }
}
