package br.com.claudemirojr.sca.api.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Sprint;
import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.repository.SprintRepository;
import br.com.claudemirojr.sca.api.model.repository.UserTimeRepository;
import br.com.claudemirojr.sca.api.model.service.IFabricaService;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;
import br.com.claudemirojr.sca.api.security.service.IUserService;

@Service
public class FabricaService implements IFabricaService {

	@Autowired
	private SprintRepository sprintRepository;

	@Autowired
	private UserRepository useRepository;

	@Autowired
	private UserTimeRepository userTimeRepository;

	@Autowired
	private IUserService userService;

	private SprintVO convertToSprintVO(Sprint entity) {
		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> FindBySprintDoTime(ParamsRequestModel prm) {
		var user = useRepository.findByUserName(userService.getUsuarioLogado());
		if (user == null) {
			throw new ResourceNotFoundException("Username " + userService.getUsuarioLogado() + " nao existe!");
		}

		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findByTimeInAndDataEncaminhamentoAoTimeIsNotNull(this.getTimesDoUsuario(user),
				pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> findByIdGreaterThanEqualSprintDoTime(Long id, ParamsRequestModel prm) {
		var user = useRepository.findByUserName(userService.getUsuarioLogado());
		if (user == null) {
			throw new ResourceNotFoundException("Username " + userService.getUsuarioLogado() + " nao existe!");
		}

		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findByIdGreaterThanEqualAndTimeInAndDataEncaminhamentoAoTimeIsNotNull(id,
				this.getTimesDoUsuario(user), pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SprintVO findById(Long id) {
		sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));


		var user = useRepository.findByUserName(userService.getUsuarioLogado());
		if (user == null) {
			throw new ResourceNotFoundException("Username " + userService.getUsuarioLogado() + " nao existe!");
		}
		
		var entity = sprintRepository
				.findByIdAndTimeInAndDataEncaminhamentoAoTimeIsNotNull(id, this.getTimesDoUsuario(user))
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Sprint %d não liberado para o time", id)));

		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	private List<Time> getTimesDoUsuario(User user) {
		List<Time> times = new ArrayList<>();
		List<UserTime> userTimes = userTimeRepository.findByUser(user);

		for (UserTime linha : userTimes) {
			times.add(linha.getTime());
		}

		return times;
	}

}
