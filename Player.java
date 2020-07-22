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

class Player implements Runnable {
	
	static int playerTerminate;				  /*This static variable will tell us whether the player thread should continue to run or not.
												When a winner is found, it's value becomes 1, indicating to stop the game.*/
	
	private String playerName;                //Name of the player
	private ArrayList<Integer> cards;	      //List of the cards/tokens for each player
	private int score=0;                      //Score is the number of matches that the player get
	Moderator playerMod;				      //The moderator of the game
	
	ArrayList<Integer> alreadyEncounteredNumbers=new ArrayList<Integer>(); //This list will store the list of numbers already encountered in order to remove redundancy.
	
	Player(String name, Moderator m)	      //Constructor for the Player class
	{
		playerName=name;
		playerMod=m;
	}
	
	String getPlayerName()				       //Getter function to get the name of the player
	{
		return playerName;
	}
	
	int getPlayerScore()				  	   //Getter function to get the score of the player
	{
		return score;
	}
	
	void setPlayerCards(ArrayList<Integer> cardList)	//Setter function to set the cards of the player
	{
		cards=cardList;
	}
	
	void displayPlayerCards()					//This function displays the cards of a player
	{
		System.out.print(playerName+": ");
		System.out.print(cards);
		System.out.println();	
	}
	
	int searchCards(int number)					//This function will be used to search a given number if it is present in the set of cards or not
	{
		try {
			for (int i = 0; i < cards.size(); i++) 
			{
				if(number==cards.get(i))
					return i;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Array index is out of bound.");
		}
		return -2;
	}
	
	int searchAlreadyEncounteredList(int number)					//This function will be used to search a given number if it is present in the set of cards or not
	{
		try {
			for (int i = 0; i < alreadyEncounteredNumbers.size(); i++) 
			{
				if(number==alreadyEncounteredNumbers.get(i))
					return i;
			}
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Array index is out of bound.");
		}
		return -2;
	}
	
	void strikeOutNumber(int num)				/*This function will remove the card if the value on the card is equal to the newly generated number 
												  by the moderator and update the score else do nothing.*/
	{
		if(Player.playerTerminate==1)
			return;
		
		int previous=searchAlreadyEncounteredList(num);
		if(previous>=0)							//If the number is found in the previously encountered numbers list, then no need to strike it out again. (RULE)
			return;

		int index=searchCards(num);				//index gives us the index of the element if it exists in the list of cards else gives garbage value (negative value)
		
		if(index>=0)
		{
			score++;
			//System.out.println(playerName+" has "+ num+ " and his/her score is "+ score);
			if(score==3)
			{
				DisplayResults.winner=playerName;
				Moderator.WinnerFoundFlag=1;
				DriverClass.displayScoreFlag=1;
				Player.playerTerminate=1;
				return;
			}
			alreadyEncounteredNumbers.add(num);				//Adding the matched number from the already encountered numbers list 
			return;
		}
	}
	
	void processNewNum()						//This function will process the newly generated random number by the moderator
	{
		try {
			int newNum=Moderator.randomNumbers.get(Moderator.roundNumber);
			strikeOutNumber(newNum);
		}
		catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Array index is out of bound.");
		}
	}
	
	public void run()							/*This is the run method concrete implementation under the Runnable interface 
												  It will be executed when the thread for each player is started.*/
	{
		if(Player.playerTerminate==1)			//If any player has won, this will stop the thread of other players.
			return;
		try 
		{
			for(int i=0; i<10; i++)
            {
				if(Player.playerTerminate==1)
					return;
				while(Moderator.lock==1); 		 /*This is like a semaphore where the player thread can't see the newly generated 
										  		   random number until it gets displayed by the moderator (i.e. when lock=0).*/
				Moderator.lock=1;				 /*When a player thread is reading the ArrayList of newly generated random numbers by the moderator,
												  no other thread can access that ArrayList*/
				processNewNum();				
				Moderator.lock=0;
				if(Player.playerTerminate==1)
					return;
				Thread.sleep(1000);
            }
        }
		catch (InterruptedException e) {
			System.out.println("Thread has been interrupted.");
		}
		
	}
	
}
