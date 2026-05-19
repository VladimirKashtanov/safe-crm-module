package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;


/**
 * Описывает сервис управления пользователями.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Репозиторий управления пользователями.
     */
    private final UserRepository userRepository;


    /**
     * Находит пользователя по его идентификатору.
     * @param id идентификатор пользователя.
     * @return Optional с пользователем или пустой.
     */
    public Optional<UserEntity> getUserById(UUID id) {
        return userRepository.findById(id);
    }


    /**
     * Находит пользователя по его имени в системе.
     * @param username имя пользователя в системе.
     * @return Optional с пользователем или пустой.
     */
    public Optional<UserEntity> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
