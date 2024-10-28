package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class WalkRouteDTO {

    private Integer walkrouteId;

    @NotNull
    @Size(max = 100)
    private String walkrouteName;

    @NotNull
    @Size(max = 100)
    private String location;

    @NotNull
    private Integer distanceKm;

    @NotNull
    private Integer popularity;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Integer user;

}
