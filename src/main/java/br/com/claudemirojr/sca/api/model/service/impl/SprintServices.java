package br.com.claudemirojr.sca.api.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceInvalidException;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.entity.Sprint;
import br.com.claudemirojr.sca.api.model.entity.SprintSolicitacao;
import br.com.claudemirojr.sca.api.model.repository.SprintRepository;
import br.com.claudemirojr.sca.api.model.repository.SprintSolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.service.ISprintService;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;

@Service
public class SprintServices implements ISprintService {

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private SprintSolicitacaoRepository sprintSolicitacaoRepository;

	private SprintVO convertToSprintVO(Sprint entity) {
		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	private SolicitacaoVO convertToSolicitacaoVO(SprintSolicitacao entity) {
		SolicitacaoVO result = new SolicitacaoVO();
		
		result.setKey( entity.getSolicitacao().getId() );
		result.setDescricao( entity.getSolicitacao().getDescricao() );
		result.setStatusAtual( entity.getSolicitacao().getStatusAtual() );
		result.setUserName(entity.getSolicitacao().getUser().getUsername());
		result.setCreatedDate( entity.getSolicitacao().getCreatedDate() );
		
		
		Sistema sistema = new Sistema();
		sistema.setId( entity.getSolicitacao().getSistema().getId() );
		sistema.setNome( entity.getSolicitacao().getSistema().getNome() );
		result.setSistema( sistema );
		
		Cliente cliente = new Cliente();
		cliente.setId( entity.getSolicitacao().getCliente().getId() );
		cliente.setNome( entity.getSolicitacao().getCliente().getNome() );
		result.setCliente(cliente);
		
		return result;
	}

	@Override
	@Transactional(readOnly = false)
	public SprintVO create(SprintVO sprintVo) {
		var entity = DozerConverter.parseObject(sprintVo, Sprint.class);
		entity.setId(null);

		var vo = DozerConverter.parseObject(sprintRepository.save(entity), SprintVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public SprintVO update(SprintVO sprintVO) {
		var entity = sprintRepository.findById(sprintVO.getKey()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Sprint não encontrado para id %d", sprintVO.getKey())));

		if (entity.getDataEncaminhamentoAoTime() != null) {
			throw new ResourceInvalidException(
					"Sprint " + entity.getId() + " já encaminhado ao time, operação não permitida!");
		}

		entity.Atualizar(sprintVO.getDataInicio(), sprintVO.getDataFim(), sprintVO.getTime());

		var vo = DozerConverter.parseObject(sprintRepository.save(entity), SprintVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Sprint entity = sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));

		if (entity.getDataEncaminhamentoAoTime() != null) {
			throw new ResourceInvalidException(
					"Sprint " + entity.getId() + " já encaminhado ao time, operação não permitida!");
		}

		sprintRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findAll(pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SprintVO findById(Long id) {
		var entity = sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public void encaminarAoTime(Long id) {
		var entity = sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));

		if (entity.getDataEncaminhamentoAoTime() != null) {
			throw new ResourceInvalidException(
					"Sprint " + entity.getId() + " já encaminhado ao time, operação não permitida!");
		}

		entity.EncaminharAoTime();

		sprintRepository.save(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<SolicitacaoVO> findBySprint(Long id) {
		var sprint = sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));

		List<SprintSolicitacao> result = sprintSolicitacaoRepository.findBySprint(sprint);
		
		return result.stream().map(this::convertToSolicitacaoVO).collect(Collectors.toList());
	}

}
