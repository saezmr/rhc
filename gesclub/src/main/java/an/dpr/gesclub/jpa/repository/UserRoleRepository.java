package an.dpr.gesclub.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.gesclub.domain.User;
import an.dpr.gesclub.domain.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    User findByUsername(String username);

}
