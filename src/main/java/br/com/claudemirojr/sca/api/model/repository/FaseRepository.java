package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Fase;

public interface FaseRepository extends JpaRepository<Fase, Long> {

	Page<Fase> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Fase> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

}
