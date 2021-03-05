package br.com.claudemirojr.sca.api.model.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.sca.api.model.entity.Cliente;
import br.com.claudemirojr.sca.api.model.entity.ClienteSistema;
import br.com.claudemirojr.sca.api.model.entity.Sistema;
import br.com.claudemirojr.sca.api.model.vo.IClienteSistemaVO;
import br.com.claudemirojr.sca.api.model.vo.ISistemaVO;

public interface ClienteSistemaRepository extends JpaRepository<ClienteSistema, Long> {

	
	@Query(
			value = "SELECT 	a.id,\n"
					+ "			a.nome,\n"
					+ "			case\n"
					+ "				when sis_cli.id IS NULL then false \n"
					+ "				ELSE true \n"
					+ "			END AS cadastrado \n"
					+ "FROM sistema AS a\n"
					+ "		LEFT JOIN (\n"
					+ "					SELECT b.*\n"
					+ "					FROM cliente_sistema AS b\n"
					+ "					WHERE b.cliente_id = ?1 ) as sis_cli ON ( a.id = sis_cli.sistema_id )",
			countQuery="SELECT COUNT(id) FROM sistema",
			nativeQuery = true)
	Page<IClienteSistemaVO> findBySistemasDoCliente(Long id, Pageable pageable);
	
	
	Optional<ClienteSistema> findByClienteAndSistema(Cliente cliente, Sistema sistema);
	
	
	@Query(
			value = "SELECT CAST(s.id AS CHAR) AS id,\n"
					+ "		s.nome,\n"
					+ "		s.ativo\n"
					+ "FROM user_cliente_sistema AS ucs\n"
					+ "		JOIN cliente_sistema AS cs ON ( ucs.cliente_sistema_id = cs.id )\n"
					+ "		JOIN sistema AS s ON ( cs.sistema_id = s.id )\n"
					+ "WHERE ucs.user_id = ?1",
			nativeQuery = true)
	List<ISistemaVO> findBySistemas(Long idUser);
	
	
	
	Page<ClienteSistema> findByCliente(Cliente cliente, Pageable pageable);
	
	Page<ClienteSistema> findByClienteAndSistemaIn(Cliente cliente, Collection<Sistema> sistemas, Pageable pageable);
}
