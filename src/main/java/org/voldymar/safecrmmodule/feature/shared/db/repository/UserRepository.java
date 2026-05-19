package org.voldymar.safecrmmodule.feature.shared.db.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserEntity;

import java.util.Optional;
import java.util.UUID;


/**
 * Описывает интерфейс репозитория для управления пользователями.
 */
@Repository
public interface UserRepository
        extends JpaRepository<@NonNull UserEntity, @NonNull UUID> {

    /**
     * Находит пользователя по его имени в системе.
     *
     * @param username имя пользователя в системе.
     * @return Optional с пользователем или пустой.
     */
    Optional<UserEntity> findByUsername(String username);
}
