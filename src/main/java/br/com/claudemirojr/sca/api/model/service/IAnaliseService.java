package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;

public interface IAnaliseService {

	public Page<AnaliseVO> FindByAnalise(ParamsRequestModel prm);

	public AnaliseVO findById(Long id);
	
	public AnaliseVO update(AnaliseVO analiseVO);

}
