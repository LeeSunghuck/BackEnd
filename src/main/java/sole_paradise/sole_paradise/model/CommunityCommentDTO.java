package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommunityCommentDTO {

    private Integer commentId;

    @NotNull
    private Boolean comment;

    private OffsetDateTime updatedAt;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    private Integer post;

    @NotNull
    private Integer user;

}
