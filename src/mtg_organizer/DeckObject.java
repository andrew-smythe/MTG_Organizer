package mtg_organizer;

public class DeckObject {
	private int quantity;
	private String cardName;
	
	public DeckObject(String s) {
		cardName = s;
		quantity = 0;
	}
	
	public String getName() {
		return cardName;
	}
	
	public void incQuantity() {
		quantity++;
	}
	
	public void decQuantity() {
		quantity--;
	}
	
	public String[] get() {
		return (new String[]{cardName, ((Integer)quantity).toString()});
	}
	
	@Override
	public boolean equals(Object o)
	{
		return (this.cardName == ((DeckObject)o).getName());
	}
}
