package org.voldymar.safecrmmodule.feature.shared.db.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserRoleEntity;

import java.util.Optional;
import java.util.UUID;


/**
 * Описывает интерфейс репозитория для управления ролями пользователя.
 */
@Repository
public interface UserRoleRepository
        extends JpaRepository<@NonNull UserRoleEntity, @NonNull UUID> {

    /**
     * Находит роль по имени.
     *
     * @param name имя роли.
     * @return Optional с ролью или пустой.
     */
    Optional<UserRoleEntity> findByName(String name);
}
