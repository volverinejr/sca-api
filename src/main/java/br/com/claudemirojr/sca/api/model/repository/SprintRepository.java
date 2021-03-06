package br.com.claudemirojr.sca.api.model.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Sprint;
import br.com.claudemirojr.sca.api.model.entity.Time;

public interface SprintRepository extends JpaRepository<Sprint, Long> {

	Page<Sprint> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Sprint> findByTimeInAndDataEncaminhamentoAoTimeIsNotNull(Collection<Time> times, Pageable pageable);

}
