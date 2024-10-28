package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class User {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(nullable = false)
    private String accountEmail;

    @Column(nullable = false)
    private String profileNickname;

    @Column(columnDefinition = "longtext")
    private String userPicture;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean address;

    @OneToMany(mappedBy = "user")
    private Set<Cart> userCarts;

    @OneToMany(mappedBy = "user")
    private Set<Order> userOrders;

    @OneToMany(mappedBy = "user")
    private Set<WalkRoute> userWalkRoutes;

    @OneToMany(mappedBy = "user")
    private Set<Pet> userPets;

    @OneToMany(mappedBy = "user")
    private Set<PetItem> userPetItems;

    @OneToMany(mappedBy = "user")
    private Set<PetItemComment> userPetItemComments;

    @OneToMany(mappedBy = "user")
    private Set<Community> userCommunities;

    @OneToMany(mappedBy = "user")
    private Set<CommunityComment> userCommunityComments;

}
