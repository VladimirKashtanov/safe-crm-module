package org.voldymar.safecrmmodule.feature.access.api;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voldymar.safecrmmodule.feature.access.dto.AccessRequest;
import org.voldymar.safecrmmodule.feature.access.dto.AccessResponse;
import org.voldymar.safecrmmodule.feature.access.dto.AccessType;
import org.voldymar.safecrmmodule.feature.access.service.AccessService;
import org.voldymar.safecrmmodule.feature.securityevent.dto.SecurityEvent;
import org.voldymar.safecrmmodule.feature.securityevent.service.AuditService;

import java.util.Map;


/**
 * Описывает контроллер, проверяющий доступ к объекту.
 */
@RestController
@RequestMapping("/check-access")
@RequiredArgsConstructor
public class AccessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class);

    /**
     * Сервис проверки доступа к объекту.
     */
    private final AccessService accessService;

    /**
     * Сервис аудита событий безопасности.
     */
    private final AuditService auditService;


    /**
     * Предоставляет ответ о разрешении/отклонении доступа к операции
     * @param request запрос на доступ.
     * @return ResponseEntity с телом ответа (AccessResponse DTO).
     */
    @GetMapping
    public ResponseEntity<@NonNull AccessResponse> getAccess(
            @RequestBody AccessRequest request
    ) {
        LOGGER.debug("Method 'getAccess' was called");

        /* Обработка запроса.
         * При запрете управление передается глобальному обработчику. */
        Map<String, String> result = accessService.checkAccess(request);

        /* Формирование события безопасности */
        SecurityEvent event = this.auditService.createSecurityEvent(
                request,
                result.get("decision"),
                result.get("reason")
        );

        /* Формирование положительного ответа */
        var response = new AccessResponse(
                AccessType.PERMITTED.name(),
                event
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
