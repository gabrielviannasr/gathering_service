package br.com.gathering.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Entidade placeholder usada para permitir o uso de JpaRepository
 * em repositórios baseados em views/projections.
 */
@Entity
public class ViewPlaceholder {
    @Id
    private Long id;
}
