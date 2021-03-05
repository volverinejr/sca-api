package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.TimeVO;

public interface ITimeService {

	public TimeVO create(TimeVO time);

	public TimeVO update(TimeVO timeVo);

	public void delete(Long id);

	public Page<TimeVO> findAll(ParamsRequestModel prm);

	public Page<TimeVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<TimeVO> findByNome(String nome, ParamsRequestModel prm);

	public TimeVO findById(Long id);

}
