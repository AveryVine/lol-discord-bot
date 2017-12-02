package com.lol.discord.bot;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import org.json.*;

public class LolDiscordBot {

	public static void main(String args[]) {
		final Fetch fetch = new Fetch();
		final Parse parse = new Parse();
		String token = "Mzg2NTc2MzM4Mjg4NTA4OTI4.DQR6zA.N74UuXBlgjWApmBFnMRiHITTcnw";
		// See "How to get the token" below
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
							String output = "", returnMessage = "";
							if ("lookup".equalsIgnoreCase(keyword)) {
								if (body != null && !"".equals(body)) {
									output = fetch.retrieveData(keyword, body);
									// This is all the good stuff
									if (!"Not found".equals(output) && !"Forbidden".equals(output)
											&& !"Something went wrong!".equals(output)) {
										System.out.println(output);
										JSONObject json = new JSONObject(output);
										output = json.toString();
										if ("lookup".equalsIgnoreCase(keyword)) {
											returnMessage = parse.parseLookup(json);
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

}