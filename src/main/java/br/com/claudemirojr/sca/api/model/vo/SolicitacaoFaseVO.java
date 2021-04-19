package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.sun.istack.NotNull;

import br.com.claudemirojr.sca.api.model.entity.Fase;
import br.com.claudemirojr.sca.api.security.model.vo.UserVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "fase", "responsavel", "observacao", "finalizada" })
public class SolicitacaoFaseVO extends RepresentationModel<SolicitacaoFaseVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotNull
	private Fase fase;

	@NotNull
	private UserVO responsavel;

	private Boolean finalizada;

	private String observacao;

}
