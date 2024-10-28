package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CommunityDTO {

    private Integer postId;

    @NotNull
    private Boolean title;

    @NotNull
    private String contents;

    private Integer good;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Integer user;

}
