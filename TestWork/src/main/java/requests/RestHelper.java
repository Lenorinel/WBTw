package requests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RestHelper {
    protected String url;

    public Response post(String path, Object body) {
        return buildRequestSpecification()
                .body(body)
                .when()
                .post(path);
    }

    private RequestSpecification buildRequestSpecification() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON).setContentType(ContentType.JSON);
        return given(requestSpecBuilder
                        .setBaseUri(url)
                        .build());
    }
}
