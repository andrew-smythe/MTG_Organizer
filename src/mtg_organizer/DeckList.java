package mtg_organizer;
import java.io.*;
import java.util.*;

/**
 * 
 * @author Andrew Smythe
 * 
 * Class:   DeckList
 * 
 * Purpose: Extends the abstract class CardList. Adds a separate list
 *          of cards which act as a sideboard (see MTG game rules).
 *
 */

public class DeckList extends CardList implements Serializable {

	private ArrayList<Card> cards;
	private ArrayList<Card> sideboard;
	
	public DeckList()
	{
		cards = new ArrayList<Card>();
		sideboard = new ArrayList<Card>();
	}
	
	/**
	 * 
	 * Adds a card to the main deck. Takes in a reference to a card
	 * object of the card to add.
	 * 
	 */
	
	public void addCard(Card c)
	{
		cards.add(c);
	}
	
	/**
	 * 
	 * Adds a card to the sideboard. Takes in a reference to a card
	 * object of the card to add.
	 * 
	 */
	
	public void addSideCard(Card c)
	{
		sideboard.add(c);
	}
	
	/**
	 * 
	 * Removes a card from the main deck. Takes in name of card, and
	 * number of copies to remove.
	 * 
	 */
	
	public boolean removeCard(String name, int n)
	{
		// number of occurences removed
		int removedCards = 0;
		
		// find the card and remove it from the database
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).toString().equals(name)) {
				// found card -- remove it
				cards.remove(i);
				i = -1;
				removedCards++;
				
				// return true if all desired cards have been removed
				if (removedCards == n)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Removes all copies of a card from the main deck.
	 * 
	 */
	
	public boolean removeCard(String name)
	{
		// determines whether card has been found or not
		boolean foundCard = false;
		
		// find the card and remove it from the database
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).toString().equals(name)) {
				// found card -- remove it
				cards.remove(i);
				i = -1;
				foundCard = true;
			}
		}
		return foundCard;
	}
	
	/**
	 * 
	 * Removes a card from the sideboard. Takes in name of card, and
	 * number of copies to remove.
	 * 
	 */
	
	public boolean removeSideCard(String name, int n)
	{
		// determines whether card has been found or not
		boolean foundCard = false;
		
		// find the card and remove it from the database
		for (int i = 0; i < cards.size(); i++) {
			if (sideboard.get(i).toString().equals(name)) {
				// found card -- remove it
				sideboard.remove(i);
				foundCard = true;
			}
		}
		return foundCard;
	}
	
	public DeckObject[] getTable()
	{
		ArrayList<DeckObject> cArray = new ArrayList<DeckObject>();
		
		// add each card to the array
		for (int i = 0; i < cards.size(); i++) {
			if (cArray.contains(new DeckObject(cards.get(i).getName()))) {
				cArray.get(cArray.indexOf(new DeckObject(cards.get(i).getName()))).incQuantity();
			}
			else {
				cArray.add(new DeckObject(cards.get(i).getName()));
				cArray.get(cArray.indexOf(new DeckObject(cards.get(i).getName()))).incQuantity();
			}
		}
		return cArray.toArray(new DeckObject[0]);
	}	
	
	public Card getCard(String cardName) {
		// Make a fake card to check, knowing that only the name must be equal
		Card temp = new Card(cardName, "","","","","",0,"","");
		
		// get and return real card object
		int index = cards.indexOf(temp);
		temp = cards.get(index);
		return temp;
	}
}
