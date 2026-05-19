package org.voldymar.safecrmmodule.feature.shared.db.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


/**
 * Описывает модель роли пользователя.
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Имя роли.
     */
    @Column(name = "name", nullable = false, unique = true, length = 20)
    private String name;

    /**
     * Описание роли (необязательное поле).
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * Родительская роль.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_role_id")
    private UserRoleEntity parentRole;

    /**
     * Дочерние роли.
     */
    @OneToMany(mappedBy = "parentRole", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserRoleEntity> childRoles = new HashSet<>();

    /**
     * Набор разрешений для роли.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    @Builder.Default
    private Set<PermissionEntity> permissions = new HashSet<>();

    /**
     * Пользователи, у которых назначена эта роль.
     */
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @Builder.Default
    private Set<UserEntity> users = new HashSet<>();

    /**
     * Время создания роли (неизменяемое поле).
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
