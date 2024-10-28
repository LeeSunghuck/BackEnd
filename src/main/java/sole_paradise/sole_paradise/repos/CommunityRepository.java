package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Community;
import sole_paradise.sole_paradise.domain.User;


public interface CommunityRepository extends JpaRepository<Community, Integer> {

    Community findFirstByUser(User user);

}
