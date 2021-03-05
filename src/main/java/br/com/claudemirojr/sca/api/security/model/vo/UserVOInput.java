package br.com.claudemirojr.sca.api.security.model.vo;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import com.sun.istack.NotNull;

import br.com.claudemirojr.sca.api.model.entity.Cliente;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@JsonPropertyOrder({ "id", "userName", "fullName", "email", "enabled", "cliente", "verOutraSolicitacao" })
public class UserVOInput extends RepresentationModel<UserVOInput> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Mapping("id")
	@JsonProperty("id")
	private Long key;

	@NotBlank
	@Size(min = 4, max = 100)
	private String userName;

	@NotBlank
	@Size(min = 4, max = 100)
	private String fullName;
	
	@NotBlank
	@Email
	private String email;
	

	@NotBlank
	@Size(min = 4, max = 12)
	private String password;

	@NotNull
	private Boolean enabled;

	@NotNull
	private Boolean verOutraSolicitacao;

	private Cliente cliente;

}
