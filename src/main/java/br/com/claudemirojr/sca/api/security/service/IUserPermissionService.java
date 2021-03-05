package br.com.claudemirojr.sca.api.security.service;


import java.util.List;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.model.vo.IUserPermissionVO;

public interface IUserPermissionService {
	
	public List<String> getRoles(User user);
	
	public Page<IUserPermissionVO> findByPermissaoDoUser(Long idUser, ParamsRequestModel prm);
	
	public void addPermissaoAoUser(Long idPermissao, Long idUser);
	
	public void deletePermissaoDoUser(Long idPermissao, Long idUser);

}
