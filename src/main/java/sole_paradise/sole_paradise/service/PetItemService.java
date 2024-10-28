package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.PetItem;
import sole_paradise.sole_paradise.domain.PetItemComment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.PetItemDTO;
import sole_paradise.sole_paradise.repos.PetItemCommentRepository;
import sole_paradise.sole_paradise.repos.PetItemRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class PetItemService {

    private final PetItemRepository petItemRepository;
    private final UserRepository userRepository;
    private final PetItemCommentRepository petItemCommentRepository;

    public PetItemService(final PetItemRepository petItemRepository,
            final UserRepository userRepository,
            final PetItemCommentRepository petItemCommentRepository) {
        this.petItemRepository = petItemRepository;
        this.userRepository = userRepository;
        this.petItemCommentRepository = petItemCommentRepository;
    }

    public List<PetItemDTO> findAll() {
        final List<PetItem> petItems = petItemRepository.findAll(Sort.by("petItemId"));
        return petItems.stream()
                .map(petItem -> mapToDTO(petItem, new PetItemDTO()))
                .toList();
    }

    public PetItemDTO get(final Integer petItemId) {
        return petItemRepository.findById(petItemId)
                .map(petItem -> mapToDTO(petItem, new PetItemDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final PetItemDTO petItemDTO) {
        final PetItem petItem = new PetItem();
        mapToEntity(petItemDTO, petItem);
        return petItemRepository.save(petItem).getPetItemId();
    }

    public void update(final Integer petItemId, final PetItemDTO petItemDTO) {
        final PetItem petItem = petItemRepository.findById(petItemId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(petItemDTO, petItem);
        petItemRepository.save(petItem);
    }

    public void delete(final Integer petItemId) {
        petItemRepository.deleteById(petItemId);
    }

    private PetItemDTO mapToDTO(final PetItem petItem, final PetItemDTO petItemDTO) {
        petItemDTO.setPetItemId(petItem.getPetItemId());
        petItemDTO.setName(petItem.getName());
        petItemDTO.setDescription(petItem.getDescription());
        petItemDTO.setImageUrl(petItem.getImageUrl());
        petItemDTO.setStatus(petItem.getStatus());
        petItemDTO.setPrice(petItem.getPrice());
        petItemDTO.setGood(petItem.getGood());
        petItemDTO.setSharing(petItem.getSharing());
        petItemDTO.setCreatedAt(petItem.getCreatedAt());
        petItemDTO.setUpdatedAt(petItem.getUpdatedAt());
        petItemDTO.setNanum(petItem.getNanum());
        petItemDTO.setUser(petItem.getUser() == null ? null : petItem.getUser().getUserId());
        return petItemDTO;
    }

    private PetItem mapToEntity(final PetItemDTO petItemDTO, final PetItem petItem) {
        petItem.setName(petItemDTO.getName());
        petItem.setDescription(petItemDTO.getDescription());
        petItem.setImageUrl(petItemDTO.getImageUrl());
        petItem.setStatus(petItemDTO.getStatus());
        petItem.setPrice(petItemDTO.getPrice());
        petItem.setGood(petItemDTO.getGood());
        petItem.setSharing(petItemDTO.getSharing());
        petItem.setCreatedAt(petItemDTO.getCreatedAt());
        petItem.setUpdatedAt(petItemDTO.getUpdatedAt());
        petItem.setNanum(petItemDTO.getNanum());
        final User user = petItemDTO.getUser() == null ? null : userRepository.findById(petItemDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        petItem.setUser(user);
        return petItem;
    }

    public ReferencedWarning getReferencedWarning(final Integer petItemId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final PetItem petItem = petItemRepository.findById(petItemId)
                .orElseThrow(NotFoundException::new);
        final PetItemComment petItemPetItemComment = petItemCommentRepository.findFirstByPetItem(petItem);
        if (petItemPetItemComment != null) {
            referencedWarning.setKey("petItem.petItemComment.petItem.referenced");
            referencedWarning.addParam(petItemPetItemComment.getCommentId());
            return referencedWarning;
        }
        return null;
    }

}
