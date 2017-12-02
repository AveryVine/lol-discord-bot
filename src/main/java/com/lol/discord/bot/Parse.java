package com.lol.discord.bot;

import org.json.*;

public class Parse {

	private int firstID;
	private int accountID;
	private String sumName;
	private int profIcon;
	private long revDate;
	private int sumLvl;
	
	private String tier;
	private int win, losses, lPoint;
	private float totalWr;
	private String tag, rank;

	/*
	 * Sample input String
	 * {"id":41197038,"accountId":202770521,"name":"Ace Damasos","profileIconId":
	 * 3186,"revisionDate":1512194457000,"summonerLevel":38}
	 */

	public String getSummonerName() {
		return sumName;
	}
	
	public int getSummonerLevel() {
		return sumLvl;
	}
	
	public int getFirstID() {
		return firstID;
	}

	public void parseLookup(JSONObject input) {
		firstID = input.getInt("id");
		accountID = input.getInt("accountId");
		sumName = input.getString("name");
		profIcon = input.getInt("profileIconId");
		revDate = input.getLong("revisionDate");
		sumLvl = input.getInt("summonerLevel");
	}

	public String parseRank(JSONObject input) {
		tier = input.getString("tier");
		JSONArray arr = input.getJSONArray("entries");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject player = arr.getJSONObject(i);
			int playerID = player.getInt("playerOrTeamId");
			if (playerID == firstID) {
				rank = player.getString("rank");
				lPoint = player.getInt("leaguePoints");
				win = player.getInt("wins");
				losses = player.getInt("losses");
				totalWr = win / (win + losses) * 100;
				break;
			}
		}
		return tier + " " + rank + " " + lPoint + "\n " + " Your total games played is " + (win + losses) + "\n"
				+ "(" + win + ", " + losses + ", " + String.format("%.2f", totalWr) + ")";
	}
	
}


