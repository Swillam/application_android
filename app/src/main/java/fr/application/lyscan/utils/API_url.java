package fr.application.lyscan.utils;

import java.text.Normalizer;

public class API_url {

    public static final String url = "https://lyscan.tunnel.staqlab.com/";
    public static final String url_img = url + "image.php?image=";
    public static final String url_json = url + "json.php";

    // remove accent, the escape and lower letters
    public static String format(String string) {
        string = Normalizer.normalize(string, Normalizer.Form.NFD);
        string = string.replaceAll("[^\\p{ASCII}]", "");
        string = string.replace(" ", "_");
        string = string.toLowerCase();
        return string;
    }
}
