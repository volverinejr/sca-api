package br.com.claudemirojr.sca.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "cliente")
@Entity
@Audited
@AuditTable(value = "cliente_audit")
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(length = 100, nullable = false, unique = false)
	private String nome;

	@Column(nullable = false)
	private Boolean ativo;

	// -------AUDITORIA
	@Column(name = "created_by")
	@CreatedBy
	@JsonIgnore
	private String createdBy;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	@JsonIgnore
	private Date createdDate;

	@Column(name = "last_modified_by")
	@LastModifiedBy
	@JsonIgnore
	private String lastModifiedBy;

	@Column(name = "last_modified_date")
	@LastModifiedDate
	@JsonIgnore
	private Date lastModifiedDate;
	// -------AUDITORIA

	public void Atualizar(String nome, Boolean ativo) {
		this.nome = nome;
		this.ativo = ativo;
	}

}
