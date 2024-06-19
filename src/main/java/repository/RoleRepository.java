package repository;

import model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import model.User;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
