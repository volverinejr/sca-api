package br.com.claudemirojr.sca.api.model.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceInvalidException;
import br.com.claudemirojr.sca.api.exception.ResourceNaoPermitidoException;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.entity.ClienteSistema;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.SolicitacaoMovimentacao;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.sca.api.model.repository.ClienteRepository;
import br.com.claudemirojr.sca.api.model.repository.ClienteSistemaRepository;
import br.com.claudemirojr.sca.api.model.repository.SistemaRepository;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoMovimentacaoRepository;
import br.com.claudemirojr.sca.api.model.repository.SolicitacaoRepository;
import br.com.claudemirojr.sca.api.model.service.ISolicitacaoService;
import br.com.claudemirojr.sca.api.model.vo.ClienteSistemaVO;
import br.com.claudemirojr.sca.api.model.vo.ClienteVO;
import br.com.claudemirojr.sca.api.model.vo.ISistemaVO;
import br.com.claudemirojr.sca.api.model.vo.MovimentacaoVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;
import br.com.claudemirojr.sca.api.security.service.IUserService;

@Service
public class SolicitacaoService implements ISolicitacaoService {

	@Autowired
	private SolicitacaoRepository solicitacaoRepository;

	@Autowired
	private UserRepository useRepository;

	@Autowired
	private SistemaRepository sistemaRepository;

	@Autowired
	private ClienteSistemaRepository clienteSistemaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private SolicitacaoMovimentacaoRepository solicitacaoMovimentacaoRepository;

	@Autowired
	private IUserService userService;

	private SolicitacaoVO convertToSolicitacaoVO(Solicitacao entity) {
		SolicitacaoVO result = DozerConverter.parseObject(entity, SolicitacaoVO.class);
		result.setUserName(entity.getUser().getUsername());

		return result;
	}

	private ClienteVO convertToClienteVO(Cliente entity) {
		ClienteVO result = DozerConverter.parseObject(entity, ClienteVO.class);
		result.setKey(entity.getId());

		return result;
	}

	private ClienteSistemaVO convertToClienteSistemaVO(ClienteSistema entity) {
		return DozerConverter.parseObject(entity, ClienteSistemaVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public SolicitacaoVO create(SolicitacaoVO solicitacaoVO) {
		sistemaRepository.findById(solicitacaoVO.getSistema().getId()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Sistema não encontrado para id %d", solicitacaoVO.getSistema().getId())));

		var entity = DozerConverter.parseObject(solicitacaoVO, Solicitacao.class);

		var user = useRepository.findByUserName(userService.getUsuarioLogado());
		if (user == null) {
			throw new ResourceNotFoundException("Username " + userService.getUsuarioLogado() + " nao existe!");
		}

		if ((user.getCliente() == null) & (solicitacaoVO.getCliente() == null)) {
			throw new ResourceNotFoundException("Cliente não encontrado para Solicitação/User");
		}

		if (user.getCliente() == null) {
			clienteRepository.findById(solicitacaoVO.getCliente().getId())
					.orElseThrow(() -> new ResourceNotFoundException(
							String.format("Cliente não encontrado para id %d", solicitacaoVO.getCliente().getId())));

			user.setCliente(solicitacaoVO.getCliente());
		}

		entity.InformarUser(user);

		Solicitacao novaSolicitacao = solicitacaoRepository.save(entity);

		SolicitacaoMovimentacao solicitacaoMovimentacao = new SolicitacaoMovimentacao();
		solicitacaoMovimentacao.Inserir(novaSolicitacao, null);
		solicitacaoMovimentacaoRepository.save(solicitacaoMovimentacao);

		var vo = DozerConverter.parseObject(novaSolicitacao, SolicitacaoVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public SolicitacaoVO update(SolicitacaoVO solicitacaoVO) {
		var entity = solicitacaoRepository.findById(solicitacaoVO.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Solicitação não encontrado para id %d", solicitacaoVO.getKey())));

		if (entity.getStatusAtual() != SolicitacaoStatus.CADASTRADA) {
			throw new ResourceInvalidException("Solicitação " + entity.getStatusAtual() + " não pode ser modificada!");
		} else if (!entity.getUser().getUserName().equals(userService.getUsuarioLogado())) {
			throw new ResourceInvalidException("Solicitação só pode ser modificada pelo seu proprietário!");
		}

		sistemaRepository.findById(solicitacaoVO.getSistema().getId()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Sistema não encontrado para id %d", solicitacaoVO.getSistema().getId())));

		entity.Atualizar(solicitacaoVO.getDescricao(), solicitacaoVO.getCliente(), solicitacaoVO.getSistema());

		Solicitacao novaSolicitacao = solicitacaoRepository.save(entity);

		var vo = DozerConverter.parseObject(novaSolicitacao, SolicitacaoVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = true)
	public SolicitacaoVO findById(Long id) {
		var entity = solicitacaoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Solicitação não encontrado para id %d", id)));

		if (userService.isAdmin()) {
			return convertToSolicitacaoVO(entity);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);
			List<Sistema> sistemas = this.getSistemasDoUsuario(user);

			if (user.getVerOutraSolicitacao()) {
				if (sistemas.contains(entity.getSistema())) {
					return convertToSolicitacaoVO(entity);
				} else {
					throw new ResourceNaoPermitidoException("Recursos não disponível para seu usuário!");
				}
			} else {
				if (entity.getUser().getId() == user.getId()) {
					return convertToSolicitacaoVO(entity);
				}
			}
		}

		throw new ResourceNaoPermitidoException("Recursos não disponível para seu usuário!");
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SolicitacaoVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		Page<Solicitacao> page = null;

		if (userService.isAdmin()) {
			page = solicitacaoRepository.findAll(pageable);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);

			if (user.getVerOutraSolicitacao()) {
				page = solicitacaoRepository.findBySistemaIn(this.getSistemasDoUsuario(user), pageable);
			} else {
				page = solicitacaoRepository.findByUser(user, pageable);
			}
		}

		return page.map(this::convertToSolicitacaoVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteVO> FindByClientesDoUsuario(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		if (userService.isAdmin()) {
			var page = clienteRepository.findAll(pageable);

			return page.map(this::convertToClienteVO);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);

			var page = clienteRepository.findById(user.getCliente().getId(), pageable);

			return page.map(this::convertToClienteVO);
		}

	}

	@Override
	@Transactional(readOnly = true)
	public Page<ClienteSistemaVO> FindBySistemasDoCliente(Long idCliente, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var cliente = clienteRepository.findById(idCliente).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Cliente não encontrado para id %d", idCliente)));

		Page<ClienteSistema> page = null;

		if (userService.isAdmin()) {
			page = clienteSistemaRepository.findByCliente(cliente, pageable);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);

			page = clienteSistemaRepository.findByClienteAndSistemaIn(cliente, this.getSistemasDoUsuario(user),
					pageable);
		}

		return page.map(this::convertToClienteSistemaVO);
	}

	private List<Sistema> getSistemasDoUsuario(User user) {
		List<Sistema> sistemas = new ArrayList<>();
		List<ISistemaVO> sistemasVO = clienteSistemaRepository.findBySistemas(user.getId());

		for (ISistemaVO linha : sistemasVO) {
			sistemas.add(sistemaRepository.findById(linha.getId()).orElseThrow(() -> new ResourceNotFoundException(
					String.format("Sistema não encontrado para id %d", linha.getId()))));
		}

		return sistemas;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SolicitacaoVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		Page<Solicitacao> page = null;

		if (userService.isAdmin()) {
			page = solicitacaoRepository.findByIdGreaterThanEqual(id, pageable);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);

			if (user.getVerOutraSolicitacao()) {
				page = solicitacaoRepository.findByIdGreaterThanEqualAndSistemaIn(id, this.getSistemasDoUsuario(user),
						pageable);
			} else {
				page = solicitacaoRepository.findByIdGreaterThanEqualAndUser(id, user, pageable);
			}
		}

		return page.map(this::convertToSolicitacaoVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<SolicitacaoVO> findByDescricao(String descricao, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		Page<Solicitacao> page = null;

		if (userService.isAdmin()) {
			page = solicitacaoRepository.findByDescricaoIgnoreCaseContaining(descricao, pageable);
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);

			if (user.getVerOutraSolicitacao()) {
				page = solicitacaoRepository.findByDescricaoIgnoreCaseContainingAndSistemaIn(descricao,
						this.getSistemasDoUsuario(user), pageable);
			} else {
				page = solicitacaoRepository.findByDescricaoIgnoreCaseContainingAndUser(descricao, user, pageable);
			}
		}

		return page.map(this::convertToSolicitacaoVO);
	}

	private MovimentacaoVO convertToMovimentacaoVO(SolicitacaoMovimentacao solicitacaoMovimentacao) {
		MovimentacaoVO movimentacaoVO = new MovimentacaoVO();
		movimentacaoVO.setKey(solicitacaoMovimentacao.getId());
		movimentacaoVO.setStatus(solicitacaoMovimentacao.getStatus());
		movimentacaoVO.setObservacao(solicitacaoMovimentacao.getObservacao());
		movimentacaoVO.setCreatedDate( solicitacaoMovimentacao.getCreatedDate() );

		return movimentacaoVO;
	}

	@Override
	@Transactional(readOnly = true)
	public List<MovimentacaoVO> findByMovimentacao(Long id) {
		var entity = solicitacaoRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Solicitação não encontrado para id %d", id)));

		List<SolicitacaoMovimentacao> result;
		
		if (userService.isAdmin()) {
			result = solicitacaoMovimentacaoRepository.findBySolicitacao(entity);

			return result.stream().map(this::convertToMovimentacaoVO).collect(Collectors.toList());
		} else {
			String userName = userService.getUsuarioLogado();

			var user = useRepository.findByUserName(userName);
			List<Sistema> sistemas = this.getSistemasDoUsuario(user);

			if (user.getVerOutraSolicitacao()) {
				if (sistemas.contains(entity.getSistema())) {
					result = solicitacaoMovimentacaoRepository.findBySolicitacao(entity);

					return result.stream().map(this::convertToMovimentacaoVO).collect(Collectors.toList());
				} else {
					throw new ResourceNaoPermitidoException("Recursos não disponível para seu usuário!");
				}
			} else {
				if (entity.getUser().getId() == user.getId()) {
					result = solicitacaoMovimentacaoRepository.findBySolicitacao(entity);

					return result.stream().map(this::convertToMovimentacaoVO).collect(Collectors.toList());
				}
			}
		}

		throw new ResourceNaoPermitidoException("Recursos não disponível para seu usuário!");
	}

}
