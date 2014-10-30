package mtg_organizer;

import java.io.*;
import javax.swing.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.*;
import java.util.*;

import java.net.*;

/**
 * 
 * @author Andrew Smythe
 * 
 * Class:   CardDatabase
 * 
 * Purpose: Extends the abstract class CardList. Adds methods to
 * 			read a list of cards from an XML file, and add them to the
 * 			database.
 *
 */

public class CardDatabase extends CardList implements Serializable {
	
	private ArrayList<Card> cards;
	private String xmlFile;
	
	/**
	 * Public constructor of CardDatabase. Takes in a path to an XML file
	 * containing a database of Magic cards. Assumes that the XML file is
	 * structured correctly.
	 * 
	 * Initializes the database from the XML file.
	 * 
	 */
	
	public CardDatabase(String xml)
	{
		cards = new ArrayList<Card>();
		xmlFile = xml;
	}
	
	/**
	 * 
	 * Adds a card to the database. Takes in a reference to a card object.
	 * 
	 */
	
	public void addCard(Card c)
	{
		// if we already have the card, simply update its information
		if (cards.contains(c) == true) {
			Card card = cards.get(cards.indexOf(c));
			card = c;
		}
		else
			cards.add(c);
	}
	
	/**
	 * 
	 * Removes a card from the database. Takes in name, and quantity of
	 * cards. 
	 * 
	 * Note that this will not likely be used in this class, as there
	 * should only ever be one instance of any given card.
	 * 
	 * If this method is called, it will simply call removeCard(String).
	 * 
	 */
	
	public boolean removeCard(String name, int n)
	{
		return removeCard(name);
	}
	
	/**
	 * 
	 * Removes a card from the database by its name.
	 * 
	 */
	
	public boolean removeCard(String name)
	{		
		// find the card and remove it from the database
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).toString().equals(name)) {
				// found card -- remove it and return true
				cards.remove(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Retrieves cards from an XML file, and puts them into the database.
	 * 
	 */
	
	public void getCards()
	{
		try {
			// get the XML File
			InputStream xmlCards = CardDatabase.class.getResourceAsStream(xmlFile);
			if (xmlCards == null) JOptionPane.showMessageDialog(null, "Could not locate XML file.");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlCards);
			doc.getDocumentElement().normalize();
			
			// Get the card elements we need for the database
			NodeList nList = doc.getElementsByTagName("card");
			
			// For each card in the XML file, add it to the database
			for (int i = 0; i < nList.getLength(); i++) {
				 
				   Node nNode = nList.item(i);
				   if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
				      Element eElement = (Element) nNode;
				      
				      // Create a new card
				      Card c = new Card(getTagValue("name", eElement), getTagValue("short_set", eElement), 
				    		  			getTagValue("type", eElement), getTagValue("sub", eElement), 
				    		  			getTagValue("mana", eElement), getTagValue("text", eElement), 
				    		  			Integer.parseInt(getTagValue("num", eElement)), 
				    		  			getTagValue("pt", eElement) != "" ? getTagValue("pt", eElement).split("/")[0] : "",
				    		  			getTagValue("pt", eElement) != "" ? getTagValue("pt", eElement).split("/")[1] : "");
				          
				      // Add the new card to the database
				      this.addCard(c);
			          
				   }
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		catch (SAXException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns the string value inside a given tag, specified by the
	 * user.
	 * 
	 * @return String
	 */
	
	private static String getTagValue(String sTag, Element eElement) {
		// String to return
		String ret;
		try {
			// attempt to get the value in given XML tag
			NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
			Node nValue = (Node) nlList.item(0);
			ret = nValue.getNodeValue();
		}
		catch (NullPointerException e) {
			// return a blank string if a certain tag was not found
			ret = "";
		}
		
		return ret;
	}
	
	/**
	 * 
	 * Displays all cards to standard out.
	 * 
	 */
	
	public void displayCards()
	{
		for (int i = 0; i < cards.size(); i++) {
			System.out.println(cards.get(i));
		}
	}
	
	/**
	 * 
	 * Returns a two-dimensional Card array -- for use in the GUI.
	 * 
	 * @return Card[][]
	 */
	
	public String[][] getTable()
	{
		ArrayList<String[]> cArray = new ArrayList<String[]>();
		
		// add each card to the array
		for (int i = 0; i < cards.size(); i++) {
			cArray.add(cards.get(i).asTable());
		}
		return(cArray.toArray(new String[0][0]));
	}
	
	public String getCardURL(String cardName)
	{
		// Make a fake card to check, knowing that only the name must be equal
		Card temp = new Card(cardName, "","","","","",0,"","");
		
		// get the actual card
		temp = cards.get(cards.indexOf(temp));
		
		// create a URL from the name
		String sURL = "http://magiccards.info/scans/en/" + temp.getSet() + "/" + temp.getNum() + ".jpg";
		return sURL;
	}
	
	public String getCardInfo(String cardName)
	{
		// Make a fake card to check, knowing that only the name must be equal
		Card temp = new Card(cardName, "","","","","",0,"","");
		
		// get the actual card
		temp = cards.get(cards.indexOf(temp));
		System.out.println("Getting card info for: " + temp);
		// Create a string of card information
		String cardInfo = ("Name:\t" + temp.getName() + "\nType:\t" + temp.getType()
				+ ((temp.getSubType() != "") ? ("") : ("\nSub-Type:\t" + temp.getSubType()))
				+ "\nCost:\t" + temp.getCost()
				+ "\n\n" + temp.getRules() + "\n");
		System.out.println("Got: \n" + cardInfo);
		return cardInfo;
		
	}
	
	public String[][] searchCards(String cardName)
	{
		// Make a fake card to check, knowing that only the name must be equal
		Card temp = new Card(cardName, "","","","","",0,"","");
		
		List<Card> matchingCards = new ArrayList<Card>();
		
		// look for a matching card
		for (Iterator<Card> i = cards.iterator(); i.hasNext();) {
			Card c = (Card)i.next();
			if (c.matches(temp)) {
				matchingCards.add(c);
			}
		}
		
		// Put all of the card information into an array for the
		// GUI table
		ArrayList<String[]> cArray = new ArrayList<String[]>();
		
		// add each card to the array
		for (int i = 0; i < matchingCards.size(); i++) {
			cArray.add(matchingCards.get(i).asTable());
		}
		return(cArray.toArray(new String[0][0]));
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
