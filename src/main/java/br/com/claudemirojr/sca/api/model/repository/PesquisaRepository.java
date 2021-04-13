package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Pesquisa;

public interface PesquisaRepository extends JpaRepository<Pesquisa, Long> {

	Page<Pesquisa> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Pesquisa> findByClassNameIgnoreCaseContaining(String className, Pageable pageable);

	Page<Pesquisa> findByMethodNameIgnoreCaseContaining(String methodName, Pageable pageable);

	Page<Pesquisa> findByArgumentoIgnoreCaseContaining(String argumento, Pageable pageable);

	Page<Pesquisa> findByRetornoIgnoreCaseContaining(String retorno, Pageable pageable);

	Page<Pesquisa> findByCreatedByIgnoreCaseContaining(String createdBy, Pageable pageable);
}
