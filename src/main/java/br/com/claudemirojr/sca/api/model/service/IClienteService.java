package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;

public interface IClienteService {

	public ClienteVO create(ClienteVO cliente);

	public ClienteVO update(ClienteVO clienteVo);

	public void delete(Long id);

	public Page<ClienteVO> findAll(ParamsRequestModel prm);

	public Page<ClienteVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<ClienteVO> findByNome(String nome, ParamsRequestModel prm);

	public ClienteVO findById(Long id);

}
