package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.User;


public interface UserRepository extends JpaRepository<User, Integer> {
	User findByAccountEmail(String email);
}