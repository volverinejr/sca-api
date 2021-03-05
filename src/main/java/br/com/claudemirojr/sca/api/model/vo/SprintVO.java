package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

import br.com.claudemirojr.sca.api.model.entity.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "dataInicio", "dataInicioFormatada", "dataFim", "dataFimFormatada", "time", "dataEncaminhamentoAoTimeFormatada" })
public class SprintVO extends RepresentationModel<SprintVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotNull
	private Date dataInicio;

	@NotNull
	private Date dataFim;

	@NotNull
	private Time time;
	
	private Date dataEncaminhamentoAoTime;
	

	public String getDataInicioFormatada() {

		if (this.dataInicio != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.dataInicio);
		}

		return null;
	}

	public String getDataFimFormatada() {

		if (this.dataFim != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
			return formatador.format(this.dataFim);
		}

		return null;
	}
	
	public String getdataEncaminhamentoAoTimeFormatada() {

		if (this.dataEncaminhamentoAoTime != null) {
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			return formatador.format(this.dataEncaminhamentoAoTime);
		}

		return null;
	}	

}
