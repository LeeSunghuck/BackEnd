package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.domain.WalkRoute;


public interface WalkRouteRepository extends JpaRepository<WalkRoute, Integer> {

    WalkRoute findFirstByUser(User user);

}
