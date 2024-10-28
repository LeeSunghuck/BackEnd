package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PetItemDTO {

    private Integer petItemId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    private String description;

    private String imageUrl;

    private Integer status;

    private Integer price;

    private Integer good;

    @NotNull
    private Integer sharing;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private Boolean nanum;

    @NotNull
    private Integer user;

}
