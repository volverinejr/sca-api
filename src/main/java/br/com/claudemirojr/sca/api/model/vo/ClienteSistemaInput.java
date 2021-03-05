package br.com.claudemirojr.sca.api.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteSistemaInput implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	Long clienteId;

	@NotNull
	Long sistemaId;
}
