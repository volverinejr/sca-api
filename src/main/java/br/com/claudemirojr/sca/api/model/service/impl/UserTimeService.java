package br.com.claudemirojr.sca.api.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;
import br.com.claudemirojr.sca.api.model.repository.UserTimeRepository;
import br.com.claudemirojr.sca.api.model.service.IUserTimeService;
import br.com.claudemirojr.sca.api.model.vo.IUserTimeVO;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;

@Service
public class UserTimeService implements IUserTimeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TimeRepository timeRepository;

	@Autowired
	private UserTimeRepository userTimeRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<IUserTimeVO> FindByTimesDoUsuario(Long idUser, ParamsRequestModel prm) {
		userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		Pageable pageable = prm.toSpringPageRequest();

		return userTimeRepository.FindByTimesDoUsuario(idUser, pageable);
	}

	@Override
	@Transactional(readOnly = false)
	public void addTimeAoUser(Long idUser, Long idTime) {
		var user = userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		var time = timeRepository.findById(idTime).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", idTime)));

		UserTime userTime = new UserTime();
		userTime.setUser(user);
		userTime.setTime(time);

		userTimeRepository.save(userTime);
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUserDoTime(Long idUser, Long idTime) {
		var user = userRepository.findById(idUser).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", idUser)));

		var time = timeRepository.findById(idTime).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Time não encontrado para id %d", idTime)));

		var entity = userTimeRepository.findByUserAndTime(user, time).orElseThrow(
				() -> new ResourceNotFoundException(String.format("User/Time: Não encontrado para o conjunto")));

		userTimeRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public UserTime findById(Long id) {
		var entity = userTimeRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("User/Time não encontrado para id %d", id)));
		return entity;
	}

}
