package com.lol.discord.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.json.*;

public class Fetch {

	public final String apiKey = "RGAPI-48dff335-0201-4fd0-9442-9cced6f696b2";
	private final Parse parse = new Parse();

	public String retrieveData(String keyword, String body) {
		String message = "Something went wrong!";
		if ("lookup".equalsIgnoreCase(keyword)) {
			message = "";
			try {
				JSONObject json = readJsonFromUrl(
						"https://na1.api.riotgames.com/lol/summoner/v3/summoners/by-name/" + body + "?api_key=" + apiKey);
				message = json.toString();
			} catch (Exception e) {
				message = "Something went wrong!\n" + e.getStackTrace();
			}
		}
		return message;
	}

	public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		System.out.println("Reaching out to url: " + url);
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			System.out.println("Reading in...");
			String jsonText = readAll(rd);
			System.out.println("Received: " + jsonText);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}
}
