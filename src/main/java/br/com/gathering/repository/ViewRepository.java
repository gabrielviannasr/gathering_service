package br.com.gathering.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import br.com.gathering.entity.ViewPlaceholder;

/**
 * Base genérica para repositórios baseados em views ou projections.
 * 
 * Esta interface não oferece métodos de escrita (save/delete),
 * sendo destinada apenas a consultas via @Query.
 */
@NoRepositoryBean
public interface ViewRepository extends Repository<ViewPlaceholder, Long> {

}
