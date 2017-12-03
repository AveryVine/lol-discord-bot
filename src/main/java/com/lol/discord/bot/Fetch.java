package com.lol.discord.bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.*;

public class Fetch {

	public final String apiKey = "RGAPI-148202d3-e020-4baa-a09a-58e0bb964d98";

	public String retrieveData(String keyword, String body) {
		String output = "";
		if ("lookup".equalsIgnoreCase(keyword) || "rank".equalsIgnoreCase(keyword)
				|| "totalMastery".equalsIgnoreCase(keyword) || "icon".equalsIgnoreCase(keyword)) {
			URI uri = null;
			try {
				if ("lookup".equalsIgnoreCase(keyword)) {
					uri = new URI("https", "na1.api.riotgames.com", "/lol/summoner/v3/summoners/by-name/" + body,
							"api_key=" + apiKey, null);
				} else if ("rank".equalsIgnoreCase(keyword)) {
					uri = new URI("https", "na1.api.riotgames.com", "/lol/league/v3/leagues/by-summoner/" + body,
							"api_key=" + apiKey, null);
				} else if ("totalMastery".equalsIgnoreCase(keyword)) {
					uri = new URI("https", "na1.api.riotgames.com",
							"/lol/champion-mastery/v3/scores/by-summoner/" + body, "api_key=" + apiKey, null);
				} else if ("icon".equalsIgnoreCase(keyword)) {
					uri = new URI("https", "na1.api.riotgames.com", "/lol/static-data/v3/versions", "api_key=" + apiKey,
							null);
				}
				if (uri != null) {
					URL url = uri.toURL();
					output = readJsonFromUrl(url);
					if ("Bad request".equals(output)) {
						output = retrieveData("lookup", body);
						if (!"Not found".equals(output) && !"Forbidden".equals(output)
								&& !"Something went wrong!".equals(output)) {
							JSONObject json = new JSONObject(output);
							LolDiscordBot.parse.parseLookup(json);
							output = retrieveData("rank", Integer.toString(LolDiscordBot.parse.getFirstID()));
						} else if ("Not found".equals(output)) {
							output = "Invalid summoner name!";
						} else if ("Forbidden".equals(output)) {
							output = "Access forbidden, contact the developer!";
						}
					}
				}
			} catch (Exception e) {
				output = "Something went wrong!\n" + e.getStackTrace().toString();
			}
		}
		System.out.println("Returning output: " + output);
		return output;
	}

	public String readJsonFromUrl(URL url) throws IOException, JSONException {
		System.out.println("Attempting to reach url: " + url);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		final int responseCode = connection.getResponseCode();
		switch (responseCode) {
		case 404:
			return "Not found";
		case 403:
			return "Forbidden";
		case 400:
			return "Bad request";
		case 200:
			InputStream is = url.openStream();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
				System.out.println("Reading in...");
				String jsonText = readAll(rd);
				System.out.println("Received: " + jsonText);
				return jsonText;
			} finally {
				is.close();
			}
		}
		return "Something went wrong!";

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
