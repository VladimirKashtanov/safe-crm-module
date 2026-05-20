package org.voldymar.safecrmmodule.feature.manage.api;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleRequest;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleResponse;
import org.voldymar.safecrmmodule.feature.manage.service.UserRoleManageService;

import java.util.UUID;


/**
 * Описывает контроллер операций управления ролями и политиками доступа.
 */
@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ManageController.class);

    /**
     * Сервис управления ролями и связями ролей и разрешений.
     */
    private final UserRoleManageService userRoleManageService;


    /**
     * Создает новую роль пользователя.
     *
     * @param request запрос.
     * @return созданную сущность.
     */
    @PostMapping("/role")
    public ResponseEntity<@NonNull UserRoleResponse> createRole(
            @RequestBody UserRoleRequest request
    ) {
        LOGGER.debug("Method 'createRole' was called");

        /* Создание сущности.
         *  При ошибке управление передается глобальному обработчику. */
        UserRoleResponse response =
                this.userRoleManageService.createOne(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    /**
     * Удаляет существующую роль пользователя.
     *
     * @param id идентификатор роли для удаления.
     * @return удаленную сущность.
     */
    @DeleteMapping("/role/{id}")
    public ResponseEntity<@NonNull UserRoleResponse> deleteRole(
            @PathVariable UUID id
    ) {
        LOGGER.debug("Method 'deleteRole' was called");

        /* Удаление сущности.
         * При ошибке управление передается глобальному обработчику. */
        UserRoleResponse response =
                this.userRoleManageService.deleteOne(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
