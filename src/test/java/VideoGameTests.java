import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import objects.VideoGame;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;

public class VideoGameTests extends VideoGameConfig {

    String gameBodyJson = "{\n" +
            "  \"category\": \"Platform\",\n" +
            "  \"name\": \"Mario\",\n" +
            "  \"rating\": \"Mature\",\n" +
            "  \"releaseDate\": \"2012-05-04\",\n" +
            "  \"reviewScore\": 85\n" +
            "}";

    @Test
    public void getAllGames(){
       get(VideoGameEndpoints.ALL_VIDEO_GAMES);
    }

    @Test
    public void createNewGameByJSON(){

        given()
                .body(gameBodyJson)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();


    }

    @Test
    public void createNewGameByXML(){
        String gameBodyXml = "<VideoGameRequest>\n" +
                "\t<category>Platform</category>\n" +
                "\t<name>Mario</name>\n" +
                "\t<rating>Mature</rating>\n" +
                "\t<releaseDate>2012-05-04</releaseDate>\n" +
                "\t<reviewScore>85</reviewScore>\n" +
                "</VideoGameRequest>";

        given()
                .body(gameBodyXml)
                .contentType("application/xml")
                .accept("application/xml")
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then();


    }

    @Test
    public void updateGame(){
        given()
                .body(gameBodyJson)
        .when()
                .put("videogame/4")
        .then();
    }

    @Test
    public void deleteGame(){
        given()
                .accept("text/plain")
        .when()
                .delete("videogame/8")
        .then();
    }

    @Test
    public void getSingleGame(){
        given()
                .pathParams("videoGameId", 5)
        .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAMES)
        .then();
    }

    @Test
    public void testVideoGameSerializationJSON(){
        VideoGame videoGame = new VideoGame("Shooter", "DOOM", "Mature", "2016-01-01", 80);
        given()
                .body(videoGame)
        .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
        .then();
    }

    @Test
    public void testVideoGameSchemaXML(){
        given()
                .pathParams("videoGameId", 5)
                .accept("application/xml")
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAMES)
                .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJson(){
        given()
                .pathParams("videoGameId", 5)
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAMES)
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void jsonToPojo(){
        Response response =
                given()
                        .pathParam("videoGameId", 5)
                        .when()
                        .get(VideoGameEndpoints.SINGLE_VIDEO_GAMES);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame.toString());
    }

    @Test
    public void captureResponseTime(){
        long responceTime = get(VideoGameEndpoints.ALL_VIDEO_GAMES).time();
        System.out.println(responceTime);
    }

    @Test
    public void assertOnResponseTime(){
        get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then()
                .time(lessThan(1000L));
    }

}
