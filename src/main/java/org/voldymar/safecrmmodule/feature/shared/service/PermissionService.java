package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.db.model.PermissionEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.PermissionRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


/**
 * Описывает сервис управления разрешениями пользователя.
 */
@Service
@RequiredArgsConstructor
public class PermissionService {

    /**
     * Репозиторий управления разрешениями пользователя.
     */
    private final PermissionRepository permissionRepository;


    /**
     * Находит разрешение по идентификатору объекта и типу операции.
     *
     * @param objectId  идентификатор объекта доступа.
     * @param operation тип операции.
     * @return Optional с разрешением или пустой.
     */
    public Optional<PermissionEntity> findByObjectTypeAndOperation(
            String objectId,
            String operation
    ) {
        return this.permissionRepository.findByObjectTypeAndOperation(
                objectId,
                operation
        );
    }


    /**
     * Находит все разрешения, которые есть у пользователя через его роли.
     *
     * @param userId идентификатор пользователя.
     * @return множество разрешений пользователя.
     */
    public Set<PermissionEntity> findAllByUserId(
            @Param("userId") UUID userId
    ) {
        return this.permissionRepository.findAllByUserId(userId);
    }
}
