package com.lol.discord.bot;

public class Parse {	
	
	public String firstid;
	public String accountID;
	public String sumName;
	public String profIcon;
	public String RevDate;
	public String sumlvl;

	/*	
 *  Sample input String
	{"id":41197038,"accountId":202770521,"name":"Ace Damasos","profileIconId":3186,"revisionDate":1512194457000,"summonerLevel":38}
*/	
	
	public String parseLookup(String input) {
		varSetUp(input);
		
		String output = sumName + "\n" + "Level: " + sumlvl;
		
		return output;
	}
	
	public void varSetUp(String input) {
		//split string array length should always be 6 total 
		String[] tempStrings = input.split(","); //splits input into array of strings at each ,
		
		//pretreats each chunk to isolate what we want
		firstid = tempStrings[0];
		firstid = firstid.substring(5);
		
		accountID = tempStrings[1];
		accountID = accountID.substring(11);
		
		sumName = tempStrings[2];
		sumName = sumName.substring(7);
		sumName = sumName.replaceAll("'","");
		
		profIcon = tempStrings[3];
		profIcon = profIcon.substring(15);
		
		RevDate = tempStrings[4];
		RevDate = RevDate.substring(14);
		
		sumlvl = tempStrings[5];
		sumlvl = sumlvl.substring(15);
		sumlvl = sumlvl.replaceAll("}","");
		
	}
	
}

