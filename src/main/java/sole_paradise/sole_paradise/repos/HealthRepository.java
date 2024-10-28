package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Health;
import sole_paradise.sole_paradise.domain.Pet;


public interface HealthRepository extends JpaRepository<Health, Integer> {

    Health findFirstByPet(Pet pet);

}
