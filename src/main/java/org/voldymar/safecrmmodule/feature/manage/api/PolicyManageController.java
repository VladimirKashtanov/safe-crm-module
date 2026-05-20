package org.voldymar.safecrmmodule.feature.manage.api;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voldymar.safecrmmodule.feature.manage.dto.PolicyRequest;
import org.voldymar.safecrmmodule.feature.manage.dto.PolicyResponse;
import org.voldymar.safecrmmodule.feature.shared.service.PolicyService;

import java.util.UUID;


/**
 * Описывает контроллер операций управления политиками доступа.
 */
@RestController
@RequestMapping("/manage/policy")
@RequiredArgsConstructor
public class PolicyManageController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(PolicyManageController.class);

    /**
     * Сервис управления политиками доступа.
     */
    private final PolicyService policyService;


    /**
     * Создает новую политику доступа.
     * @param request запрос.
     * @return созданную сущность.
     */
    @PostMapping("")
    public ResponseEntity<@NonNull PolicyResponse> createPolicy(
            @RequestBody PolicyRequest request
    ) {
        LOGGER.debug("Method 'createPolicy' was called");

        /* Создание сущности.
         *  При ошибке управление передается глобальному обработчику. */
        PolicyResponse response = this.policyService.createOne(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * Удаляет политику доступа по указанному идентификатору.
     * @param id идентификатор политики.
     * @return удаленную сущность.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<@NonNull PolicyResponse> deletePolicy(
            @PathVariable UUID id
    ) {
        LOGGER.debug("Method 'deletePolicy' was called");

        /* Удаление сущности.
         *  При ошибке управление передается глобальному обработчику. */
        PolicyResponse response = this.policyService.deleteOne(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
