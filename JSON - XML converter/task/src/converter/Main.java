package converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();

        try {
            JSONObject js = new JSONObject(input);
            String xml = XML.toString(js);

            System.out.println(xml);
        } catch (JSONException ex) {

            System.out.println(XML.toJSONObject(input));
        }

    }
}
