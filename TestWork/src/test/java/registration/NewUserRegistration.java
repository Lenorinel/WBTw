package registration;

import entitites.UserCreateRequestBody;
import fragments.RegistrationFragments;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

public class NewUserRegistration {
    private static final RegistrationFragments fragments = new RegistrationFragments();
    private static UserCreateRequestBody requestBody;

    @BeforeEach
    public void setup(TestInfo info) {
        String tag = info.getTags().toString();
        if (tag.contains("random")) {
            requestBody = fragments.getCorrectUserRegistrationBody();
        } else if (tag.contains("hardcode")) {
            //За неимением доступа к базе, откуда я мог бы взять уже существующего пользака,
            //я добавил пользователя созданного мной ранее,
            //на рабочем проекте я бы прикрутил фрагмент, который бы доставал запись из бд и парсил в UserCreateRequestBody
            requestBody = UserCreateRequestBody.builder()
                    .email("123tes1t@gmail.com")
                    .username("alreadytest1")
                    .password("12355")
                    .build();
        }
    }

    @Test
    @Tag(value = "random")
    @DisplayName("Регистрация пользователя с корректными данными в системе")
    public void smokeRegistrationNewUser() {
        Response response = fragments.getResponseUserCreate(requestBody);
        Assertions.assertTrue(fragments.checkSuccessResponseCode(response));
        Assertions.assertTrue(fragments.successBodyesReqAndRespCompare(requestBody, response));
        //Дополнительно, я бы добавил проверку на наличие данной записи в бд зареганных пользователей
    }

    @Test
    @Tag("hardcode")
    @DisplayName("Попытка повторной регистрации уже зареганого пользователя с полным совпадением данных")
    public void negativeTryToRegDublicateDataUser() {
        Response response = fragments.getResponseUserCreate(requestBody);
        Assertions.assertTrue(fragments.checkBadRequestResponseCode(response));
        Assertions.assertTrue(fragments.checkCreateUserError("username",response));
    }

    @Test
    @Tag("random")
    @DisplayName("Попытка регистрации пользователя с новым username, password, и имеющимся в системе email")
    public void negativeTryDublicateOnlyEmail() {
        //Аналогично, в рабочем проекте в прекондишине добавил бы получение существующего email через запрос
        //к базе созданных пользаков и использовал его далее
        requestBody.setEmail("123tes1t@gmail.com");

        Response response = fragments.getResponseUserCreate(requestBody);
        Assertions.assertTrue(fragments.checkBadRequestResponseCode(response));
        Assertions.assertTrue(fragments.checkCreateUserError("email",response));
    }

    //Тут должен быть блок AfterEach
    //в котором был бы метод, который по объекту request body
    //удалял бы в бд созданных автотестами пользаков, во избежание переполнения базы мусорными данными
}
