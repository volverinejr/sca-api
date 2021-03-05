package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.UserClienteSistema;
import br.com.claudemirojr.sca.api.model.repository.ClienteSistemaRepository;
import br.com.claudemirojr.sca.api.model.repository.UserClienteSistemaRepository;
import br.com.claudemirojr.sca.api.model.service.IUserClienteSistemaService;
import br.com.claudemirojr.sca.api.model.vo.IUserClienteSistemaVO;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;

@Service
public class UserClienteSistemaService implements IUserClienteSistemaService {

	@Autowired
	private UserClienteSistemaRepository userClienteSistemaRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ClienteSistemaRepository clienteSistemaRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<IUserClienteSistemaVO> FindBySistemasDoUser(Long idUser, ParamsRequestModel prm) {
		var user = userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		Long idCliente = -1L;
		if (user.getCliente() != null) {
			idCliente = user.getCliente().getId();
		}

		Pageable pageable = prm.toSpringPageRequest();

		return userClienteSistemaRepository.findBySistemasDoUser(idUser, idCliente, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public void addSistemaAoUser(Long idUser, Long idClienteSistema) {
		User user = userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		var clienteSistema = clienteSistemaRepository.findById(idClienteSistema)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Cliente/Sistema não encontrado para id %d", idClienteSistema)));

		UserClienteSistema userClienteSistema = new UserClienteSistema();

		userClienteSistema.setUser(user);
		userClienteSistema.setClienteSistema(clienteSistema);

		userClienteSistemaRepository.save(userClienteSistema);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteSistemaDoUser(Long idUser, Long idClienteSistema) {
		User user = userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		var clienteSistema = clienteSistemaRepository.findById(idClienteSistema)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Cliente/Sistema não encontrado para id %d", idClienteSistema)));

		var entity = userClienteSistemaRepository.findByUserAndClienteSistema(user, clienteSistema).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente/Sistema: Não encontrado para o conjunto")));

		userClienteSistemaRepository.delete(entity);
	}

}
