package sole_paradise.sole_paradise.service;


import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import sole_paradise.sole_paradise.domain.Cart;
import sole_paradise.sole_paradise.domain.Community;
import sole_paradise.sole_paradise.domain.CommunityComment;
import sole_paradise.sole_paradise.domain.Order;
import sole_paradise.sole_paradise.domain.Pet;
import sole_paradise.sole_paradise.domain.PetItem;
import sole_paradise.sole_paradise.domain.PetItemComment;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.domain.WalkRoute;
import sole_paradise.sole_paradise.model.UserDTO;
import sole_paradise.sole_paradise.repos.CartRepository;
import sole_paradise.sole_paradise.repos.CommunityCommentRepository;
import sole_paradise.sole_paradise.repos.CommunityRepository;
import sole_paradise.sole_paradise.repos.OrderRepository;
import sole_paradise.sole_paradise.repos.PetItemCommentRepository;
import sole_paradise.sole_paradise.repos.PetItemRepository;
import sole_paradise.sole_paradise.repos.PetRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.repos.WalkRouteRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final WalkRouteRepository walkRouteRepository;
    private final PetRepository petRepository;
    private final PetItemRepository petItemRepository;
    private final PetItemCommentRepository petItemCommentRepository;
    private final CommunityRepository communityRepository;
    private final CommunityCommentRepository communityCommentRepository;
    public UserService(final UserRepository userRepository, final CartRepository cartRepository,
            final OrderRepository orderRepository, final WalkRouteRepository walkRouteRepository,
            final PetRepository petRepository, final PetItemRepository petItemRepository,
            final PetItemCommentRepository petItemCommentRepository,
            final CommunityRepository communityRepository,
            final CommunityCommentRepository communityCommentRepository) {
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.walkRouteRepository = walkRouteRepository;
        this.petRepository = petRepository;
        this.petItemRepository = petItemRepository;
        this.petItemCommentRepository = petItemCommentRepository;
        this.communityRepository = communityRepository;
        this.communityCommentRepository = communityCommentRepository;
    }
    public List<UserDTO> findAll() {
        final List<User> users = userRepository.findAll(Sort.by("userId"));
        return users.stream()
                .map(user -> mapToDTO(user, new UserDTO()))
                .toList();
    }
    public UserDTO get(final Integer userId) {
        return userRepository.findById(userId)
                .map(user -> mapToDTO(user, new UserDTO()))
                .orElseThrow(NotFoundException::new);
    }
    public Integer create(final UserDTO userDTO) {
        final User user = new User();
        mapToEntity(userDTO, user);
        return userRepository.save(user).getUserId();
    }
    public void update(final Integer userId, final UserDTO userDTO) {
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(userDTO, user);
        userRepository.save(user);
    }
    public void delete(final Integer userId) {
        userRepository.deleteById(userId);
    }
    private UserDTO mapToDTO(final User user, final UserDTO userDTO) {
        userDTO.setUserId(user.getUserId());
        userDTO.setAccountEmail(user.getAccountEmail());
        userDTO.setProfileNickname(user.getProfileNickname());
        userDTO.setUserPicture(user.getUserPicture());
        userDTO.setNickname(user.getNickname());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }
    private User mapToEntity(final UserDTO userDTO, final User user) {
        user.setAccountEmail(userDTO.getAccountEmail());
        user.setProfileNickname(userDTO.getProfileNickname());
        user.setUserPicture(userDTO.getUserPicture());
        user.setNickname(userDTO.getNickname());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setUpdatedAt(userDTO.getUpdatedAt());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }
    public ReferencedWarning getReferencedWarning(final Integer userId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final User user = userRepository.findById(userId)
                .orElseThrow(NotFoundException::new);
        final Cart userCart = cartRepository.findFirstByUser(user);
        if (userCart != null) {
            referencedWarning.setKey("user.cart.user.referenced");
            referencedWarning.addParam(userCart.getCartId());
            return referencedWarning;
        }
        final Order userOrder = orderRepository.findFirstByUser(user);
        if (userOrder != null) {
            referencedWarning.setKey("user.order.user.referenced");
            referencedWarning.addParam(userOrder.getOrderId());
            return referencedWarning;
        }
        final WalkRoute userWalkRoute = walkRouteRepository.findFirstByUser(user);
        if (userWalkRoute != null) {
            referencedWarning.setKey("user.walkRoute.user.referenced");
            referencedWarning.addParam(userWalkRoute.getWalkrouteId());
            return referencedWarning;
        }
        final Pet userPet = petRepository.findFirstByUser(user);
        if (userPet != null) {
            referencedWarning.setKey("user.pet.user.referenced");
            referencedWarning.addParam(userPet.getPetId());
            return referencedWarning;
        }
        final PetItem userPetItem = petItemRepository.findFirstByUser(user);
        if (userPetItem != null) {
            referencedWarning.setKey("user.petItem.user.referenced");
            referencedWarning.addParam(userPetItem.getPetItemId());
            return referencedWarning;
        }
        final PetItemComment userPetItemComment = petItemCommentRepository.findFirstByUser(user);
        if (userPetItemComment != null) {
            referencedWarning.setKey("user.petItemComment.user.referenced");
            referencedWarning.addParam(userPetItemComment.getCommentId());
            return referencedWarning;
        }
        final Community userCommunity = communityRepository.findFirstByUser(user);
        if (userCommunity != null) {
            referencedWarning.setKey("user.community.user.referenced");
            referencedWarning.addParam(userCommunity.getPostId());
            return referencedWarning;
        }
        final CommunityComment userCommunityComment = communityCommentRepository.findFirstByUser(user);
        if (userCommunityComment != null) {
            referencedWarning.setKey("user.communityComment.user.referenced");
            referencedWarning.addParam(userCommunityComment.getCommentId());
            return referencedWarning;
        }
        return null;
    }
}