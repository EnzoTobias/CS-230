import com.sun.tools.javac.Main;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MessageOfTheDay {
    public static void main(String[] args) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://cswebcat.swansea.ac.uk/puzzle")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(MessageOfTheDay::parse)
                .join();

        HttpClient clientTwo = HttpClient.newHttpClient();
        HttpRequest requestTwo = HttpRequest.newBuilder().uri(URI.create("http://cswebcat.swansea.ac.uk/message?solution=" + decryptMessage.toString() )).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

    }
    public static String parse(String responseBody){
        String data = responseBody;
        String decryptMessage = "";
        for (int i = 0; i < data.length(); i++){
            int charPosition = data.charAt(i);
            int shiftKey = charPosition + 1;
            int keyVal = (charPosition - shiftKey) % 26;
            if (keyVal < 0) {
                keyVal = data.length() + keyVal;
            }
            char replaceVal = data.charAt(keyVal);
            decryptMessage += replaceVal;
        }
        decryptMessage = decryptMessage + "CS-230";
        System.out.println(decryptMessage);
        return decryptMessage;
    }

    @Override
    public String toString(String decryptMessage) {
        return decryptMessage;
    }
}






