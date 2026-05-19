package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserRoleEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.UserRoleRepository;

import java.util.Optional;


/**
 * Описывает сервис управления ролями пользователя.
 */
@Service
@RequiredArgsConstructor
public class UserRoleService {

    /**
     * Репозиторий управления ролями пользователя.
     */
    private final UserRoleRepository userRoleRepository;


    /**
     * Находит роль по имени.
     *
     * @param name имя роли.
     * @return Optional с ролью или пустой.
     */
    public Optional<UserRoleEntity> getByName(
            String name
    ) {
        return this.userRoleRepository.findByName(name);
    }
}
