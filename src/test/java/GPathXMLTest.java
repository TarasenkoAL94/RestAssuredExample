import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static io.restassured.RestAssured.get;

public class GPathXMLTest extends VideoGameConfig {

    @Test
    public void getFirstGameInList(){
        Response response = get(VideoGameEndpoints.ALL_VIDEO_GAMES);

        String name = response.path("List.item.name[0]");
        System.out.println(name);
    }

    @Test
    public void getAttribute(){
        Response response = get(VideoGameEndpoints.ALL_VIDEO_GAMES);

        String attribute = response.path("List.item[0].@category");
        System.out.println(attribute);
    }

    @Test
    public void findAllXML(){
        String responseAsString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();

        List<Node> allResults = XmlPath.from(responseAsString).get("List.item.findAll { element -> return element}");
        System.out.println(allResults.get(2).get("name").toString());
    }

    @Test
    public void extractNodesWithAttributeViaFindAll(){
        String responseAsString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();

        List<Node> allShooterGames = XmlPath.from(responseAsString).get(
                "List.item.findAll { game -> def category = game.@category; category=='Shooter'}");

        System.out.println(allShooterGames.get(0).get("name").toString());
    }

    @Test
    public void getSingleNode(){
        String responseAsString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();

        Node videoGame = XmlPath.from(responseAsString).get(
                "List.item.find{ game -> def name = game.name; name=='Doom'}");

        System.out.println(videoGame.get("name").toString());
    }

    @Test
    public void deepFirstSearch(){
        String responseAsString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();

        int reviewScore = XmlPath.from(responseAsString).getInt("**.find{ it.name == 'Gran Turismo 3' }.reviewScore");

        System.out.println(reviewScore);
    }

    @Test
    public void allNodesBasedOnCondition(){
        String responseAsString = get(VideoGameEndpoints.ALL_VIDEO_GAMES).asString();

        int reviewScore =90;

        List<Node> allGamesOverScore = XmlPath.from(responseAsString).get(
                "List.item.findAll {it.reviewScore.toFloat() >= "+reviewScore+"} ");

        System.out.println(allGamesOverScore);

        System.out.println("Word chunga - " + isIsogram("chunga"));
        System.out.println("Word changa - " + isIsogram("changa"));

        String s1 = new String("42");
        String s2 = new String("42");
        String s3 = s1;
        String s4 = "42";
        String s5 = "4"+"2";

        System.out.println(s1==s2);
        System.out.println(s1==s3);
        System.out.println(s1==s4);
        System.out.println(s1.equals(s3));
        System.out.println(s1.equals(s2));
        System.out.println(s1.equals(s4));
        System.out.println(s5.equals(s4));
        System.out.println(s5.equals(s2));

    }

    public static boolean isIsogram(String str) {
        return str.length() == 0 ? true : new HashSet<String>(Arrays.asList(str.toLowerCase().split(""))).size() == str.length();
    }
}
