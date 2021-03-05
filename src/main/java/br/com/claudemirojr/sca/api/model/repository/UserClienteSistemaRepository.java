package br.com.claudemirojr.sca.api.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.sca.api.model.entity.ClienteSistema;
import br.com.claudemirojr.sca.api.model.entity.UserClienteSistema;
import br.com.claudemirojr.sca.api.model.vo.IUserClienteSistemaVO;
import br.com.claudemirojr.sca.api.security.model.User;

public interface UserClienteSistemaRepository extends JpaRepository<UserClienteSistema, Long> {

	@Query(value = "SELECT	cs.id,\r\n" + "		s.nome,\r\n" + "		case\r\n"
			+ "			when ucs_cad.cliente_sistema_id IS NULL then false\r\n" + "			ELSE true\r\n"
			+ "		END AS cadastrado\r\n" + "FROM cliente_sistema AS cs\r\n"
			+ "		JOIN sistema AS s ON ( cs.sistema_id = s.id )\r\n" + "		LEFT JOIN (\r\n"
			+ "			SELECT ucs.cliente_sistema_id\r\n" + "			FROM user_cliente_sistema as ucs\r\n"
			+ "			WHERE ucs.user_id = ?1 \r\n"
			+ "		) AS ucs_cad ON ( cs.id = ucs_cad.cliente_sistema_id )\r\n" + "WHERE cs.cliente_id = ?2 \r\n"
			+ "", countQuery = "SELECT COUNT(1) FROM cliente_sistema AS cs WHERE cs.cliente_id = 1", nativeQuery = true)
	Page<IUserClienteSistemaVO> findBySistemasDoUser(Long idUser, Long idCliente, Pageable pageable);

	Optional<UserClienteSistema> findByUserAndClienteSistema(User user, ClienteSistema clienteSistema);
	
	
	@Modifying
	void deleteByUser(User user);

}
