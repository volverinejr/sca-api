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
	
	//id
	Page<Solicitacao> findByIdGreaterThanEqual(Long id, Pageable pageable);
	
	Page<Solicitacao> findByIdGreaterThanEqualAndSistemaIn(Long id, Collection<Sistema> sistemas, Pageable pageable);

	Page<Solicitacao> findByIdGreaterThanEqualAndUser(Long id, User user, Pageable pageable);
	
	//descricao
	Page<Solicitacao> findByDescricaoIgnoreCaseContaining(String descricao, Pageable pageable);
	
	Page<Solicitacao> findByDescricaoIgnoreCaseContainingAndSistemaIn(String descricao, Collection<Sistema> sistemas, Pageable pageable);

	Page<Solicitacao> findByDescricaoIgnoreCaseContainingAndUser(String descricao, User user, Pageable pageable);

}
