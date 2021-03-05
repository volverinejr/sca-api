package br.com.claudemirojr.sca.api.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.security.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Page<Permission> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<Permission> findByDescriptionIgnoreCaseContaining(String description, Pageable pageable);

}
