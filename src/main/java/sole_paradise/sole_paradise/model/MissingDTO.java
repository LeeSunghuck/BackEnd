package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MissingDTO {

    private Integer missingId;

    @NotNull
    @Size(max = 100)
    private String alarmName;

    @NotNull
    @Size(max = 100)
    private String location;

    @NotNull
    private Integer alertRadiusKm;

    @NotNull
    private OffsetDateTime missingDate;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    @Size(max = 2000)
    private String missingDetails;

    @NotNull
    private Integer missingStatus;

    @NotNull
    private Integer pet;

    @NotNull
    private Integer walkroute;

}
