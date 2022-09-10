package providers;

import entitites.UserCreateRequestBody;
import org.apache.commons.lang3.RandomStringUtils;

public class UserDataProvider {

    public static UserCreateRequestBody createNewUserData() {
        //в дальнейшем, для бОльшего охвата проверок count и шаблон email можно рандомизировать для каждого
        //нового запроса на создание тела user/create
        return UserCreateRequestBody.builder()
                .password(RandomStringUtils.random(10, true, true))
                .username(RandomStringUtils.random(10, true, true))
                .email(RandomStringUtils.random(10, true, true)
                    + "@gmail.com")
                .build();
    }
}
