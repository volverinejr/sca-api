package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Sistema;

public interface SistemaRepository extends JpaRepository<Sistema, Long> {

	Page<Sistema> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Sistema> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<Sistema> findByAtivo(Boolean ativo, Pageable pageable);

}
