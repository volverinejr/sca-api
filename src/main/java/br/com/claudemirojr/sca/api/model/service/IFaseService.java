package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.FaseVO;

public interface IFaseService {

	public FaseVO create(FaseVO fase);

	public FaseVO update(FaseVO saseVo);

	public void delete(Long id);

	public Page<FaseVO> findAll(ParamsRequestModel prm);

	public Page<FaseVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<FaseVO> findByNome(String nome, ParamsRequestModel prm);

	public FaseVO findById(Long id);

}
