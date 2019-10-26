package converter;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String input = sc.nextLine();
        //String input = "<jdk>1.8.9</jdk>";

        String xmlToJson = "(^<[ ]?\\w+[ ]?/>)|(<.*?>)(.*?)(<.*>)";
        String jsonToXml = "^\\{([ ]?\"\\w+\"[ ]?):([ ]?\"?.*\"?[ ]?)}$|\\{(?>\"(?>\\\\.|[^\"])*\"|[^{}\"]+)*}";

        Pattern xmlPattern = Pattern.compile(xmlToJson);
        Pattern jsonPattern = Pattern.compile(jsonToXml);
        Matcher xmlMatcher = xmlPattern.matcher(input);
        Matcher jsonMatcher = jsonPattern.matcher(input);

        StringBuilder result = new StringBuilder();

        if (xmlMatcher.find()){

            if (xmlMatcher.group(0).endsWith("/>")){
                result.append(xmlMatcher.group(0).replace("<", "{\"")
                        .replace("/>", "\":"));

                result.append(" null }");
            } else {
            result.append(xmlMatcher.group(2).replace("<", "{\"")
                    .replace(">", "\" : "));

            result.append("\"");
            result.append(xmlMatcher.group(3));
            result.append("\"");

            result.append("}");
            }
        }else if (jsonMatcher.find()){
            String firstPart = jsonMatcher.group(1).replaceAll("\"", "").trim();
            String secondPart = jsonMatcher.group(2).replaceAll("\"", "").trim();

            result.append("<");
            result.append(firstPart);

            if (jsonMatcher.group(2).trim().equals("null")){
               result.append("/>");
            } else {
                result.append(">");
                result.append(secondPart);
                result.append("</");
                result.append(firstPart);
                result.append(">");
            }
        }

        System.out.println(result.toString());
    }
}
