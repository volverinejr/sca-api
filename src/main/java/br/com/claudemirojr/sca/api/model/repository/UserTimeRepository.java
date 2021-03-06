package br.com.claudemirojr.sca.api.model.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.claudemirojr.sca.api.model.entity.Time;
import br.com.claudemirojr.sca.api.model.entity.UserTime;
import br.com.claudemirojr.sca.api.model.vo.IUserTimeVO;
import br.com.claudemirojr.sca.api.security.model.User;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
	
	@Query(
			value = "SELECT	t.id,\n"
					+ "		t.nome,\n"
					+ "		case\n"
					+ "			when ut.id IS NULL then false\n"
					+ "			ELSE true\n"
					+ "		END AS cadastrado\n"
					+ "FROM time AS t\n"
					+ " 		LEFT JOIN (\n"
					+ " 			SELECT 	b.id,\n"
					+ "			 			b.time_id\n"
					+ " 			FROM user_time AS b\n"
					+ " 			WHERE b.user_id = ?1 \n"
					+ "		 ) AS ut ON ( t.id = ut.time_id )",
			countQuery="SELECT COUNT(id) FROM time",
			nativeQuery = true)
	Page<IUserTimeVO> FindByTimesDoUsuario(Long idUser, Pageable pageable);
	
	
	Optional<UserTime> findByUserAndTime(User user, Time time);
	
	

}
