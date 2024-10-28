package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.PetItem;
import sole_paradise.sole_paradise.domain.User;


public interface PetItemRepository extends JpaRepository<PetItem, Integer> {

    PetItem findFirstByUser(User user);

}
