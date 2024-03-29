package br.com.claudemirojr.sca.api.model.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.TimeRepository;
import br.com.claudemirojr.sca.api.model.repository.UserTimeRepository;
import br.com.claudemirojr.sca.api.model.service.IUserTimeService;
import br.com.claudemirojr.sca.api.model.vo.IUserTimeVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;

@Service
public class UserTimeService implements IUserTimeService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TimeRepository timeRepository;
	
	@Autowired
	private SolicitacaoRepository solicitacaoRepository; 

	@Autowired
	private UserTimeRepository userTimeRepository;
	
	
	
	private UserVO convertToUserVO(UserTime userTime) {
		UserVO userVO = new UserVO();
		userVO.setUserName( userTime.getUser().getUsername() );
		userVO.setKey( userTime.getUser().getId() );

		return userVO;
	}	
	
	

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

	@Override
	@Transactional(readOnly = true)
	public List<UserVO> findByUsuariosDoTimeDaSolicitacao(Long idSolicitacao) {
		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Solicitação não encontrado para id %d", idSolicitacao)));
		
		List<UserTime> userTime = userTimeRepository.findByTime(solicitacao.getSistema().getTime());
		
		return userTime.stream().map(this::convertToUserVO).collect(Collectors.toList());
	}

}
