package in.basha.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.basha.entity.Role;



public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}

