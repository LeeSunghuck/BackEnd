package sole_paradise.sole_paradise.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
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
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @NotNull
    private String address;

}
