package org.voldymar.safecrmmodule.feature.shared.db.repository;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.voldymar.safecrmmodule.feature.shared.db.model.PolicyEntity;

import java.util.List;
import java.util.UUID;


/**
 * Описывает интерфейс репозитория для управления политиками доступа.
 */
@Repository
public interface PolicyRepository
        extends JpaRepository<@NonNull PolicyEntity, @NonNull UUID> {

    /**
     * Находит все включенные политики с определенным эффектом.
     * @param effect эффект политики (разрешить/запретить).
     * @return список включенных политик с указанным эффектом.
     */
    List<PolicyEntity> findAllByEnabledTrueAndEffect(
            String effect
    );


    /**
     * Проверяет существование политики по имени.
     * @param name имя политики.
     * @return true, если политика с такими именем существует, false иначе.
     */
    boolean existsByName(
            String name
    );
}
