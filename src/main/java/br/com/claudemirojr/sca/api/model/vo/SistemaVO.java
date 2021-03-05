package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.sun.istack.NotNull;

import br.com.claudemirojr.sca.api.model.entity.Time;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "nome", "time", "ativo" })
public class SistemaVO extends RepresentationModel<SistemaVO> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotBlank
	@Size(min = 4, max = 100)
	private String nome;

	@NotNull
	private Boolean ativo;

	@NotNull
	private Time time;

	/*
	 * @Override public int hashCode() { final int prime = 31; int result =
	 * super.hashCode(); result = prime * result + ((ativo == null) ? 0 :
	 * ativo.hashCode()); result = prime * result + ((key == null) ? 0 :
	 * key.hashCode()); result = prime * result + ((nome == null) ? 0 :
	 * nome.hashCode()); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (!super.equals(obj)) return false; if (getClass() != obj.getClass())
	 * return false; SistemaVO other = (SistemaVO) obj; if (ativo == null) { if
	 * (other.ativo != null) return false; } else if (!ativo.equals(other.ativo))
	 * return false; if (key == null) { if (other.key != null) return false; } else
	 * if (!key.equals(other.key)) return false; if (nome == null) { if (other.nome
	 * != null) return false; } else if (!nome.equals(other.nome)) return false;
	 * return true; }
	 */

}
