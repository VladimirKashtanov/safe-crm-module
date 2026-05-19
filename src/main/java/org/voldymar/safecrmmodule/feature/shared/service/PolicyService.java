package org.voldymar.safecrmmodule.feature.shared.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.voldymar.safecrmmodule.feature.shared.db.model.PolicyEntity;
import org.voldymar.safecrmmodule.feature.shared.db.repository.PolicyRepository;

import java.util.List;


/**
 * Описывает сервис управления политиками доступа.
 */
@Service
@RequiredArgsConstructor
public class PolicyService {

    /**
     * Репозиторий управления политиками доступа.
     */
    private final PolicyRepository policyRepository;


    /**
     * Находит все включенные политики с определенным эффектом.
     * @param effect эффект политики (разрешить/запретить).
     * @return список включенных политик с указанным эффектом.
     */
    public List<PolicyEntity> findAllByEnabledTrueAndEffect(
            String effect
    ) {
        return this.policyRepository.findAllByEnabledTrueAndEffect(
                effect
        );
    }
}
