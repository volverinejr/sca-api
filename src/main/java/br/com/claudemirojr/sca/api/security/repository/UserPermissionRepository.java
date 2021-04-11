package br.com.claudemirojr.sca.api.security.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.sca.api.security.model.Permission;
import br.com.claudemirojr.sca.api.security.model.User;
import br.com.claudemirojr.sca.api.security.model.UserPermission;
import br.com.claudemirojr.sca.api.security.model.vo.IUserPermissionVO;

public interface UserPermissionRepository extends JpaRepository<UserPermission, Long>{

	public List<UserPermission> findByUser(User user);
	
	
	@Query(
			value = "SELECT	p.id,\r\n"
					+ "		p.description as nome,\r\n"
					+ "		case\r\n"
					+ "			when per_usu.id IS NULL then 0 \r\n"
					+ "			ELSE 1 \r\n"
					+ "		END AS cadastrado \r\n"
					+ "FROM permission AS p\r\n"
					+ "		LEFT JOIN (\r\n"
					+ "			SELECT up.*\r\n"
					+ "			FROM user_permission AS up\r\n"
					+ "			WHERE up.user_id = ?1 \r\n"
					+ "		) AS per_usu ON ( p.id = per_usu.permission_id )\r\n"
					+ "",
			countQuery="SELECT COUNT(id) FROM permission",
			nativeQuery = true)
	Page<IUserPermissionVO> findByPermissaoDoUser(Long id, Pageable pageable);
	
	
	Optional<UserPermission> findByUserAndPermission(User user, Permission permission);
}
