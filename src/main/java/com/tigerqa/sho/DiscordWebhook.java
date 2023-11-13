package com.tigerqa.sho;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DiscordWebhook {
    public static void SendMessage(String content, String webhook_url) {
        try {
            URL url = new URL(webhook_url);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.addRequestProperty("Content-Type", "application/json");
            con.addRequestProperty("User-Agent", "Mozilla/5.0 (Linux; U; Linux i551 ) Gecko/20130401 Firefox/53.3");
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            OutputStream stream = con.getOutputStream();
            stream.write(String.format("{\"content\": \"%s\"}", content).getBytes());
            stream.flush();
            stream.close();
            con.getInputStream().close();
            con.disconnect();
        } catch (Exception ignored) {
            System.out.println("Could not log to discord, try checking the webhook_url");
        }
    }
}
