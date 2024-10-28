package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.PetItem;
import sole_paradise.sole_paradise.domain.PetItemComment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.PetItemCommentDTO;
import sole_paradise.sole_paradise.repos.PetItemCommentRepository;
import sole_paradise.sole_paradise.repos.PetItemRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class PetItemCommentService {

    private final PetItemCommentRepository petItemCommentRepository;
    private final PetItemRepository petItemRepository;
    private final UserRepository userRepository;

    public PetItemCommentService(final PetItemCommentRepository petItemCommentRepository,
            final PetItemRepository petItemRepository, final UserRepository userRepository) {
        this.petItemCommentRepository = petItemCommentRepository;
        this.petItemRepository = petItemRepository;
        this.userRepository = userRepository;
    }

    public List<PetItemCommentDTO> findAll() {
        final List<PetItemComment> petItemComments = petItemCommentRepository.findAll(Sort.by("commentId"));
        return petItemComments.stream()
                .map(petItemComment -> mapToDTO(petItemComment, new PetItemCommentDTO()))
                .toList();
    }

    public PetItemCommentDTO get(final Integer commentId) {
        return petItemCommentRepository.findById(commentId)
                .map(petItemComment -> mapToDTO(petItemComment, new PetItemCommentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PetItemCommentDTO petItemCommentDTO) {
        final PetItemComment petItemComment = new PetItemComment();
        mapToEntity(petItemCommentDTO, petItemComment);
        return petItemCommentRepository.save(petItemComment).getCommentId();
    }

    public void update(final Integer commentId, final PetItemCommentDTO petItemCommentDTO) {
        final PetItemComment petItemComment = petItemCommentRepository.findById(commentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(petItemCommentDTO, petItemComment);
        petItemCommentRepository.save(petItemComment);
    }

    public void delete(final Integer commentId) {
        petItemCommentRepository.deleteById(commentId);
    }

    private PetItemCommentDTO mapToDTO(final PetItemComment petItemComment,
            final PetItemCommentDTO petItemCommentDTO) {
        petItemCommentDTO.setCommentId(petItemComment.getCommentId());
        petItemCommentDTO.setComment(petItemComment.getComment());
        petItemCommentDTO.setCreatedAt(petItemComment.getCreatedAt());
        petItemCommentDTO.setUpdatedAt(petItemComment.getUpdatedAt());
        petItemCommentDTO.setPetItem(petItemComment.getPetItem() == null ? null : petItemComment.getPetItem().getPetItemId());
        petItemCommentDTO.setUser(petItemComment.getUser() == null ? null : petItemComment.getUser().getUserId());
        return petItemCommentDTO;
    }

    private PetItemComment mapToEntity(final PetItemCommentDTO petItemCommentDTO,
            final PetItemComment petItemComment) {
        petItemComment.setComment(petItemCommentDTO.getComment());
        petItemComment.setCreatedAt(petItemCommentDTO.getCreatedAt());
        petItemComment.setUpdatedAt(petItemCommentDTO.getUpdatedAt());
        final PetItem petItem = petItemCommentDTO.getPetItem() == null ? null : petItemRepository.findById(petItemCommentDTO.getPetItem())
                .orElseThrow(() -> new NotFoundException("petItem not found"));
        petItemComment.setPetItem(petItem);
        final User user = petItemCommentDTO.getUser() == null ? null : userRepository.findById(petItemCommentDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        petItemComment.setUser(user);
        return petItemComment;
    }

}
