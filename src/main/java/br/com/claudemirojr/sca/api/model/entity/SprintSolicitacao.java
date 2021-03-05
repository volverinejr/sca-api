package br.com.claudemirojr.sca.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "sprint_solicitacao", uniqueConstraints = @UniqueConstraint(columnNames = { "sprint_id",
		"solicitacao_id" }))
@Entity
@Audited
@AuditTable(value = "sprint_solicitacao_audit")
@EntityListeners(AuditingEntityListener.class)
public class SprintSolicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Sprint sprint;

	@OneToOne(fetch = FetchType.LAZY)
	private Solicitacao solicitacao;

	@Column(nullable = false)
	private Boolean finalizada;

	// -------AUDITORIA
	@Column(name = "created_by", nullable = true)
	@CreatedBy
	@JsonIgnore
	private String createdBy;

	@Column(name = "created_date", nullable = true, updatable = false)
	@CreatedDate
	@JsonIgnore
	private Date createdDate;
	// -------AUDITORIA
	
	public void Nova(Sprint sprint, Solicitacao solicitacao) {
		this.sprint = sprint;
		this.solicitacao = solicitacao;
		this.finalizada = false;
	}

}
