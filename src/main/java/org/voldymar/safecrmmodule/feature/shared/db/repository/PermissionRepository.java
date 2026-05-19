package org.voldymar.safecrmmodule.feature.shared.db.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.voldymar.safecrmmodule.feature.shared.db.model.PermissionEntity;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;


/**
 * Описывает интерфейс репозитория для управления разрешениями пользователя.
 */
@Repository
public interface PermissionRepository
        extends JpaRepository<@NonNull PermissionEntity, @NonNull UUID> {

    /**
     * Находит разрешение по идентификатору объекта и типу операции.
     *
     * @param objectId  идентификатор объекта доступа.
     * @param operation тип операции.
     * @return Optional с разрешением или пустой.
     */
    Optional<PermissionEntity> findByObjectTypeAndOperation(
            String objectId,
            String operation
    );


    /**
     * Находит все разрешения, которые есть у пользователя через его роли.
     * @param userId идентификатор пользователя.
     * @return множество разрешений пользователя.
     */
    @Query("""
        SELECT DISTINCT p FROM PermissionEntity p
        JOIN p.roles r
        JOIN r.users u
        WHERE u.id = :userId
    """)
    Set<PermissionEntity> findAllByUserId(
            @Param("userId") UUID userId
    );
}
