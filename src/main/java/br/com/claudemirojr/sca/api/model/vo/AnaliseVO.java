package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.sun.istack.NotNull;

import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "descricao", "sistema", "statusAtual", "userName", "prioridade" })
public class AnaliseVO extends RepresentationModel<AnaliseVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotBlank
	@Size(min = 10, max = 2000)
	private String descricao;

	private Cliente cliente;

	@NotNull
	private Sistema sistema;

	private SolicitacaoStatus statusAtual;

	private String userName;
	
	@NotNull
	@Range(min = 1, max = 3)
	private Integer prioridade;
	

}
