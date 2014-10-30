package mtg_organizer;
import java.util.*;
import java.io.*;

/**
 * 
 * @author Andrew Smythe
 *
 * Class:   Card
 * 
 * Purpose: Defines the characteristics of a Magic card. Each
 *          Card objects represents a single MTG playing card.
 *
 */

public class Card implements Serializable {
	
	// Card name, set name, card types, mana cost, card text
	private String name;
	private String set;
	private String type;
	private String subtype;
	private String cost;
	private String text;

	// Number of card, power, toughness
	// Note: Some of these may be irrelevant, depending on card type.
	private int num;
	private String power;
	private String toughness;
	
	/**
	 * 
	 * Public constructor for the Card class. Initializes all private members.
	 */
	
	public Card(String name, String set, String type, String subtype, String cost, String text, int num, String power, String toughness)
	{
		// initialize all of the card characteristics
		this.name = name;
		this.set = set;
		this.type = type;
		this.subtype = subtype;
		this.cost = cost;
		this.text = text;
		this.num = num;
		this.power = power;
		this.toughness = toughness;
	}
	
	/**
	 * 
	 * Override toString so that it returns the name of
	 * the card.
	 * 
	 */
	
	public String toString()
	{
		return this.name;
	}
	
	/**
	 * 
	 * Override equals so that it returns true if
	 * the name of two cards are equal.
	 * 
	 */
	
	public boolean equals(Object o)
	{
		return (this.name.equals(((Card)o).name));
	}
	
	/*
	 * Override equals (takes String as parameter) --
	 * return true of name of two cards are equal.
	 */
	
	public boolean equals(String s)
	{
		return (this.name == s);
	}
	
	public String[] asTable()
	{
		String[] ret = {this.getName(), this.getType(), this.getSubType()};
		return ret;
	}

	public boolean matches(Card other)
	{
		return (this.name.toLowerCase().startsWith(other.toString().toLowerCase()));
	}
	
	
	/*
	 * Getter Methods
	 */
	
	public String getName()
	{
		return this.name;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public String getSubType()
	{
		return this.subtype;
	}
	
	public String getCost()
	{
		return this.cost;
	}
	
	public String getSet()
	{
		return this.set;
	}
	
	public int getNum()
	{
		return this.num;
	}
	
	public String getRules()
	{
		return this.text;
	}
}
