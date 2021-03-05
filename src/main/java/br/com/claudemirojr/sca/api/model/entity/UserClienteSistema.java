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

import br.com.claudemirojr.sca.api.security.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "user_cliente_sistema", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id", "cliente_sistema_id" }))
@Entity
@Audited
@AuditTable(value = "user_cliente_sistema_audit")
@EntityListeners(AuditingEntityListener.class)
public class UserClienteSistema implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	private ClienteSistema clienteSistema;

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
	
}
