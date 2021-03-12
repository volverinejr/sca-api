package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.sun.istack.NotNull;

import br.com.claudemirojr.sca.api.model.entity.Fase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "fase", "finalizada", "observacao" })
public class SolicitacaoFaseVO extends RepresentationModel<SolicitacaoFaseVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotNull
	private Fase fase;

	private Boolean finalizada;

	private String observacao;

}
