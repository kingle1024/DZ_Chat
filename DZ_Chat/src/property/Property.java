package property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class Property {
    public static HashMap<String, String> list() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("resources/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, String> map = new HashMap<>();

        for(Object key : properties.keySet()){
            map.put(key.toString(), properties.getProperty((String)key));
        }
        return map;
    }

    public static void main(String[] args) {
        File file = new File("resource");

        System.out.println(file.getAbsolutePath());
    }
}
