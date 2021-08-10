package project.practice.document.repository;

import project.practice.document.models.ERole;
import project.practice.document.models.Role;
import project.practice.document.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsernameOrEmail(String username, String email);

	List<User> findUserByRoles_Id(int id);

	Boolean existsByUsernameOrEmail(String username, String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

}
