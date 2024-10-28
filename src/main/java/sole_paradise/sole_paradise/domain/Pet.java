package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Pet {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer petId;

    @Column(nullable = false, length = 100)
    private String petName;

    @Column(nullable = false)
    private Integer petAge;

    @Column(nullable = false, length = 100)
    private String dogOrCat;

    @Column(nullable = false, length = 100)
    private String petType;

    @Column(columnDefinition = "longtext")
    private String petPicture;

    @Column(nullable = false)
    private Integer petWeight;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean neutering;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean petAllergy;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
    private Boolean gender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "pet")
    private Set<Missing> petMissings;

    @OneToMany(mappedBy = "pet")
    private Set<Health> petHealths;

}
