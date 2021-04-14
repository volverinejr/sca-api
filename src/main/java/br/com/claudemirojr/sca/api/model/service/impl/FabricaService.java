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
import br.com.claudemirojr.sca.api.model.entity.Fase;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoFase;
import br.com.claudemirojr.sca.api.model.entity.Sprint;
import br.com.claudemirojr.sca.api.model.entity.SprintSolicitacao;
import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.repository.FaseRepository;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoFaseRepositoy;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.SprintRepository;
import br.com.claudemirojr.sca.api.model.repository.SprintSolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.UserTimeRepository;
import br.com.claudemirojr.sca.api.model.service.IFabricaService;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoFaseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
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
	private SprintSolicitacaoRepository sprintSolicitacaoRepository;

	@Autowired
	private SolicitacaoFaseRepositoy solicitacaoFaseRepositoy;

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private FaseRepository faseRepository;

	@Autowired
	private IUserService userService;

	private SprintVO convertToSprintVO(Sprint entity) {
		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	private SolicitacaoFaseVO convertToSolicitacaoFaseVO(SolicitacaoFase entity) {
		return DozerConverter.parseObject(entity, SolicitacaoFaseVO.class);
	}

	private AnaliseVO convertToAnaliseVO(SprintSolicitacao entity) {
		AnaliseVO result = DozerConverter.parseObject(entity.getSolicitacao(), AnaliseVO.class);

		result.setKey(entity.getSolicitacao().getId());
		result.setUserName(entity.getSolicitacao().getUser().getUsername());

		return result;
	}

	private List<Time> getTimesDoUsuario(User user) {
		List<Time> times = new ArrayList<>();
		List<UserTime> userTimes = userTimeRepository.findByUser(user);

		for (UserTime linha : userTimes) {
			times.add(linha.getTime());
		}

		return times;
	}

	private User getUsuario() {
		var user = useRepository.findByUserName(userService.getUsuarioLogado());
		if (user == null) {
			throw new ResourceNotFoundException("Username " + userService.getUsuarioLogado() + " nao existe!");
		}

		return user;
	}

	private Sprint getSprint(Long id) {
		var sprint = sprintRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Sprint não encontrado para id %d", id)));
		return sprint;
	}

	private Sprint getSprintDoUser(Long id, User user) {
		var entity = sprintRepository
				.findByIdAndTimeInAndDataEncaminhamentoAoTimeIsNotNull(id, this.getTimesDoUsuario(user)).orElseThrow(
						() -> new ResourceNotFoundException(String.format("Sprint %d não liberado para o time", id)));

		return entity;

	}

	private Fase getFase(Long id) {
		var fase = faseRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException(String.format("Fase não encontrado para id %d", id)));
		return fase;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> FindBySprintDoTime(ParamsRequestModel prm) {
		var user = getUsuario();

		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findByTimeInAndDataEncaminhamentoAoTimeIsNotNull(this.getTimesDoUsuario(user),
				pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SprintVO> findByIdGreaterThanEqualSprintDoTime(Long id, ParamsRequestModel prm) {
		var user = getUsuario();

		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintRepository.findByIdGreaterThanEqualAndTimeInAndDataEncaminhamentoAoTimeIsNotNull(id,
				this.getTimesDoUsuario(user), pageable);

		return page.map(this::convertToSprintVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SprintVO findById(Long id) {
		getSprint(id);

		var user = getUsuario();

		var entity = getSprintDoUser(id, user);

		return DozerConverter.parseObject(entity, SprintVO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<AnaliseVO> FindBySolicitacaoDaSprint(Long idSprint, ParamsRequestModel prm) {
		getSprint(idSprint);

		var user = getUsuario();

		var sprint = getSprintDoUser(idSprint, user);

		Pageable pageable = prm.toSpringPageRequest();

		var page = sprintSolicitacaoRepository.findBySprint(sprint, pageable);

		return page.map(this::convertToAnaliseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SolicitacaoFaseVO> FindBySprintSolicitacaoFase(Long idSprint, Long idSolicitacao,
			ParamsRequestModel prm) {
		getSprint(idSprint);

		var user = getUsuario();

		var sprint = getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação não encontrado para o conjunto")));

		Pageable pageable = prm.toSpringPageRequest();

		var page = solicitacaoFaseRepositoy.findBySolicitacao(solicitacao, pageable);

		return page.map(this::convertToSolicitacaoFaseVO);
	}

	@Override
	@Transactional(readOnly = true)
	public SolicitacaoVO findBySolicitacaoPorId(Long idSprint, Long idSolicitacao) {
		getSprint(idSprint);

		var user = getUsuario();

		getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		var vo = DozerConverter.parseObject(solicitacao, SolicitacaoVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public SolicitacaoFaseVO createFase(Long idSprint, Long idSolicitacao, SolicitacaoFaseVO solicitacaoFase) {
		var sprint = getSprint(idSprint);

		var user = getUsuario();

		getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação não encontrado para o conjunto")));

		var fase = getFase(solicitacaoFase.getFase().getId());

		SolicitacaoFase newSolicitacaoFase = new SolicitacaoFase();
		newSolicitacaoFase.Insert(solicitacao, fase, solicitacaoFase.getObservacao(), solicitacaoFase.getFinalizada());

		var entity = solicitacaoFaseRepositoy.save(newSolicitacaoFase);

		var vo = DozerConverter.parseObject(entity, SolicitacaoFaseVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = true)
	public SolicitacaoFaseVO FindByFase(Long idSprint, Long idSolicitacao, Long idFase) {
		var sprint = getSprint(idSprint);

		var user = getUsuario();

		getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação não encontrado para o conjunto")));

		var entity = solicitacaoFaseRepositoy.findById(idFase).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Fase da Solicitação não encontrado para id %d", idFase)));

		var vo = DozerConverter.parseObject(entity, SolicitacaoFaseVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public SolicitacaoFaseVO updateFase(Long idSprint, Long idSolicitacao, SolicitacaoFaseVO solicitacaoFase) {
		var sprint = getSprint(idSprint);

		var user = getUsuario();

		getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação não encontrado para o conjunto")));

		var fase = faseRepository.findById(solicitacaoFase.getFase().getId())
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Fase não encontrado para id %d", solicitacaoFase.getFase().getId())));

		var entity = solicitacaoFaseRepositoy.findById( solicitacaoFase.getKey() )
				.orElseThrow(() -> new ResourceNotFoundException(String
						.format("Fase da Solicitação não encontrado para id %d", solicitacaoFase.getFase().getId())));

		entity.Atualizar(fase, solicitacaoFase.getObservacao(), solicitacaoFase.getFinalizada());

		var vo = DozerConverter.parseObject(solicitacaoFaseRepositoy.save(entity), SolicitacaoFaseVO.class);

		return vo;
	}

	@Override
	public void deleteFase(Long idSprint, Long idSolicitacao, Long idFase) {
		var sprint = getSprint(idSprint);

		var user = getUsuario();

		getSprintDoUser(idSprint, user);

		var solicitacao = solicitacaoRepository.findById(idSolicitacao).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Solicitação não encontrado para id %d", idSolicitacao)));

		sprintSolicitacaoRepository.findBySolicitacaoAndSprint(solicitacao, sprint)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Sprint/Solicitação não encontrado para o conjunto")));
		
		solicitacaoFaseRepositoy.findById(idFase)
		.orElseThrow(() -> new ResourceNotFoundException(
				String.format("Fase da solicitação não encontrado para id %d", idFase)));

		solicitacaoFaseRepositoy.deleteById(idFase);
	}

}
