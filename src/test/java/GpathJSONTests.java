import config.FootballConfig;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.get;

public class GpathJSONTests extends FootballConfig {

    @Test
    public void extractMapOfElementsWithFind(){
        Response response = get("competitions/2021/teams");
        Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find { it.name == 'Manchester United FC' }");

        System.out.println("map of team data:" + allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueTest(){
        Response response = get("teams/57");
        String particularPlayer = response.path("squad.find { it.id == 7784 }.name");

        System.out.println("player name:" + particularPlayer);
    }

    @Test
    public void findAllTest(){
        Response response = get("teams/57");
        List<String> listOfNames = response.path("squad.findAll { it.id >= 7784 }.name");

        for(String name : listOfNames){
            System.out.println("player name: " + name);
        }
    }

    @Test
    public void extractSingleValueWithHighestNumber(){
        Response response = get("teams/57");
        String name = response.path("squad.max { it.id }.name");

        System.out.println("player name: " + name);
    }

    @Test
    public void extractMultipleValueAndSumThem(){
        Response response = get("teams/57");
        int name = response.path("squad.collect { it.id }.sum()");

        System.out.println("sum of Ids: " + name);
    }

    @Test
    public void MapWithFindAllAndParameters() {
        String position = "Goalkeeper";
        String nationality = "Spain";

        Response response = get("teams/57");

        Map<String, ?> playerOfCertainPosition = response.path("squad.findAll { it.position == '%s' }.find { it.nationality == '%s' }",
                position, nationality);

        System.out.println("details of player: " + playerOfCertainPosition);
    }

    @Test
    public void extractMultiplePlayers() {
        String position = "Goalkeeper";
        String nationality = "Spain";

        Response response = get("teams/57");

        ArrayList<Map<String, ?>> allPlayerOfCertainPosition = response.path("squad.findAll { it.position == '%s' }.findAll { it.nationality == '%s' }",
                position, nationality);

        System.out.println("details of player: " + allPlayerOfCertainPosition);
    }
}
