package br.com.claudemirojr.sca.api.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "solicitacao_movimentacao")
@Entity
@Audited
@AuditTable(value = "solicitacao_movimentacao_audit")
@EntityListeners(AuditingEntityListener.class)
public class SolicitacaoMovimentacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private Solicitacao solicitacao;

	@Column(length = 50, nullable = false, updatable = false)
	@Enumerated(EnumType.STRING)
	private SolicitacaoStatus status;

	@Column(length = 2000)
	private String observacao;

	// -------AUDITORIA
	@Column(name = "created_by")
	@CreatedBy
	@JsonIgnore
	private String createdBy;

	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	@JsonIgnore
	private Date createdDate;
	// -------AUDITORIA

	public void Inserir(Solicitacao solicitacao, String observacao) {
		this.solicitacao = solicitacao;
		this.status = solicitacao.getStatusAtual();
		this.observacao = observacao;
	}

}
