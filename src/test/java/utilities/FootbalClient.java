package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class FootbalClient {

    public FootbalClient() {

    }

    public Response get(String endpoint) {
        return RestAssured
                .given()
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
    public <T> Response get(String endpoint, HashMap<String, T> queryParams) {
        RequestSpecification request = RestAssured.given();

        if (queryParams != null) {
            for (Map.Entry<String, T> entry : queryParams.entrySet()) {
                request.queryParam(entry.getKey(), entry.getValue());
            }
        }

        return request
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }


    public Response post(String endpoint, Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response put(String endpoint, Object body) {
        return RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public Response delete(String endpoint) {
        return RestAssured
                .given()
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    public RequestSpecification given() {
        return RestAssured.given();
    }
}
