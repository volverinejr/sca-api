package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;
import br.com.claudemirojr.sca.api.model.service.ITimeService;
import br.com.claudemirojr.sca.api.model.vo.TimeVO;

@Service
public class TimeServices implements ITimeService {

	@Autowired
	private TimeRepository timeRepository;

	private TimeVO convertToTimeVO(Time entity) {
		return DozerConverter.parseObject(entity, TimeVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public TimeVO create(TimeVO timeVo) {
		var entity = DozerConverter.parseObject(timeVo, Time.class);
		var vo = DozerConverter.parseObject(timeRepository.save(entity), TimeVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public TimeVO update(TimeVO timeVo) {
		var entity = timeRepository.findById(timeVo.getKey()).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", timeVo.getKey())));

		entity.Atualizar(timeVo.getNome(), timeVo.getAtivo());

		var vo = DozerConverter.parseObject(timeRepository.save(entity), TimeVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Time entity = timeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", id)));
		timeRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TimeVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = timeRepository.findAll(pageable);

		return page.map(this::convertToTimeVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TimeVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = timeRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToTimeVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<TimeVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = timeRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToTimeVO);
	}

	@Override
	@Transactional(readOnly = true)
	public TimeVO findById(Long id) {
		var entity = timeRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, TimeVO.class);
	}

}
