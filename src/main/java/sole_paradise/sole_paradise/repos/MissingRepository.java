package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Missing;
import sole_paradise.sole_paradise.domain.Pet;
import sole_paradise.sole_paradise.domain.WalkRoute;


public interface MissingRepository extends JpaRepository<Missing, Integer> {

    Missing findFirstByPet(Pet pet);

    Missing findFirstByWalkroute(WalkRoute walkRoute);

}
