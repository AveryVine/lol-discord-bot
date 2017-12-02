package com.lol.discord.bot;

import org.json.*;

public class Parse {

	public int firstID;
	public int accountID;
	public String sumName;
	public int profIcon;
	public long revDate;
	public int sumLvl;
	
	public String tier;
	public int win, losses, lPoint;
	public String tag, rank;
	public boolean winStreaks;

	/*
	 * Sample input String
	 * {"id":41197038,"accountId":202770521,"name":"Ace Damasos","profileIconId":
	 * 3186,"revisionDate":1512194457000,"summonerLevel":38}
	 */

	public String parseLookup(JSONObject input) {
		varSetUp(input);

		String output = sumName + "\n" + "Level: " + sumLvl + "\n";

		return output;
	}

	public void varSetUp(JSONObject input) {
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
				break;
			}
		}
		return tier + " " + rank;
	}
	
}
