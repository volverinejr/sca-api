package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.repository.ClienteRepository;
import br.com.claudemirojr.sca.api.model.service.IClienteService;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;

@Service
public class ClienteServices implements IClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	private ClienteVO convertToClienteVO(Cliente entity) {
		return DozerConverter.parseObject(entity, ClienteVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public ClienteVO create(ClienteVO clienteVo) {
		var entity = DozerConverter.parseObject(clienteVo, Cliente.class);
		entity.setId(null);

		var vo = DozerConverter.parseObject(clienteRepository.save(entity), ClienteVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public ClienteVO update(ClienteVO clienteVo) {
		var entity = clienteRepository.findById(clienteVo.getKey()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Cliente não encontrado para id %d", clienteVo.getKey())));

		entity.Atualizar(clienteVo.getNome(), clienteVo.getAtivo());

		var vo = DozerConverter.parseObject(clienteRepository.save(entity), ClienteVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Cliente entity = clienteRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", id)));
		clienteRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = clienteRepository.findAll(pageable);

		return page.map(this::convertToClienteVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = clienteRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToClienteVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = clienteRepository.findByNomeIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToClienteVO);
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteVO findById(Long id) {
		var entity = clienteRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, ClienteVO.class);
	}

}
