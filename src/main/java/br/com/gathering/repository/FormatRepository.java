package br.com.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gathering.entity.Format;

@Repository
public interface FormatRepository extends JpaRepository<Format, Long>{

}
