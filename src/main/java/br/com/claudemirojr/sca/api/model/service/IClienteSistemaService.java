package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.ClienteSistema;
import br.com.claudemirojr.sca.api.model.vo.IClienteSistemaVO;

public interface IClienteSistemaService {

	public Page<IClienteSistemaVO> FindBySistemasDoCliente(Long id, ParamsRequestModel prm);

	public void addSistemaAoCliente(Long idCliente, Long idSistema);

	public void deleteSistemaDoCliente(Long idCliente, Long idSistema);

	public ClienteSistema findById(Long id);

}
