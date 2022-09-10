package entitites;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateRequestBody {
    private String username;
    private String email;
    private String password;
}
