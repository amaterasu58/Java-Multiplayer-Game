/*
 *OBJECT ORIENTED PROGRAMMING (CS F213)
 *
 *Code written by:
 *MONIS MUSHTAQUE
 *
 *monis.mushtaque@gmail.com
 *
 *Date: 19th July 2020
*/

package Multiplayer_Game;

import java.util.*;

class RandomNumberGenerator {
	
	int getRandNum()									//This method generates and returns a random number using the Random class present in the util library
	{
		Random R = new Random();
		int randNum = R.nextInt(50);
		return randNum;
	}

	
	ArrayList<Integer> getAL() 							//This method returns an arraylist of 10 random numbers for each player
	{
		int i;
		ArrayList<Integer> AL=new ArrayList<Integer>();
		for(i=0;i<10;i++)
		{
			int k=getRandNum();
			AL.add(k);
		}
		return AL;
	}
}

