package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.ClienteSistemaVO;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;

public interface ISolicitacaoService {

	public SolicitacaoVO create(SolicitacaoVO solicitacaoVO);

	public SolicitacaoVO update(SolicitacaoVO solicitacaoVO);

	public SolicitacaoVO findById(Long id);
	
	public Page<SolicitacaoVO> findAll(ParamsRequestModel prm);
	
	public Page<ClienteVO> FindByClientesDoUsuario(ParamsRequestModel prm);

	public Page<ClienteSistemaVO> FindBySistemasDoCliente(Long idCliente, ParamsRequestModel prm);

}
