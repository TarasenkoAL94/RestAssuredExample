package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import utilities.ReadProperties;

import static org.hamcrest.Matchers.lessThan;

public class VideoGameConfig {

    @BeforeClass
    public static void setup(){

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ReadProperties.getProp("database.baseUrl"))
                .setBasePath(ReadProperties.getProp("database.basePath"))
                .setContentType("application/xml")
                .addHeader("Accept", "application/xml")
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
//                .expectResponseTime(lessThan(30L))
                .build();
    }
}
