package br.com.claudemirojr.sca.api.model.service;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.IUserClienteSistemaVO;

public interface IUserClienteSistemaService {

	public Page<IUserClienteSistemaVO> FindBySistemasDoUser(Long idUser, ParamsRequestModel prm);

	public void addSistemaAoUser(Long idUser, Long idClienteSistema);

	public void deleteSistemaDoUser(Long idUser, Long idClienteSistema);

}
