package br.com.claudemirojr.sca.api.security.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class UserSenhaVOInput extends RepresentationModel<UserSenhaVOInput> implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;

	@NotBlank
	@Size(min = 4, max = 12)
	private String password;

}
