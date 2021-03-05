package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Fase;
import br.com.claudemirojr.sca.api.model.repository.FaseRepository;
import br.com.claudemirojr.sca.api.model.service.IFaseService;
import br.com.claudemirojr.sca.api.model.vo.FaseVO;

@Service
public class FaseServices implements IFaseService {

	@Autowired
	private FaseRepository faseRepository;

	private FaseVO convertToFaseVO(Fase entity) {
		return DozerConverter.parseObject(entity, FaseVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public FaseVO create(FaseVO faseVo) {
		var entity = DozerConverter.parseObject(faseVo, Fase.class);
		var vo = DozerConverter.parseObject(faseRepository.save(entity), FaseVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public FaseVO update(FaseVO faseVo) {
		var entity = faseRepository.findById(faseVo.getKey()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Fase não encontrado para id %d", faseVo.getKey())));

		entity.Atualizar(faseVo.getNome(), faseVo.getPedirAceiteDoUsuario());

		var vo = DozerConverter.parseObject(faseRepository.save(entity), FaseVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Fase entity = faseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Fase não encontrado para id %d", id)));
		faseRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FaseVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = faseRepository.findAll(pageable);

		return page.map(this::convertToFaseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FaseVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = faseRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToFaseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<FaseVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = faseRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToFaseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public FaseVO findById(Long id) {
		var entity = faseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Fase não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, FaseVO.class);
	}

}
