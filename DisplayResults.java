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

import java.util.ArrayList;

public class DisplayResults implements Runnable {
	
	static String winner;							//winner variable stores the name of the winner (if any)
	
	ArrayList<Player> AL_player;					//List of all the players playing the game
	
	DisplayResults(ArrayList<Player> myList)		//Constructor for the DisplayResults class
	{
		AL_player=myList;
	}
	
	public void run()								/*This is the run method concrete implementation under the Runnable interface 
	  												  It will be executed when the thread for each player is started.*/
	{
		try 
		{
			while(true)								/*The thread of DisplayResults will wait upon the WinnerFoundFlag and NoWinnerFlag i.e. either until
													  a winner is found or no winner is found using an infinite while loop.*/
			{
				Thread.sleep(500);
				if(Moderator.NoWinnerFlag==1)
					break;
				if(Moderator.WinnerFoundFlag==1)
					break;
			}

			if(DriverClass.displayScoreFlag==1)		//When a winner is found, print the scores of all the players and declare the winner.
			{
				System.out.println("****GAME OVER****");
				for(int i=0; i<AL_player.size(); i++)
				{
					System.out.println("Score of "+ AL_player.get(i).getPlayerName() + " is " + AL_player.get(i).getPlayerScore());
				}
				System.out.println("Winner is "+ winner);
			}
			else									//When a winner is not found, print the scores of all the players and print that no one is the winner.
			{
				System.out.println("****GAME OVER****");
				for(int i=0; i<AL_player.size(); i++)
				{
					System.out.println("Score of "+ AL_player.get(i).getPlayerName() + " is " + AL_player.get(i).getPlayerScore());
				}
				System.out.println("No one is the winner");
				
			}
		}
		catch (InterruptedException e) {
	         System.out.println("Thread has been interrupted.");
	    }
		return;
	}

}
