package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Community;
import sole_paradise.sole_paradise.domain.CommunityComment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.CommunityCommentDTO;
import sole_paradise.sole_paradise.repos.CommunityCommentRepository;
import sole_paradise.sole_paradise.repos.CommunityRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class CommunityCommentService {

    private final CommunityCommentRepository communityCommentRepository;
    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityCommentService(final CommunityCommentRepository communityCommentRepository,
            final CommunityRepository communityRepository, final UserRepository userRepository) {
        this.communityCommentRepository = communityCommentRepository;
        this.communityRepository = communityRepository;
        this.userRepository = userRepository;
    }

    public List<CommunityCommentDTO> findAll() {
        final List<CommunityComment> communityComments = communityCommentRepository.findAll(Sort.by("commentId"));
        return communityComments.stream()
                .map(communityComment -> mapToDTO(communityComment, new CommunityCommentDTO()))
                .toList();
    }

    public CommunityCommentDTO get(final Integer commentId) {
        return communityCommentRepository.findById(commentId)
                .map(communityComment -> mapToDTO(communityComment, new CommunityCommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CommunityCommentDTO communityCommentDTO) {
        final CommunityComment communityComment = new CommunityComment();
        mapToEntity(communityCommentDTO, communityComment);
        return communityCommentRepository.save(communityComment).getCommentId();
    }

    public void update(final Integer commentId, final CommunityCommentDTO communityCommentDTO) {
        final CommunityComment communityComment = communityCommentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(communityCommentDTO, communityComment);
        communityCommentRepository.save(communityComment);
    }

    public void delete(final Integer commentId) {
        communityCommentRepository.deleteById(commentId);
    }

    private CommunityCommentDTO mapToDTO(final CommunityComment communityComment,
            final CommunityCommentDTO communityCommentDTO) {
        communityCommentDTO.setCommentId(communityComment.getCommentId());
        communityCommentDTO.setComment(communityComment.getComment());
        communityCommentDTO.setUpdatedAt(communityComment.getUpdatedAt());
        communityCommentDTO.setCreatedAt(communityComment.getCreatedAt());
        communityCommentDTO.setPost(communityComment.getPost() == null ? null : communityComment.getPost().getPostId());
        communityCommentDTO.setUser(communityComment.getUser() == null ? null : communityComment.getUser().getUserId());
        return communityCommentDTO;
    }

    private CommunityComment mapToEntity(final CommunityCommentDTO communityCommentDTO,
            final CommunityComment communityComment) {
        communityComment.setComment(communityCommentDTO.getComment());
        communityComment.setUpdatedAt(communityCommentDTO.getUpdatedAt());
        communityComment.setCreatedAt(communityCommentDTO.getCreatedAt());
        final Community post = communityCommentDTO.getPost() == null ? null : communityRepository.findById(communityCommentDTO.getPost())
                .orElseThrow(() -> new NotFoundException("post not found"));
        communityComment.setPost(post);
        final User user = communityCommentDTO.getUser() == null ? null : userRepository.findById(communityCommentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        communityComment.setUser(user);
        return communityComment;
    }

}
