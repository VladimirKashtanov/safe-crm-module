package org.voldymar.safecrmmodule.feature.shared.db.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Описывает модель политики доступа (ABAC-модель).
 */
@Entity
@Table(name = "policies")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Название политики.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * Описание политики.
     */
    @Column(name = "description", length = 200)
    private String description;

    /**
     * Правило политики.
     */
    @Column(name = "rule_expr", nullable = false, columnDefinition = "TEXT")
    private String ruleExpression;

    /**
     * Эффект применения политики (разрешает/запрещает).
     */
    @Column(name = "effect", nullable = false, length = 10)
    private String effect;

    /**
     * Включена ли политика.
     */
    @Column(name = "enabled")
    @Builder.Default
    private Boolean enabled = true;

    /**
     * Время создания политики (неизменяемое поле).
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Время последнего обновления политики.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
