package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.repository.SistemaRepository;
import br.com.claudemirojr.sca.api.model.service.ISistemaService;
import br.com.claudemirojr.sca.api.model.vo.SistemaVO;

@Service
public class SistemaServices implements ISistemaService {

	@Autowired
	private SistemaRepository sistemaRepository;

	private SistemaVO convertToSistemaVO(Sistema entity) {
		return DozerConverter.parseObject(entity, SistemaVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public SistemaVO create(SistemaVO sistemaVo) {
		var entity = DozerConverter.parseObject(sistemaVo, Sistema.class);
		entity.setId(null);

		var vo = DozerConverter.parseObject(sistemaRepository.save(entity), SistemaVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public SistemaVO update(SistemaVO sistemaVo) {
		var entity = sistemaRepository.findById(sistemaVo.getKey()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Sistema não encontrado para id %d", sistemaVo.getKey())));

		entity.Atualizar(sistemaVo.getNome(), sistemaVo.getAtivo(), sistemaVo.getTime());

		var vo = DozerConverter.parseObject(sistemaRepository.save(entity), SistemaVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Sistema entity = sistemaRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sistema não encontrado para id %d", id)));
		sistemaRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SistemaVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = sistemaRepository.findAll(pageable);

		return page.map(this::convertToSistemaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SistemaVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = sistemaRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToSistemaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SistemaVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = sistemaRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToSistemaVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SistemaVO findById(Long id) {
		var entity = sistemaRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sistema não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, SistemaVO.class);
	}

}
