package br.com.claudemirojr.sca.api.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;

public interface SolicitacaoMovimentacaoRepository extends JpaRepository<SolicitacaoMovimentacao, Long> {

	Optional<SolicitacaoMovimentacao> findBySolicitacaoAndStatus(Solicitacao solicitacao, SolicitacaoStatus status);

}
