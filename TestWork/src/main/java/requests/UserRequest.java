package requests;

import entitites.UserCreateRequestBody;
import io.restassured.response.Response;

public class UserRequest extends RestHelper{
    public UserRequest() {
        this.url = "http://3.145.97.83:3333/user/";
    }

    public Response postCreateUser(UserCreateRequestBody requestBody) {
        return post(getUserRegistrationEndpoint(), requestBody);
    }

    private String getUserRegistrationEndpoint() {
        return "/create";
    }
}
