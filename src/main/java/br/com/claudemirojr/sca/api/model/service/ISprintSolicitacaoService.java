package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.ISprintSolicitacaoVO;

public interface ISprintSolicitacaoService {

	public Page<ISprintSolicitacaoVO> findBySprintSolicitacao(Long idSprint, ParamsRequestModel prm);

	public void addSolicitacaoASprint(Long idSolicitacao, Long idSprint);

	public void deleteSistemaDoCliente(Long idSolicitacao, Long idSprint);

}
