package br.com.claudemirojr.sca.api.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.claudemirojr.sca.api.security.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUserName(String userName);
	
	Page<User> findByIdGreaterThanEqual(Long id, Pageable pageable);

	Page<User> findByUserNameIgnoreCaseContaining(String userName, Pageable pageable);
	
	Page<User> findByFullNameIgnoreCaseContaining(String fullName, Pageable pageable);
	

}