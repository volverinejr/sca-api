package br.com.claudemirojr.sca.api.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.sca.api.model.entity.Solicitacao;
import br.com.claudemirojr.sca.api.model.entity.Sprint;
import br.com.claudemirojr.sca.api.model.entity.SprintSolicitacao;
import br.com.claudemirojr.sca.api.model.vo.ISprintSolicitacaoVO;

public interface SprintSolicitacaoRepository extends JpaRepository<SprintSolicitacao, Long> {

	
	@Query(
			value = "SELECT 	s.id,\r\n"
					+ "			s.descricao,\r\n"
					+ "			s.prioridade,\r\n"
					+ "			u.user_name AS username,\r\n"
					+ "			sis.nome AS sistemanome,\r\n"
					+ "			case\r\n"
					+ "				when ( sub.solicitacao_id is NULL) then false\r\n"
					+ "				ELSE true\r\n"
					+ "			END AS cadastrado,\r\n"
					+ "			sub.finalizada\r\n"
					+ "		\r\n"
					+ "FROM solicitacao AS s\r\n"
					+ "		LEFT JOIN (\r\n"
					+ "			SELECT 	ss.solicitacao_id,\r\n"
					+ "						ss.finalizada\r\n"
					+ "			FROM sprint_solicitacao AS ss\r\n"
					+ "			WHERE ss.sprint_id = ?1 \r\n"
					+ "		) AS sub ON ( s.id = sub.solicitacao_id  )\r\n"
					+ "		JOIN users AS u ON ( s.user_id = u.id )\r\n"
					+ "		JOIN sistema AS sis ON ( s.sistema_id = sis.id )\r\n"
					+ "		JOIN sprint AS sp ON ( sp.time_id = sis.time_id  )\r\n"
					+ "\r\n"
					+ "WHERE s.status_atual IN ( 'ANALISADA', 'PLANEJADA' )\r\n"
					+ "	   AND NOT EXISTS (\r\n"
					+ "			SELECT sub_ss.id\r\n"
					+ "			FROM sprint_solicitacao AS sub_ss\r\n"
					+ "			WHERE sub_ss.sprint_id <> ?1 \r\n"
					+ "				    and s.id = sub_ss.solicitacao_id\r\n"
					+ "		)\r\n"
					+ "		AND sp.id = ?1 ",
			countQuery="SELECT 	COUNT(s.id)\r\n"
					+ "FROM solicitacao AS s\r\n"
					+ "		JOIN sistema AS sis ON ( s.sistema_id = sis.id )\r\n"
					+ "		JOIN sprint AS sp ON ( sp.time_id = sis.time_id  )\r\n"
					+ "\r\n"
					+ "WHERE s.status_atual IN ( 'ANALISADA', 'PLANEJADA' )\r\n"
					+ "	   AND NOT EXISTS (\r\n"
					+ "			SELECT sub_ss.id\r\n"
					+ "			FROM sprint_solicitacao AS sub_ss\r\n"
					+ "			WHERE sub_ss.sprint_id <> 1\r\n"
					+ "				    and s.id = sub_ss.solicitacao_id\r\n"
					+ "		)\r\n"
					+ "		AND sp.id = ?1 \r\n"
					+ "",
			nativeQuery = true)
	Page<ISprintSolicitacaoVO> findBySprintSolicitacao(Long idSprint, Pageable pageable);
	
	
	Optional<SprintSolicitacao> findBySolicitacaoAndSprint(Solicitacao solicitacao, Sprint sprint);
	
	
	Page<SprintSolicitacao> findBySprint(Sprint sprint, Pageable pageable);
	
	List<SprintSolicitacao> findBySprint(Sprint sprint);
	
	
	
}
