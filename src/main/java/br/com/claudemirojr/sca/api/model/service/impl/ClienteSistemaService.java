package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.ClienteSistema;
import br.com.claudemirojr.sca.api.model.repository.ClienteRepository;
import br.com.claudemirojr.sca.api.model.repository.ClienteSistemaRepository;
import br.com.claudemirojr.sca.api.model.repository.SistemaRepository;
import br.com.claudemirojr.sca.api.model.service.IClienteSistemaService;
import br.com.claudemirojr.sca.api.model.vo.IClienteSistemaVO;

@Service
public class ClienteSistemaService implements IClienteSistemaService {

	@Autowired
	private ClienteSistemaRepository clienteSistemaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private SistemaRepository sistemaRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<IClienteSistemaVO> FindBySistemasDoCliente(Long id, ParamsRequestModel prm) {
		clienteRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", id)));

		Pageable pageable = prm.toSpringPageRequest();

		return clienteSistemaRepository.findBySistemasDoCliente(id, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public void addSistemaAoCliente(Long idCliente, Long idSistema) {
		var cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", idCliente)));

		var sistema = sistemaRepository.findById(idSistema).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sistema não encontrado para id %d", idSistema)));

		ClienteSistema clienteSistema = new ClienteSistema();

		clienteSistema.setCliente(cliente);
		clienteSistema.setSistema(sistema);

		clienteSistemaRepository.save(clienteSistema);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteSistemaDoCliente(Long idCliente, Long idSistema) {
		var cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", idCliente)));

		var sistema = sistemaRepository.findById(idSistema).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sistema não encontrado para id %d", idSistema)));

		var entity = clienteSistemaRepository.findByClienteAndSistema(cliente, sistema).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente/Sistema: Não encontrado para o conjunto")));

		clienteSistemaRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteSistema findById(Long id) {
		var entity = clienteSistemaRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente/Sistema não encontrado para id %d", id)));
		return entity;
	}

}
