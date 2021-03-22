package br.com.claudemirojr.sca.api.model.service.impl;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceInvalidException;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.sca.api.model.entity.SprintSolicitacao;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoMovimentacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.SprintRepository;
import br.com.claudemirojr.sca.api.model.repository.SprintSolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.service.ISprintSolicitacaoService;
import br.com.claudemirojr.sca.api.model.vo.ISprintSolicitacaoVO;

@Service
public class SprintSolicitacaoService implements ISprintSolicitacaoService {

	@Autowired
	private SprintSolicitacaoRepository sprintSolicitacaoRepository;

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private SolicitacaoMovimentacaoRepository solicitacaoMovimentacaoRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<ISprintSolicitacaoVO> findBySprintSolicitacao(Long idSprint, ParamsRequestModel prm) {
		sprintRepository.findById(idSprint).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", idSprint)));

		Pageable pageable = prm.toSpringPageRequest();

		return sprintSolicitacaoRepository.findBySprintSolicitacao(idSprint, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public void addSolicitacaoASprint(Long idSolicitacao, Long idSprint) {
		var sprint = sprintRepository.findById(idSprint).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", idSprint)));

		if (sprint.getDataEncaminhamentoAoTime() != null) {
			throw new ResourceInvalidException(
					"Sprint " + sprint.getId() + " já encaminhado ao time, operação não permitida!");
		}

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		if ((solicitacao.getStatusAtual() == SolicitacaoStatus.ANALISADA)
				|| (solicitacao.getStatusAtual() == SolicitacaoStatus.PLANEJADA)) {

			SprintSolicitacao sprintSolicitacao = new SprintSolicitacao();

			sprintSolicitacao.Nova(sprint, solicitacao);

			sprintSolicitacaoRepository.save(sprintSolicitacao);

			solicitacao.Planejada();

			solicitacaoRepository.save(solicitacao);

			SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy");

			String observacao = String.format("Sprint %d ( %s à %s )", idSprint, out.format(sprint.getDataInicio()),
					out.format(sprint.getDataFim()));

			SolicitacaoMovimentacao solicitacaoMovimentacao = new SolicitacaoMovimentacao();
			solicitacaoMovimentacao.Inserir(solicitacao, observacao);
			solicitacaoMovimentacaoRepository.save(solicitacaoMovimentacao);
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteSistemaDoCliente(Long idSolicitacao, Long idSprint) {
		var sprint = sprintRepository.findById(idSprint).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", idSprint)));

		if (sprint.getDataEncaminhamentoAoTime() != null) {
			throw new ResourceInvalidException(
					"Sprint " + sprint.getId() + " já encaminhado ao time, operação não permitida!");
		}

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		var entity = sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação: Não encontrado para o conjunto")));

		sprintSolicitacaoRepository.delete(entity);

		// removendo a ultima movimentacao
		var solicitacaoMovimentacao = solicitacaoMovimentacaoRepository
				.findBySolicitacaoAndStatus(solicitacao, solicitacao.getStatusAtual()).get();
		solicitacaoMovimentacaoRepository.delete(solicitacaoMovimentacao);

		// voltando s tatus da solicitação pra analisada
		solicitacao.setStatusAtual(SolicitacaoStatus.ANALISADA);
		solicitacaoRepository.save(solicitacao);
	}

}
