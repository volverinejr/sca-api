package br.com.claudemirojr.sca.api.security.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVOInput;

public interface IUserService {

	public UserVO create(UserVOInput user);

	public UserVO update(UserVOInput userVo);

	public void delete(Long id);

	public Page<UserVO> findAll(ParamsRequestModel prm);

	public Page<UserVO> findByIdGreaterThanEqual(Long id, ParamsRequestModel prm);

	public Page<UserVO> findByNome(String nome, ParamsRequestModel prm);

	public Page<UserVO> findByNomeCompleto(String nome, ParamsRequestModel prm);

	public UserVO findById(Long id);

	public String getUsuarioLogado();
	
	public UserVO findByUserName(String userName);
	
	public boolean isAdmin();
	
	public boolean isVerOutraSolicitacao();

}
