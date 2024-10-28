package sole_paradise.sole_paradise.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Missing {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer missingId;

    @Column(nullable = false, length = 100)
    private String alarmName;

    @Column(nullable = false, length = 100)
    private String location;

    @Column(nullable = false)
    private Integer alertRadiusKm;

    @Column(nullable = false)
    private OffsetDateTime missingDate;

    @Column(nullable = false)
    private OffsetDateTime createdAt;

    @Column
    private OffsetDateTime updatedAt;

    @Column(nullable = false, length = 2000)
    private String missingDetails;

    @Column(nullable = false)
    private Integer missingStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id", nullable = false)
    private Pet pet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walkroute_id", nullable = false)
    private WalkRoute walkroute;

}
