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

public class DriverClass extends Thread {
	
	static int displayScoreFlag;							//This static variable will be used by the DisplayResults results class to display the final results. 
	
	public static void main(String[] args) {
		
		int n=10;											//The number of players
		Moderator m=Moderator.getSingleInstance();			//By following the SINGLETON DESIGN PATTERN, we can only generate a single instance of the Moderator class.
		
		PlayerFactory pf=new PlayerFactory(n, m);			//Creating an object of player factory (FACTORY PATTERN)
		ArrayList<Player> AL_players=pf.getPlayerList();	//Calling the getPlayerList() method of PlayerFactory class to populate our list of players.
		
		System.out.println("Moderator starts the game.");
		
		try{	
			for(int i=0; i<n; i++)
			{
				Player p= AL_players.get(i);
				p.displayPlayerCards();
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Array index is out of bound.");
		}
		
		Thread modThread=new Thread(m);					    //Instantiating the Moderator thread.
		
		ArrayList<Thread> playerThreads=new ArrayList();	//ArrayList containing the threads for each player.
		
		for(int i=0; i<n; i++)
		{
			Thread t=new Thread(AL_players.get(i));			//Instantiating the threads for all the players.
			playerThreads.add(t);
		}
		
		DisplayResults dr=new DisplayResults(AL_players);
		Thread resultsThread=new Thread(dr);				//Instantiating the thread to display final results.
		
		modThread.start();									//Starting the Moderator thread.
		for(int j=0; j<n; j++)
		{
			playerThreads.get(j).start();					//Starting the threads for all the players.
		}
		resultsThread.start();								//Starting the thread to display final results.
		
	}

}
