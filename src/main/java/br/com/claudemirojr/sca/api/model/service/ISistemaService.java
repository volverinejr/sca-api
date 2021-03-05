package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.SistemaVO;

public interface ISistemaService {
	
	public SistemaVO create(SistemaVO sistema);
	
	public SistemaVO update(SistemaVO sistemaVo);
	
	public void delete(Long id);
	
	public Page<SistemaVO> findAll(ParamsRequestModel prm);
	
	public Page<SistemaVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<SistemaVO> findByNome(String nome, ParamsRequestModel prm);

	public SistemaVO findById(Long id);

}
