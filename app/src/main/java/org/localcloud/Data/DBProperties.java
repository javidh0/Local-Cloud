package org.localcloud.Data;

import org.localcloud.DataStructure.Pair;
import org.localcloud.DataStructure.Placeholder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DBProperties {
    private static final HashMap<String, String> dbProperties = new HashMap<>();

    public static void loadProperties() {
        try(InputStream file = DBProperties.class.getClassLoader().getResourceAsStream("dbproperties.txt")) {
            if(file == null) {
                throw new RuntimeException("dbproperties.txt not found");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(file));
            String propline;

            while ((propline = reader.readLine()) != null) {
                String[] temp = propline.split("-->");
                if(temp.length == 2) {
                    dbProperties.put(temp[0].trim(), temp[1].trim());
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRawProp(String key) {
        return dbProperties.getOrDefault(key, null);
    }

    public static Placeholder getParametrizedProp(String key) {
        if(dbProperties.containsKey(key)) {
            return new Placeholder(dbProperties.get(key));
        }
        return null;
    }
}
