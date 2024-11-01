package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByAccountEmail(String email); // Optional로 반환 타입 변경
}