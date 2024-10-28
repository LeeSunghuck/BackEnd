package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Integer userId;

    @NotNull
    @Size(max = 255)
    private String accountEmail;

    @NotNull
    @Size(max = 255)
    private String profileNickname;

    private String userPicture;

    @NotNull
    @Size(max = 50)
    private String nickname;

    @NotNull
    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    @NotNull
    private Boolean address;

}
