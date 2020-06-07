package fr.application.Lyscan.utils;

import java.text.Normalizer;

public class API_url {
    public static final String url = "https://lyscanapp-11e6bb5b.localhost.run/";

    // remove accent, the escape and lower letters
    public static String format(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        string = string.replace(" ", "_");
        string = string.toLowerCase();
        return string;
    }
}
