package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PetDTO {

    private Integer petId;

    @NotNull
    @Size(max = 100)
    private String petName;

    @NotNull
    private Integer petAge;

    @NotNull
    @Size(max = 100)
    private String dogOrCat;

    @NotNull
    @Size(max = 100)
    private String petType;

    private String petPicture;

    @NotNull
    private Integer petWeight;

    @NotNull
    private Boolean neutering;

    @NotNull
    private Boolean petAllergy;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Boolean gender;

    @NotNull
    private Integer user;

}
