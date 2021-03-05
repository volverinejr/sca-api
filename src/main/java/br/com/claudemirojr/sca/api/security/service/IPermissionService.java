package br.com.claudemirojr.sca.api.security.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.security.model.vo.PermissionVO;

public interface IPermissionService {

	public PermissionVO create(PermissionVO permission);

	public PermissionVO update(PermissionVO permissionVo);

	public void delete(Long id);

	public Page<PermissionVO> findAll(ParamsRequestModel prm);

	public Page<PermissionVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<PermissionVO> findByNome(String nome, ParamsRequestModel prm);

	public PermissionVO findById(Long id);

}
