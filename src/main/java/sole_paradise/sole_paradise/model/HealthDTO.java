package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HealthDTO {

    private Integer healthId;

    @NotNull
    private OffsetDateTime visitedDate;

    @NotNull
    private String notes;

    private OffsetDateTime healthDate;

    private OffsetDateTime nextCheckupDate;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Integer alarmStatus;

    @NotNull
    private Integer pet;

}
