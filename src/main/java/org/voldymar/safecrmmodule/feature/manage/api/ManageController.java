package org.voldymar.safecrmmodule.feature.manage.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleRequest;
import org.voldymar.safecrmmodule.feature.manage.dto.UserRoleResponse;
import org.voldymar.safecrmmodule.feature.manage.service.UserRoleManageService;


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
     * @return
     */
    @PostMapping("/role")
    public ResponseEntity<?> createRole(
            @RequestBody UserRoleRequest request
    ) {
        LOGGER.debug("Method 'createRole' was called");

        UserRoleResponse response =
                this.userRoleManageService.createOne(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
