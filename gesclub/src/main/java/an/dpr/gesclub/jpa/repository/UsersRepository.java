package an.dpr.gesclub.jpa.repository;

import org.springframework.data.repository.CrudRepository;

import an.dpr.gesclub.domain.User;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
