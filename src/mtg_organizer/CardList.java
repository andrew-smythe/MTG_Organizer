package mtg_organizer;

import java.io.*;
import java.util.*;

/**
 * 
 * @author Andrew Smythe
 * 
 * Class:   CardList
 * 
 * Purpose: Provide an abstract definition for a list of Magic cards for use
 * 			in the program. Examples are decklists and a full database of cards.
 * 			
 */

public abstract class CardList implements Serializable {
	
	private ArrayList<Card> cards;
	
	/**
	 * 
	 * Adds a card to the list. Takes in a card object to add
	 * to the list.
	 *
	 */
	
	public abstract void addCard(Card c);
	
	/**
	 * 
	 * Remove a card from the list by its name. User must also
	 * specify how many of the card they would like to remove.
	 * 
	 * @return bool
	 */
	public abstract boolean removeCard(String name, int n);

	/**
	 * 
	 * Remove a card from the list by its name. Removes all
	 * instances of the card.
	 * 
	 * @return bool
	 */
	public abstract boolean removeCard(String name);
	
	
}
