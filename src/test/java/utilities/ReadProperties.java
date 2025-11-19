package utilities;

import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {
    private static final String PROPERTIES_DIR ="/";
    private static final Properties props = new Properties();

    static {
        loadPropertiesFromFile();
    }

    private static void loadPropertiesFromFile(){
        try{
            InputStream propsStream;
            propsStream = ReadProperties.class.getResourceAsStream(PROPERTIES_DIR.concat("resources.properties"));
            props.load(propsStream);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProp(String key){
        String prop = props.getProperty(key);
        return (prop != null) ? prop.trim() : null;
    }
}
