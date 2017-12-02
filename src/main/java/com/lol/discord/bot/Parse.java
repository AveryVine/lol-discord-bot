package com.lol.discord.bot;

import org.json.*;

public class Parse {	
	
	public int firstID;
	public int accountID;
	public String sumName;
	public int profIcon;
	public long revDate;
	public int sumLvl;

	/*	
 *  Sample input String
	{"id":41197038,"accountId":202770521,"name":"Ace Damasos","profileIconId":3186,"revisionDate":1512194457000,"summonerLevel":38}
*/	
	
	public String parseLookup(JSONObject input) {
		varSetUp(input);
		
		String output = sumName + "\n" + "Level: " + sumLvl;
		
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
	
}

