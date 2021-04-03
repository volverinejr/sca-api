package br.com.claudemirojr.sca.api.security.model;

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
import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "permission")
@Entity
@Audited
@AuditTable(value = "permission_audit")
@EntityListeners(AuditingEntityListener.class)
public class Permission implements GrantedAuthority, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@Column(name = "id")
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;

	@Column(name = "description")
	private String description;

	@Override
	public String getAuthority() {
		return this.description;
	}

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
	
	
	public void Atualizar(String description) {
		this.description = description;
	}
	

	/*
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * public String getDescription() { return description; }
	 * 
	 * public void setDescription(String description) { this.description =
	 * description; }
	 * 
	 * @Override public int hashCode() { final int prime = 31; int result = 1;
	 * result = prime * result + ((description == null) ? 0 :
	 * description.hashCode()); result = prime * result + ((id == null) ? 0 :
	 * id.hashCode()); return result; }
	 * 
	 * @Override public boolean equals(Object obj) { if (this == obj) return true;
	 * if (obj == null) return false; if (getClass() != obj.getClass()) return
	 * false; Permission other = (Permission) obj; if (description == null) { if
	 * (other.description != null) return false; } else if
	 * (!description.equals(other.description)) return false; if (id == null) { if
	 * (other.id != null) return false; } else if (!id.equals(other.id)) return
	 * false; return true; }
	 */
}
