package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Page<Cliente> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Cliente> findByNomeIgnoreCaseContaining(String nome, Pageable pageable);

	Page<Cliente> findByAtivo(Boolean ativo, Pageable pageable);
	
	Page<Cliente> findById(Long id, Pageable pageable);
	

}
