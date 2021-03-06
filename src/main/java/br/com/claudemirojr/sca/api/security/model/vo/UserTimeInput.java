package br.com.claudemirojr.sca.api.security.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTimeInput implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	Long userId;

	@NotNull
	Long timeId;
}
