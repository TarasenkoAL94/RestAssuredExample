import config.FootballConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import utilities.FootbalClient;

import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class FootballTest extends FootballConfig {
    FootbalClient fClient = new FootbalClient();

    @Test
    public void getDetailsForSingleArea() {
        HashMap<String, Integer> queryParams = new HashMap<>();
        queryParams.put("areas", 2000);
        fClient.get("/areas", queryParams);
    }

    @Test
    public void getDetailsForMultipleAreas() {
        String areas = "2076,2077,2080";
        given()
                .queryParam("areas", areas)
                .when()
                .get("/areas");
    }

    @Test
    public void getDateFounded() {
        given()
                .when()
                .get("teams/57")
                .then()
                .body("founded", equalTo(1886));
    }

    @Test
    public void getFirstTeamName() {
        given()
                .when()
                .get("competitions/2021/teams")
                .then()
                .body("teams.name[0]", equalTo("Arsenal FC"));
    }

    @Test
    public void getAllTeamData() {
        String responceBody = get("teams/57").asString();
        System.out.println(responceBody);
    }

    @Test
    public void getAllTeamDataWithChecks() {
        Response response = fClient.get("teams/57");
//                get("teams/57")
//                        .then()
//                        .contentType(ContentType.JSON)
//                        .extract().response();

        String jSonResponseAsString = response.asString();
        System.out.println(jSonResponseAsString);
    }

    @Test
    public void extratHeaders() {
        Response response =
                get("teams/57")
                        .then()
                        .extract().response();

        String contentTypeHeader = response.getContentType();
        System.out.println(contentTypeHeader);
        System.out.println(response.getHeader("X-API-Version"));
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName = get("competitions/2021/teams").jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamName(){
        Response response =
                get("competitions/2021/teams")
                        .then()
                        .extract().response();

        List<String> teamNames = response.path("teams.name");
        for (String name : teamNames){
            System.out.println(name);
        }
    }
}
