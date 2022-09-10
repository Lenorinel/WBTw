package fragments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entitites.UserCreateError;
import entitites.UserCreateRequestBody;
import entitites.UserCreateResponseBody;
import io.restassured.response.Response;
import providers.UserCreateErrorProvider;
import providers.UserDataProvider;
import requests.UserRequest;


public class RegistrationFragments {
    private final ObjectMapper MAPPER = new ObjectMapper();

    //метод, возвращающий объект с корректными данными для регистрации нового пользователя
    public UserCreateRequestBody getCorrectUserRegistrationBody() {
        return UserDataProvider.createNewUserData();
    }

    //получение объекта ответа на запрос создания пользователя
    public Response getResponseUserCreate(UserCreateRequestBody requestBody) {
        Response response = new UserRequest().postCreateUser(requestBody);
        return response;
    }

    //проверка на успешный код ответа от сервера
    public boolean checkSuccessResponseCode(Response response) {
        return response.getStatusCode() == 200;
    }

    //проверка на 400 код ответа от сервера
    public boolean checkBadRequestResponseCode(Response response) {
        return response.getStatusCode() == 400;
    }

    public boolean successBodyesReqAndRespCompare(UserCreateRequestBody requestBody, Response response) {
        UserCreateResponseBody responseBody =
                (UserCreateResponseBody)getParsedSubscriptionsResponse(
                        response.getBody().asString(), UserCreateResponseBody.class);
        return responseBody.getSuccess()
                && requestBody.getEmail().equals(responseBody.getDetails().getEmail())
                && requestBody.getUsername().equals(responseBody.getDetails().getUsername());
    }

    public boolean checkCreateUserError(String errorType, Response response) {
        UserCreateError userCreateErrorReference = UserCreateErrorProvider.getReferenceError(errorType);
        UserCreateError userCreateErrorResponse =
                (UserCreateError) getParsedSubscriptionsResponse(
                        response.getBody().asString(), UserCreateError.class);
        return userCreateErrorResponse.equals(userCreateErrorReference);
    }

    private <T> Object getParsedSubscriptionsResponse(String content, Class<T> valueType) {
        try {
            return MAPPER.readValue(content, valueType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        throw new AssertionError("Json parse exception!");
    }
}
