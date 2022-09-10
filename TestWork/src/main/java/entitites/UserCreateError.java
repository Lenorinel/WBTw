package entitites;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateError {
    private boolean success;
    private List<String> message;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        UserCreateError obj = (UserCreateError) o;
        return this.success == obj.success
                && this.message.equals(obj.message);
    }
}
