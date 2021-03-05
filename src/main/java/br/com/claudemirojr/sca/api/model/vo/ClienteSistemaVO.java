package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "cliente", "sistema" })
public class ClienteSistemaVO extends RepresentationModel<ClienteSistemaVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	private Cliente cliente;

	private Sistema sistema;

}
