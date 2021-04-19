package br.com.claudemirojr.sca.api.model.service;

import java.util.List;

import org.springframework.data.domain.Page;

import br.com.claudemirojr.sca.api.model.ParamsRequestModel;
import br.com.claudemirojr.sca.api.model.vo.AnaliseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoFaseVO;
import br.com.claudemirojr.sca.api.model.vo.SolicitacaoVO;
import br.com.claudemirojr.sca.api.model.vo.SprintVO;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;

public interface IFabricaService {

	public Page<SprintVO> FindBySprintDoTime(ParamsRequestModel prm);

	public Page<SprintVO> findByIdGreaterThanEqualSprintDoTime(Long id, ParamsRequestModel prm);

	public SprintVO findById(Long idSprint);

	public SolicitacaoVO findBySolicitacaoPorId(Long idSprint, Long idSolicitacao);

	public Page<AnaliseVO> FindBySolicitacaoDaSprint(Long idSprint, ParamsRequestModel prm);

	public Page<SolicitacaoFaseVO> FindBySprintSolicitacaoFase(Long idSprint, Long idSolicitacao,
			ParamsRequestModel prm);
	
	
	public void createFase(Long idSprint, Long idSolicitacao, SolicitacaoFaseVO solicitacaoFase);
	
	public SolicitacaoFaseVO FindByFase(Long idSprint, Long idSolicitacao, Long idFase);
	
	public void updateFase(Long idSprint, Long idSolicitacao, SolicitacaoFaseVO solicitacaoFase);
	
	public void deleteFase(Long idSprint, Long idSolicitacao, Long idFase);
	
	public List<UserVO> findByTime(Long idSprint, Long idSolicitacao);
	
}
