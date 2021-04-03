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
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

import br.com.claudemirojr.sca.api.model.entity.enums.SolicitacaoStatus;
import br.com.claudemirojr.sca.api.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "solicitacao")
@Entity
@Audited
@AuditTable(value = "solicitacao_audit")
@EntityListeners(AuditingEntityListener.class)

public class Solicitacao implements Serializable {

	private static final long serialVersionUID = 1L;

	public Solicitacao(String descricao, User user, Cliente cliente, Sistema sistema) {
		this.descricao = descricao;
		this.user = user;
		this.cliente = cliente;
		this.sistema = sistema;
		this.statusAtual = SolicitacaoStatus.CADASTRADA;
	}

	@Id
	// @GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(length = 2000, nullable = false)
	private String descricao;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	private Cliente cliente;

	@OneToOne(fetch = FetchType.LAZY)
	private Sistema sistema;

	@Column(nullable = true)
	private Integer prioridade;

	@Column(name = "status_atual", length = 50, nullable = true)
	@Enumerated(EnumType.STRING)
	private SolicitacaoStatus statusAtual;

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

	public void InformarUser(User user) {
		this.user = user;
		this.cliente = user.getCliente();
		this.statusAtual = SolicitacaoStatus.CADASTRADA;
	}

	public void Atualizar(String descricao, Cliente cliente, Sistema sistema) {
		this.descricao = descricao;
		this.cliente = cliente;
		this.sistema = sistema;
	}

	public void Analise(Integer prioridade) {
		this.prioridade = prioridade;
		this.statusAtual = SolicitacaoStatus.ANALISADA;
	}

	public void Planejada() {
		this.statusAtual = SolicitacaoStatus.PLANEJADA;
	}

}
