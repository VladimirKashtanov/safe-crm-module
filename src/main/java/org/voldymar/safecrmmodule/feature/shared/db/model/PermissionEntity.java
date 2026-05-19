package org.voldymar.safecrmmodule.feature.shared.db.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Описывает модель правила доступа для роли (RBAC-модель).
 */
@Entity
@Table(name = "permissions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Тип объекта доступа.
     */
    @Column(name = "object_type", nullable = false, length = 25)
    private String objectType;

    /**
     * Тип операции.
     */
    @Column(name = "operation", nullable = false, length = 20)
    private String operation;

    /**
     * Описание разрешения.
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * Роли пользователя.
     */
    @ManyToMany(mappedBy = "permissions",  fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRoleEntity> roles = new HashSet<>();

    /**
     * Время создания разрешения (неизменяемое поле).
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
