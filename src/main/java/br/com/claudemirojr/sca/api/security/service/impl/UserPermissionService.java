package br.com.claudemirojr.sca.api.security.service.impl;

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
import br.com.claudemirojr.sca.api.security.model.Permission;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.model.UserPermission;
import br.com.claudemirojr.sca.api.security.model.vo.IUserPermissionVO;
import br.com.claudemirojr.sca.api.security.model.vo.PermissionVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import br.com.claudemirojr.sca.api.security.repository.UserPermissionRepository;
import br.com.claudemirojr.sca.api.security.service.IPermissionService;
import br.com.claudemirojr.sca.api.security.service.IUserPermissionService;
import br.com.claudemirojr.sca.api.security.service.IUserService;

@Service
public class UserPermissionService implements IUserPermissionService {

	@Autowired
	private UserPermissionRepository userPermissionRepository;

	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private IPermissionService permissionService;
	

	@Override
	@Transactional(readOnly = true)
	public List<String> getRoles(User user) {
		List<UserPermission> userPermission = userPermissionRepository.findByUser(user);

		List<String> roles = new ArrayList<>();

		for (UserPermission linha : userPermission) {
			roles.add(linha.getPermission().getDescription());
		}

		return roles;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<IUserPermissionVO> findByPermissaoDoUser(Long idUser, ParamsRequestModel prm) {
		userService.findById(idUser);

		Pageable pageable = prm.toSpringPageRequest();

		return userPermissionRepository.findByPermissaoDoUser(idUser, pageable);
	}

	
	@Override
	@Transactional(readOnly = false)
	public void addPermissaoAoUser(Long idPermissao, Long idUser) {
		PermissionVO permissionVO = permissionService.findById(idPermissao);
		
		UserVO userVO = userService.findById(idUser);
		
		UserPermission userPermission = new UserPermission();
		
		userPermission.setPermission( DozerConverter.parseObject(permissionVO, Permission.class) );
		userPermission.setUser( DozerConverter.parseObject(userVO, User.class) );
		
		userPermissionRepository.save(userPermission);
	}

	
	@Override
	@Transactional(readOnly = false)
	public void deletePermissaoDoUser(Long idPermissao, Long idUser) {
		PermissionVO permissionVO = permissionService.findById(idPermissao);
		
		UserVO userVO = userService.findById(idUser);

		var entity = userPermissionRepository.findByUserAndPermission(DozerConverter.parseObject(userVO, User.class), DozerConverter.parseObject(permissionVO, Permission.class))  .orElseThrow(
				() -> new ResourceNotFoundException(String.format("User/Permissão: Não encontrado para o conjunto")));
		
		userPermissionRepository.deleteById(entity.getId());
	}

}
