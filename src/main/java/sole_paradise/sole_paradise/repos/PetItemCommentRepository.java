package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.PetItem;
import sole_paradise.sole_paradise.domain.PetItemComment;
import sole_paradise.sole_paradise.domain.User;


public interface PetItemCommentRepository extends JpaRepository<PetItemComment, Integer> {

    PetItemComment findFirstByPetItem(PetItem petItem);

    PetItemComment findFirstByUser(User user);

}
