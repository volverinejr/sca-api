package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "status", "observacao", "dataCadastroFormatada" })
public class MovimentacaoVO extends RepresentationModel<MovimentacaoVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	private SolicitacaoStatus status;

	private String observacao;
	
	private Date createdDate;
	
	public String getDataCadastroFormatada() {

		if (this.createdDate != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.createdDate);
		}

		return null;
	}	
	

}
