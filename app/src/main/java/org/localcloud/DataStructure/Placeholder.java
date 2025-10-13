package org.localcloud.DataStructure;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholder {
    private final String regex; //Default regex of placeholder {text}
    private final Pattern pattern;
    private final Matcher matcher;
    private final String rawString;
    private final HashMap<String, String> placeholderMap;

    public Placeholder(String rawString) {
        this.regex = "\\{([^}]+)}";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(rawString);
        this.rawString = rawString;

        placeholderMap = computePlaceholderMap();
    }

    public Placeholder(String rawString, String regex) {
        this.regex = regex;
        pattern = Pattern.compile(this.regex);
        matcher = pattern.matcher(rawString);
        this.rawString = rawString;

        placeholderMap = computePlaceholderMap();
    }

    private HashMap<String, String> computePlaceholderMap() {
        HashMap<String, String> placeholderMap = new HashMap<>();
        while (matcher.find()) {
            int s = matcher.start();
            int e = matcher.end();

            String key = rawString.substring(s+1, e-1);
            placeholderMap.put(key, null);
        }

        return placeholderMap;
    }

    public void setValue(String key, String value) {
        if(!placeholderMap.containsKey(key))
        {
            throw new NullPointerException("invalid key");
        }
        this.placeholderMap.put(key, value);
    }

    public String getValue(String key) {
        return this.placeholderMap.get(key);
    }

    public boolean hasKey(String key) {
        return this.placeholderMap.containsKey(key);
    }

    public String replacePlaceholders() {
        String tr = rawString;

        for(String key: placeholderMap.keySet()) {
            CharSequence to = placeholderMap.get(key);
            tr = tr.replace("{"+key+"}", to);
        }

        return tr;
    }
}
