package br.com.claudemirojr.sca.api.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.claudemirojr.sca.api.converter.DozerConverter;
import br.com.claudemirojr.sca.api.exception.ResourceNotFoundException;
import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.security.model.Permission;
import br.com.claudemirojr.sca.api.security.model.vo.PermissionVO;
import br.com.claudemirojr.sca.api.security.repository.PermissionRepository;
import br.com.claudemirojr.sca.api.security.service.IPermissionService;

@Service
public class PermissionServices implements IPermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	private PermissionVO convertToPermissionVO(Permission entity) {
		return DozerConverter.parseObject(entity, PermissionVO.class);
	}

	@Override
	@Transactional(readOnly = false)
	public PermissionVO create(PermissionVO permissionVo) {
		var entity = DozerConverter.parseObject(permissionVo, Permission.class);
		entity.setId(null);

		var vo = DozerConverter.parseObject(permissionRepository.save(entity), PermissionVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public PermissionVO update(PermissionVO permissionVo) {
		var entity = permissionRepository.findById(permissionVo.getKey())
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format("Permissão não encontrado para id %d", permissionVo.getKey())));

		entity.Atualizar(permissionVo.getDescription());

		var vo = DozerConverter.parseObject(permissionRepository.save(entity), PermissionVO.class);
		return vo;
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Long id) {
		Permission entity = permissionRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Permissão não encontrado para id %d", id)));
		permissionRepository.delete(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PermissionVO> findAll(ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = permissionRepository.findAll(pageable);

		return page.map(this::convertToPermissionVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PermissionVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = permissionRepository.findByIdGreaterThanEqual(id, pageable);

		return page.map(this::convertToPermissionVO);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<PermissionVO> findByNome(String nome, ParamsRequestModel prm) {
		Pageable pageable = prm.toSpringPageRequest();

		var page = permissionRepository.findByDescriptionIgnoreCaseContaining(nome, pageable);

		return page.map(this::convertToPermissionVO);
	}

	@Override
	@Transactional(readOnly = true)
	public PermissionVO findById(Long id) {
		var entity = permissionRepository.findById(id).orElseThrow(
				() -> new ResourceNotFoundException(String.format("Permissão não encontrado para id %d", id)));
		return DozerConverter.parseObject(entity, PermissionVO.class);
	}

}
