package br.com.claudemirojr.sca.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.claudemirojr.sca.api.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "solicitacao_fase")
@Entity
@Audited
@AuditTable(value = "solicitacao_fase_audit")
@EntityListeners(AuditingEntityListener.class)
public class SolicitacaoFase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Solicitacao solicitacao;

	@OneToOne(fetch = FetchType.LAZY)
	private Fase fase;

	@Column(nullable = true)
	private Boolean finalizada;

	@Column(length = 2000, nullable = true)
	private String observacao;

	@OneToOne(fetch = FetchType.LAZY)
	private User responsavel;

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

	public void Insert(Solicitacao solicitacao, Fase fase, String observacao, Boolean finalizada, User responsavel) {
		this.solicitacao = solicitacao;
		this.fase = fase;
		this.observacao = observacao;
		this.finalizada = finalizada;
		this.responsavel = responsavel;
	}

	public void Atualizar(Fase fase, String observacao, Boolean finalizada, User responsavel) {
		this.fase = fase;
		this.observacao = observacao;
		this.finalizada = finalizada;
		this.responsavel = responsavel;
	}

}
