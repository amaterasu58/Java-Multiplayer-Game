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

public class Moderator implements Runnable {
	
	static int roundNumber;								  //This will help the threads to read the correct random number at each round.
	static int WinnerFoundFlag;							  //This flag will tell us whether a winner is found (equal to 1) or not (remains zero).
	static int NoWinnerFlag;							  //This flag will tell us that no winner is found at the end of the game when it's value becomes equal to 1
	static int no_of_players=10;						  //The total number of players playing the game
	
	static ArrayList<Integer> randomNumbers=new ArrayList<Integer>(); //This is the ArrayList which the moderator shall populate and the players will read (one by one).
	
	int newNum;
	
	RandomNumberGenerator RNG=new RandomNumberGenerator(); //Creating an object of the RandomNumberGeneratorClass
	
	private static Moderator mod=new Moderator();		   //I'll be implementing the SINGLETON DESIGN PATTERN so that only one instance of moderator can be created
	
	public static Moderator getSingleInstance()			   //This method will return an instance of Moderator class i.e. Moderator object.
	{
		return mod;
	}
	
	private Moderator()									   //Constructor for the Moderator class is private so that the Moderator class cannot be instantiated.
	{}
	
	int generateNewNum()								   //The Moderator will use this function to generate a new number each round.
	{
		newNum=RNG.getRandNum();
		randomNumbers.add(newNum);
		return newNum;
	}
	
	void displayNewRandNum()							   //The Moderator will use this function to display a new number each round.
	{
		if(Moderator.NoWinnerFlag==1 || Moderator.WinnerFoundFlag==1) //New number will only be generated when no winner is found yet.
			return;
		System.out.println("Moderator generated: "+ generateNewNum());
	}
	
	static int lock=1; 										/*This lock will prevent the player thread to read the newly generated number before it gets displayed.
															  Like a semaphore.*/
		
	public void run()										/*This is the run method concrete implementation under the Runnable interface 
	  														  It will be executed when the thread for Moderator is started.*/
	{
		try 
		{
			for(int i=0; i<10; i++)
	        {
				if(Moderator.WinnerFoundFlag==1)
				{
					lock=0;
					return;
				}
				lock=1; 							/*This will prevent the player threads from entering the 'critical section' i.e. displaying the 
						  							  newly generated random number */
				displayNewRandNum();
				lock=0;								//When the value of lock becomes 0, other players can see the newly generated random number.
				Thread.sleep(1500);
				roundNumber++;						//Before beginning the next round, we increase the roundNumber variable. 
	        }
			if(Moderator.WinnerFoundFlag!=1)
			{
				Moderator.NoWinnerFlag=1;
			}
	    }
		catch (InterruptedException e) {
	         System.out.println("Thread has been interrupted.");
	    }
		return;
	}

}

