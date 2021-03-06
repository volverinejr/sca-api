package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;

public interface IFabricaService {
	
	public Page<SprintVO> FindBySprintDoTime(ParamsRequestModel prm);
	

}
