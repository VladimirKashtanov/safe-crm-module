package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.db.model.UserRoleEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.UserRoleRepository;

import java.util.Optional;
import java.util.UUID;


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
     * Находит роль по идентификатору.
     *
     * @param id идентификатор роли.
     * @return Optional с ролью или пустой.
     */
    public Optional<UserRoleEntity> findById(
            UUID id
    ) {
        return this.userRoleRepository.findById(id);
    }


    /**
     * Находит роль по имени.
     *
     * @param name имя роли.
     * @return Optional с ролью или пустой.
     */
    public Optional<UserRoleEntity> findByName(
            String name
    ) {
        return this.userRoleRepository.findByName(name);
    }


    /**
     * Проверяет существование роли по ее имени.
     *
     * @param name имя роли пользователя.
     * @return true, если роль с таким именем существует, false иначе.
     */
    public boolean existsByName(
            String name
    ) {
        return this.userRoleRepository.existsByName(name);
    }


    /**
     * Сохраняет роль пользователя в базу данных.
     *
     * @param entity сущность роли пользователя.
     * @return сохраненная сущность роли пользователя.
     */
    public UserRoleEntity save(
            UserRoleEntity entity
    ) {
        return this.userRoleRepository.save(entity);
    }


    /**
     * Удаляет роль по ее идентификатору.
     *
     * @param id идентификатор роли.
     */
    public void delete(
            UUID id
    ) {
        this.userRoleRepository.deleteById(id);
    }
}
