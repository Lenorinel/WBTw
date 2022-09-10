package providers;

import entitites.UserCreateError;

import java.util.ArrayList;

public class UserCreateErrorProvider {
    public static UserCreateError getReferenceError(String errorType) {
        ArrayList<String> messageText = new ArrayList<>();
        switch (errorType) {
            case "username":
                messageText.add("This username is taken. Try another.");
                return UserCreateError.builder()
                        .success(false)
                        .message(messageText)
                        .build();

            case "email":
                messageText.add("Email already exists");
                return UserCreateError.builder()
                        .success(false)
                        .message(messageText)
                        .build();

            default:
                return new UserCreateError();
        }
    }
}
