package br.com.claudemirojr.sca.api.model.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.vo.IUserTimeVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;

public interface IUserTimeService {

	public Page<IUserTimeVO> FindByTimesDoUsuario(Long idUser, ParamsRequestModel prm);

	public void addTimeAoUser(Long idUser, Long idTime);

	public void deleteUserDoTime(Long idUser, Long idTime);

	public UserTime findById(Long id);
	
	public List<UserVO> findByUsuariosDoTimeDaSolicitacao(Long idSolicitacao);
}
