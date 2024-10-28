package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Cart;
import sole_paradise.sole_paradise.domain.Product;
import sole_paradise.sole_paradise.domain.User;
import sole_paradise.sole_paradise.model.CartDTO;
import sole_paradise.sole_paradise.repos.CartRepository;
import sole_paradise.sole_paradise.repos.ProductRepository;
import sole_paradise.sole_paradise.repos.UserRepository;
import sole_paradise.sole_paradise.util.NotFoundException;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(final CartRepository cartRepository,
            final ProductRepository productRepository, final UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<CartDTO> findAll() {
        final List<Cart> carts = cartRepository.findAll(Sort.by("cartId"));
        return carts.stream()
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .toList();
    }

    public CartDTO get(final Integer cartId) {
        return cartRepository.findById(cartId)
                .map(cart -> mapToDTO(cart, new CartDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CartDTO cartDTO) {
        final Cart cart = new Cart();
        mapToEntity(cartDTO, cart);
        return cartRepository.save(cart).getCartId();
    }

    public void update(final Integer cartId, final CartDTO cartDTO) {
        final Cart cart = cartRepository.findById(cartId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(cartDTO, cart);
        cartRepository.save(cart);
    }

    public void delete(final Integer cartId) {
        cartRepository.deleteById(cartId);
    }

    private CartDTO mapToDTO(final Cart cart, final CartDTO cartDTO) {
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setQuantity(cart.getQuantity());
        cartDTO.setCreatedAt(cart.getCreatedAt());
        cartDTO.setUpdatedAt(cart.getUpdatedAt());
        cartDTO.setProduct(cart.getProduct() == null ? null : cart.getProduct().getProductId());
        cartDTO.setUser(cart.getUser() == null ? null : cart.getUser().getUserId());
        return cartDTO;
    }

    private Cart mapToEntity(final CartDTO cartDTO, final Cart cart) {
        cart.setQuantity(cartDTO.getQuantity());
        cart.setCreatedAt(cartDTO.getCreatedAt());
        cart.setUpdatedAt(cartDTO.getUpdatedAt());
        final Product product = cartDTO.getProduct() == null ? null : productRepository.findById(cartDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        cart.setProduct(product);
        final User user = cartDTO.getUser() == null ? null : userRepository.findById(cartDTO.getUser())
                .orElseThrow(() -> new NotFoundException("user not found"));
        cart.setUser(user);
        return cart;
    }

}
