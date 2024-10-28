package sole_paradise.sole_paradise.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import sole_paradise.sole_paradise.domain.Community;
import sole_paradise.sole_paradise.domain.CommunityComment;
import sole_paradise.sole_paradise.domain.User;


public interface CommunityCommentRepository extends JpaRepository<CommunityComment, Integer> {

    CommunityComment findFirstByPost(Community community);

    CommunityComment findFirstByUser(User user);

}
