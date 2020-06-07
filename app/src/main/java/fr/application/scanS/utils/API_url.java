package fr.application.scanS.utils;

import java.text.Normalizer;

public class API_url {
    public static final String url = "https://lyscanapp-5e288638.localhost.run/";

    // remove accent, the escape and lower letters
    public static String format(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        string = string.replace(" ", "_");
        string = string.toLowerCase();
        return string;
    }
}
