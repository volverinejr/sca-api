package br.com.claudemirojr.sca.api.model.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.ClienteSistemaVO;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;
import br.com.claudemirojr.sca.api.model.vo.MovimentacaoVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;

public interface ISolicitacaoService {

	public SolicitacaoVO create(SolicitacaoVO solicitacaoVO);

	public SolicitacaoVO update(SolicitacaoVO solicitacaoVO);

	public SolicitacaoVO findById(Long id);

	public Page<SolicitacaoVO> findAll(ParamsRequestModel prm);

	public Page<SolicitacaoVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<SolicitacaoVO> findByDescricao(String descricao, ParamsRequestModel prm);

	public Page<ClienteVO> FindByClientesDoUsuario(ParamsRequestModel prm);

	public Page<ClienteSistemaVO> FindBySistemasDoCliente(Long idCliente, ParamsRequestModel prm);

	public List<MovimentacaoVO> findByMovimentacao(Long id);

}
