
package hu.elte.mynews.repository;

import hu.elte.mynews.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    
    Optional<User> findByEmailAndPassword(String email, String password);
}
