package com.lol.discord.bot;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

public class LolDiscordBot {

	public static void main(String args[]) {
		final Fetch fetch = new Fetch();
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
							String output = fetch.retrieveData(keyword, body);
							message.reply(output);
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