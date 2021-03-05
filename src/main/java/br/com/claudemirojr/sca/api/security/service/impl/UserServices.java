package br.com.claudemirojr.sca.api.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.repository.UserClienteSistemaRepository;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVOInput;
import br.com.claudemirojr.sca.api.security.repository.UserRepository;
import br.com.claudemirojr.sca.api.security.service.IUserService;

@Service
public class UserServices implements UserDetailsService, IUserService {

	@Autowired
	UserRepository useRepository;

	@Autowired
	private UserClienteSistemaRepository userClienteSistemaRepository;
	
	public UserServices(UserRepository repository) {
		this.useRepository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		var user = useRepository.findByUserName(userName);
		if (user != null) {
			return user;
		} else {
			throw new UsernameNotFoundException("Username " + userName + " não existe");
		}

	}

	public String getUsuarioLogado() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return null;
	}

	private UserVO convertToUserVO(User entity) {
		return DozerConverter.parseObject(entity, UserVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public UserVO create(UserVOInput userVo) {
		var entity = DozerConverter.parseObject(userVo, User.class);

		var vo = DozerConverter.parseObject(useRepository.save(entity), UserVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public UserVO update(UserVOInput userVo) {
		var entity = useRepository.findById(userVo.getKey()).orElseThrow(() -> new ResourceNotFoundException(
				String.format("Usuário não encontrado para id %d", userVo.getKey())));

		Long idClienteAntesDeAtualizar = null;

		if (entity.getCliente() != null) {
			idClienteAntesDeAtualizar = entity.getCliente().getId();
		}

		entity.Atualizar(userVo.getUserName(), userVo.getFullName(), userVo.getEnabled(),
				userVo.getVerOutraSolicitacao(), userVo.getEmail(), userVo.getCliente());

		User user = useRepository.save(entity);

		Long idClienteAntesDepoisAtualizar = null;
		if (user.getCliente() != null) {
			idClienteAntesDepoisAtualizar = user.getCliente().getId();
		}

		if ((idClienteAntesDeAtualizar != null) && (idClienteAntesDeAtualizar != idClienteAntesDepoisAtualizar)) {
			userClienteSistemaRepository.deleteByUser(user);
		}

		var vo = DozerConverter.parseObject(user, UserVO.class);

		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		User entity = useRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", id)));
		useRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = useRepository.findAll(pageable);

		return page.map(this::convertToUserVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = useRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToUserVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = useRepository.findByUserNameIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToUserVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserVO> findByNomeCompleto(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = useRepository.findByFullNameIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToUserVO);
	}

	@Override
	@Transactional(readOnly = true)
	public UserVO findById(Long id) {
		var entity = useRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Usuário não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, UserVO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public UserVO findByUserName(String userName) {
		var entity = useRepository.findByUserName(userName);

		if (entity == null) {
			throw new ResourceNotFoundException("Username " + userName + " nao existe!");
		}

		return DozerConverter.parseObject(entity, UserVO.class);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isAdmin() {
		String userName = getUsuarioLogado();
		
		var user = useRepository.findByUserName(userName);
		
		return (user.getRoles().contains("ROLE_ADMIN") ? true : false);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isVerOutraSolicitacao() {
		String userName = getUsuarioLogado();
		
		var user = useRepository.findByUserName(userName);
		
		return user.getVerOutraSolicitacao();
	}

}