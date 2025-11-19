package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import utilities.FootbalClient;
import utilities.ReadProperties;

public class FootballConfig {

    @BeforeClass
    public static void setup(){

        FootbalClient fClient = new FootbalClient();

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ReadProperties.getProp("football.baseUrl"))
                .setBasePath(ReadProperties.getProp("football.basePath"))
                .addHeader("X-Auth-Token", ReadProperties.getProp("football.authToken"))
                .addHeader("X-VideoGame-Control", "minified")
                .setAccept("application/json")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();



    }
}
