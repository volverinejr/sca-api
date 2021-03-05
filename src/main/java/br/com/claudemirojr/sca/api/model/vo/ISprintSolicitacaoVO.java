package br.com.claudemirojr.sca.api.model.vo;

public interface ISprintSolicitacaoVO {
	Long getId();

	String getDescricao();
	
	Integer getPrioridade();
	
	String getUsername();
	
	String getSistemanome();

	String getCadastrado();
	
	String getFinalizada();
}
