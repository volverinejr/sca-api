package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Pesquisa;
import br.com.claudemirojr.sca.api.model.vo.PesquisaVO;

public interface IPesquisaService {

	public void create(Pesquisa pesquisa);

	public Page<PesquisaVO> findAll(ParamsRequestModel prm);

	public Page<PesquisaVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<PesquisaVO> findByClassName(String className, ParamsRequestModel prm);

	public Page<PesquisaVO> findByMethodName(String methodName, ParamsRequestModel prm);

	public Page<PesquisaVO> findByArgumento(String argumento, ParamsRequestModel prm);

	public Page<PesquisaVO> findByRetorno(String retorno, ParamsRequestModel prm);

	public Page<PesquisaVO> findByCreatedBy(String createdBy, ParamsRequestModel prm);

	public PesquisaVO findById(Long id);

}
