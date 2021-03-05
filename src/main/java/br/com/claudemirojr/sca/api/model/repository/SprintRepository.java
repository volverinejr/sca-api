package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Sprint;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

	Page<Sprint> findByIdGreaterThanEqual(Long id, Pageable pageable);

}
