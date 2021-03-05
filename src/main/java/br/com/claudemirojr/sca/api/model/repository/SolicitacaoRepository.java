package br.com.claudemirojr.sca.api.model.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.sca.api.security.model.User;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
	
	Page<Solicitacao> findBySistemaIn(Collection<Sistema> sistemas, Pageable pageable);
	
	Page<Solicitacao> findByUser(User user, Pageable pageable);
	
	Page<Solicitacao> findByStatusAtualIn(Collection<SolicitacaoStatus> statusAtuals, Pageable pageable);

}
