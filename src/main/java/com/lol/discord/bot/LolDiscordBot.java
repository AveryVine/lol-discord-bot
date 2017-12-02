package com.lol.discord.bot;

import com.google.common.util.concurrent.FutureCallback;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

/**
 * A simple ping-pong bot.
 */
public class LolDiscordBot {

    public static void main(String args[]) {
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
                    		String msg = message.getContent();
                    		String keyword = msg.substring(0, msg.indexOf(" ")).trim();
                    		String body = msg.substring(msg.indexOf(" ") + 1, msg.length()).trim();
                        if (keyword.equalsIgnoreCase("lookup")) {
                            // reply to the message
                            message.reply("Looking up: " + body);
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