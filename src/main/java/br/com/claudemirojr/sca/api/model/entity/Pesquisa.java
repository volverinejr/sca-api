package br.com.claudemirojr.sca.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "pesquisa")
@Entity
public class Pesquisa implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(length = 2000)
	private String className;

	@Column(length = 2000)
	private String methodName;

	@Column(length = 2000)
	private String argumento;

	@Column(length = 2000)
	private String retorno;

	// -------AUDITORIA
	@Column(name = "created_by")
	// @CreatedBy
	//@JsonIgnore
	private String createdBy;

	@Column(name = "created_date", nullable = false, updatable = false)
	// @CreatedDate
	//@JsonIgnore
	private Date createdDate;
	// -------AUDITORIA

	public void Novo(String className, String methodName, String argumento, String retorno, String createdBy) {
		this.className = className;
		this.methodName = methodName;
		this.argumento = argumento;
		this.retorno = retorno;
		this.createdBy = createdBy;
		this.createdDate = new Date();
	}

}
