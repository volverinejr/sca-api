package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;

public interface ISprintService {

	public SprintVO create(SprintVO sprint);

	public SprintVO update(SprintVO sprintVO);

	public void delete(Long id);
	
	public void encaminarAoTime(Long id);

	public Page<SprintVO> findAll(ParamsRequestModel prm);

	public Page<SprintVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public SprintVO findById(Long id);

}
