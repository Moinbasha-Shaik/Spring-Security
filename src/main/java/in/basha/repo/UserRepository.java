package in.basha.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.basha.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByUsername(String username);
}
