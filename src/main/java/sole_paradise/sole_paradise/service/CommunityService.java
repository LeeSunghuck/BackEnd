package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Community;
import sole_paradise.sole_paradise.domain.CommunityComment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.CommunityDTO;
import sole_paradise.sole_paradise.repos.CommunityCommentRepository;
import sole_paradise.sole_paradise.repos.CommunityRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final CommunityCommentRepository communityCommentRepository;

    public CommunityService(final CommunityRepository communityRepository,
            final UserRepository userRepository,
            final CommunityCommentRepository communityCommentRepository) {
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
        this.communityCommentRepository = communityCommentRepository;
    }

    public List<CommunityDTO> findAll() {
        final List<Community> communities = communityRepository.findAll(Sort.by("postId"));
        return communities.stream()
                .map(community -> mapToDTO(community, new CommunityDTO()))
                .toList();
    }

    public CommunityDTO get(final Integer postId) {
        return communityRepository.findById(postId)
                .map(community -> mapToDTO(community, new CommunityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CommunityDTO communityDTO) {
        final Community community = new Community();
        mapToEntity(communityDTO, community);
        return communityRepository.save(community).getPostId();
    }

    public void update(final Integer postId, final CommunityDTO communityDTO) {
        final Community community = communityRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(communityDTO, community);
        communityRepository.save(community);
    }

    public void delete(final Integer postId) {
        communityRepository.deleteById(postId);
    }

    private CommunityDTO mapToDTO(final Community community, final CommunityDTO communityDTO) {
        communityDTO.setPostId(community.getPostId());
        communityDTO.setTitle(community.getTitle());
        communityDTO.setContents(community.getContents());
        communityDTO.setGood(community.getGood());
        communityDTO.setCreatedAt(community.getCreatedAt());
        communityDTO.setUpdatedAt(community.getUpdatedAt());
        communityDTO.setUser(community.getUser() == null ? null : community.getUser().getUserId());
        return communityDTO;
    }

    private Community mapToEntity(final CommunityDTO communityDTO, final Community community) {
        community.setTitle(communityDTO.getTitle());
        community.setContents(communityDTO.getContents());
        community.setGood(communityDTO.getGood());
        community.setCreatedAt(communityDTO.getCreatedAt());
        community.setUpdatedAt(communityDTO.getUpdatedAt());
        final User user = communityDTO.getUser() == null ? null : userRepository.findById(communityDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        community.setUser(user);
        return community;
    }

    public ReferencedWarning getReferencedWarning(final Integer postId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Community community = communityRepository.findById(postId)
                .orElseThrow(NotFoundException::new);
        final CommunityComment postCommunityComment = communityCommentRepository.findFirstByPost(community);
        if (postCommunityComment != null) {
            referencedWarning.setKey("community.communityComment.post.referenced");
            referencedWarning.addParam(postCommunityComment.getCommentId());
            return referencedWarning;
        }
        return null;
    }

}
