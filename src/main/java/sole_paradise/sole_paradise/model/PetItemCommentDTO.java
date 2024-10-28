package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PetItemCommentDTO {

    private Integer commentId;

    @NotNull
    private String comment;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Integer petItem;

    @NotNull
    private Integer user;

}
