package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "className", "methodName", "argumento", "retorno", "createdBy" })
public class PesquisaVO extends RepresentationModel<PesquisaVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	private String className;
	
	private String methodName;
	
	private String argumento;
	
	private String retorno;
	
	private String createdBy;
	
	private Date createdDate;
	
	public String getCreatedDateFormatada() {

		if (this.createdDate != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return formatador.format(this.createdDate);
		}

		return null;
	}	

}
