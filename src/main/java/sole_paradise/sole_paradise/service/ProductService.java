package sole_paradise.sole_paradise.service;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sole_paradise.sole_paradise.domain.Cart;
import sole_paradise.sole_paradise.domain.OrderDetails;
import sole_paradise.sole_paradise.domain.Product;
import sole_paradise.sole_paradise.model.ProductDTO;
import sole_paradise.sole_paradise.repos.CartRepository;
import sole_paradise.sole_paradise.repos.OrderDetailsRepository;
import sole_paradise.sole_paradise.repos.ProductRepository;
import sole_paradise.sole_paradise.util.NotFoundException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final OrderDetailsRepository orderDetailsRepository;

    public ProductService(final ProductRepository productRepository,
            final CartRepository cartRepository,
            final OrderDetailsRepository orderDetailsRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.orderDetailsRepository = orderDetailsRepository;
    }

    public List<ProductDTO> findAll() {
        final List<Product> products = productRepository.findAll(Sort.by("productId"));
        return products.stream()
                .map(product -> mapToDTO(product, new ProductDTO()))
                .toList();
    }

    public ProductDTO get(final Integer productId) {
        return productRepository.findById(productId)
                .map(product -> mapToDTO(product, new ProductDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductDTO productDTO) {
        final Product product = new Product();
        mapToEntity(productDTO, product);
        return productRepository.save(product).getProductId();
    }

    public void update(final Integer productId, final ProductDTO productDTO) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productDTO, product);
        productRepository.save(product);
    }

    public void delete(final Integer productId) {
        productRepository.deleteById(productId);
    }

    private ProductDTO mapToDTO(final Product product, final ProductDTO productDTO) {
        productDTO.setProductId(product.getProductId());
        productDTO.setProductTitle(product.getProductTitle());
        productDTO.setProductLprice(product.getProductLprice());
        productDTO.setProductLink(product.getProductLink());
        productDTO.setProductImage(product.getProductImage());
        productDTO.setProductBrand(product.getProductBrand());
        productDTO.setProductCategory1(product.getProductCategory1());
        productDTO.setProductCategory2(product.getProductCategory2());
        productDTO.setProductCategory3(product.getProductCategory3());
        productDTO.setProductCategory4(product.getProductCategory4());
        productDTO.setProductType(product.getProductType());
        productDTO.setCreatedAt(product.getCreatedAt());
        productDTO.setUpdatedAt(product.getUpdatedAt());
        return productDTO;
    }

    private Product mapToEntity(final ProductDTO productDTO, final Product product) {
        product.setProductTitle(productDTO.getProductTitle());
        product.setProductLprice(productDTO.getProductLprice());
        product.setProductLink(productDTO.getProductLink());
        product.setProductImage(productDTO.getProductImage());
        product.setProductBrand(productDTO.getProductBrand());
        product.setProductCategory1(productDTO.getProductCategory1());
        product.setProductCategory2(productDTO.getProductCategory2());
        product.setProductCategory3(productDTO.getProductCategory3());
        product.setProductCategory4(productDTO.getProductCategory4());
        product.setProductType(productDTO.getProductType());
        product.setCreatedAt(productDTO.getCreatedAt());
        product.setUpdatedAt(productDTO.getUpdatedAt());
        return product;
    }

    public ReferencedWarning getReferencedWarning(final Integer productId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NotFoundException::new);
        final Cart productCart = cartRepository.findFirstByProduct(product);
        if (productCart != null) {
            referencedWarning.setKey("product.cart.product.referenced");
            referencedWarning.addParam(productCart.getCartId());
            return referencedWarning;
        }
        final OrderDetails productOrderDetails = orderDetailsRepository.findFirstByProduct(product);
        if (productOrderDetails != null) {
            referencedWarning.setKey("product.orderDetails.product.referenced");
            referencedWarning.addParam(productOrderDetails.getOrderDetailsNo());
            return referencedWarning;
        }
        return null;
    }

}
