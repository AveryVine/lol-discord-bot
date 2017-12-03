package com.lol.discord.bot;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import org.json.*;

public class LolDiscordBot {

	final static Fetch fetch = new Fetch();
	final static Parse parse = new Parse();

	public static void main(String args[]) {
		String token = "Mzg2NTc2MzM4Mjg4NTA4OTI4.DQR6zA.N74UuXBlgjWApmBFnMRiHITTcnw";
		DiscordAPI api = Javacord.getApi(token, true);
		// connect
		api.connect(new FutureCallback<DiscordAPI>() {
			public void onSuccess(DiscordAPI api) {
				// register listener
				api.registerListener(new MessageCreateListener() {
					public void onMessageCreate(DiscordAPI api, Message message) {
						// check the content of the message
						System.out.println("Incoming message: " + message.getContent());
						String msg = message.getContent().trim();
						if (msg.indexOf(" ") != -1) {
							String keyword = msg.substring(0, msg.indexOf(" ")).trim();
							String body = msg.substring(msg.indexOf(" ") + 1, msg.length()).trim();
							String returnMessage = "";
							if ("lookup".equalsIgnoreCase(keyword) || "rank".equalsIgnoreCase(keyword)) {
								returnMessage = fetchData(keyword, body);
							}
							// Reply with the message
							message.reply(returnMessage);
						}
					}
				});
			}

			public void onFailure(Throwable t) {
				t.printStackTrace();
			}
		});
	}

	public static String fetchData(String keyword, String body) {
		String output = "", returnMessage = "";
		if (body != null && !"".equals(body)) {
			output = fetch.retrieveData(keyword, body);
			// This is all the good stuff
			if (!"Not found".equals(output) && !"Forbidden".equals(output) && !"Something went wrong!".equals(output)) {
				if ("lookup".equalsIgnoreCase(keyword)) {
					JSONObject json = new JSONObject(output);
					parse.parseLookup(json);
					returnMessage = parse.getSummonerName() + " (level " + parse.getSummonerLevel() + ")\n";
					returnMessage += "Mastery Score: " + fetchData("totalMastery", Integer.toString(parse.getFirstID()))
							+ "\n";
//					returnMessage += fetchData("rank", Integer.toString(parse.getFirstID()));
					output = fetchData("icon", null);
					returnMessage += "Summoner Icon: " + "http://ddragon.leagueoflegends.com/cdn/"
							+ parse.getVersion(output) + "/img/profileicon/"
							+ Integer.toString(parse.getprofIcon()) + ".png";
					if (returnMessage.indexOf(parse.getSummonerName()) != returnMessage
							.lastIndexOf(parse.getSummonerName())) {
						returnMessage = returnMessage.substring(0, returnMessage.lastIndexOf(parse.getSummonerName()))
								+ returnMessage.substring(returnMessage.lastIndexOf(parse.getSummonerName())
										+ parse.getSummonerName().length() + 1, returnMessage.length());
					}
				} else if ("rank".equalsIgnoreCase(keyword)) {
					output = output.substring(1, output.length() - 1);
					if (output == null || "".equals(output)) {
						returnMessage += "UNRANKED";
					} else {
						JSONObject json = new JSONObject(output);
						returnMessage = parse.getSummonerName() + "\n";
						returnMessage += parse.parseRank(json);
					}
				} else if ("totalMastery".equalsIgnoreCase(keyword)) {
					returnMessage += output;
				}
			}
			// Below this point is error handling
			else if ("Not found".equals(output)) {
				if ("lookup".equalsIgnoreCase(keyword))
					returnMessage = "Invalid summoner name!";
				else
					returnMessage = "Something went wrong!";
			} else if ("Forbidden".equals(output)) {
				returnMessage = "Access forbidden, contact the developer!";
			} else if ("Something went wrong!".equals(output)) {
				returnMessage = output;
			}
		} else {
			if ("lookup".equals(keyword))
				returnMessage = "Not a valid summoner name!";
			else
				returnMessage = "Something went wrong!";
		}
		return returnMessage;
	}

}