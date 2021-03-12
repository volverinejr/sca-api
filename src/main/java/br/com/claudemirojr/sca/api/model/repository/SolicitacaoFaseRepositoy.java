package br.com.claudemirojr.sca.api.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoFase;

public interface SolicitacaoFaseRepositoy extends JpaRepository<SolicitacaoFase, Long> {

	Page<SolicitacaoFase> findBySolicitacao(Solicitacao solicitacao, Pageable pageable);

}
