package br.com.claudemirojr.sca.api.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoMovimentacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.service.IAnaliseService;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;

@Service
public class AnaliseService implements IAnaliseService {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private SolicitacaoMovimentacaoRepository solicitacaoMovimentacaoRepository;

	private AnaliseVO convertToAnaliseVO(Solicitacao entity) {
		AnaliseVO result = DozerConverter.parseObject(entity, AnaliseVO.class);
		result.setUserName(entity.getUser().getUsername());

		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AnaliseVO> FindByAnalise(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		List<SolicitacaoStatus> status = new ArrayList<>();
		status.add(SolicitacaoStatus.CADASTRADA);
		status.add(SolicitacaoStatus.ANALISADA);

		var page = solicitacaoRepository.findByStatusAtualIn(status, pageable);

		return page.map(this::convertToAnaliseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public AnaliseVO findById(Long id) {
		var entity = solicitacaoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Solicitação não encontrado para id %d", id)));

		if ((entity.getStatusAtual() == SolicitacaoStatus.CADASTRADA)
				|| (entity.getStatusAtual() == SolicitacaoStatus.ANALISADA)) {
			return convertToAnaliseVO(entity);
		} else {
			throw new ResourceNotFoundException(String.format("Solicitação encontra-se no status atual de %s", entity.getStatusAtual()));
		}

	}

	@Override
	@Transactional(readOnly = false)
	public AnaliseVO update(AnaliseVO analiseVO) {
		var entity = solicitacaoRepository.findById(analiseVO.getKey()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Solicitação não encontrado para id %d", analiseVO.getKey())));

		if ((entity.getStatusAtual() == SolicitacaoStatus.CADASTRADA)
				|| (entity.getStatusAtual() == SolicitacaoStatus.ANALISADA)) {
			
			entity.Analise( analiseVO.getPrioridade() );
			
			solicitacaoRepository.save(entity);
			
			SolicitacaoMovimentacao solicitacaoMovimentacao = new SolicitacaoMovimentacao();
			solicitacaoMovimentacao.Inserir(entity, null);
			solicitacaoMovimentacaoRepository.save(solicitacaoMovimentacao);
			
			return convertToAnaliseVO(entity);
		} else {
			throw new ResourceNotFoundException(String.format("Solicitação encontra-se no status atual de %s", entity.getStatusAtual()));
		}
	}

}
