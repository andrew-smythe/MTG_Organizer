package mtg_organizer;

import javax.imageio.ImageIO;
import javax.swing.filechooser.*;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.*;

public class Interface {
	
	public static JLabel picLabel;
	public static JPanel midPanel;
	public static String cardInfo;
	public static CardDatabase db;
	public static JTable table;
	public static JTextField text;
	public static String[] columnNames;
	public static JTable deckTable;
	public static DeckObject[] deckContents;
	public static DeckList deck;
	public static String[] deckColumns;
	public static JRootPane rootPane;
	
	public static void main (String[] argv)
	{
		// setup Card database
		db = new CardDatabase("resources/cards.xml");
		db.getCards();
		
		// setup deck list
		deck = new DeckList();
		
		// create JFrame holder
		JFrame f = new JFrame("Andrew's MTG Card Organizer");
		
		// create panels for a three way split
		JPanel leftPanel = new JPanel();
		midPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		// set panel background colours
		leftPanel.setBackground(Color.CYAN);
		midPanel.setBackground(Color.CYAN);
		rightPanel.setBackground(Color.CYAN);
		
		// specify layout manager
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		// Buttons for Adding cards to deck and opening deck
		JButton addButton = new JButton("Add to Deck");
		JButton jopen = new JButton("Open Deck");
		
		// Action listener to open a previous deck list
		jopen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// initialize a file chooser
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				// make a custom file filter (only .deck files)
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return false;
						} 
						
						String ext = null;
				        String s = f.getName();
				        int i = s.lastIndexOf('.');

				        if (i > 0 &&  i < s.length() - 1) {
				            ext = s.substring(i+1).toLowerCase();
				        }
				        
				        if (ext != null) {
				            if (ext.equals("deck")) {
				                    return true;
				            } 
				            else {
				                return false;
				            }
				        }
				 
				        return false;
					}
					
					@Override    
					public String getDescription() {
				        return "MTG Deck Lists (*.deck)";
				    }
					
				});
				
				// display the file chooser
			    chooser.showDialog(chooser, "Open Deck");
			    
			    // get name of file to read from
				String ext = null;
		        String s = chooser.getSelectedFile().getAbsolutePath();
		        int i = s.lastIndexOf('.');

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        
		        System.out.println("Opening: " + s);
		        
		        // open file and read from it
		        try {
			        ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File(s)));
			        deck = (DeckList)is.readObject();
			        is.close();
			        ArrayList<Object[]> objs = new ArrayList<Object[]>();
					
			        // update the deck table
					DeckObject[] deckRows = deck.getTable();
					for (int k = 0; k < deckRows.length; k++) {
						objs.add(deckRows[k].get());
					}
					
					String[][] rows = objs.toArray(new String[0][0]);
					
					deckTable.setModel(new UneditableTableModel(rows, deckColumns));
		        }
		        // handle exceptions
		        catch (FileNotFoundException ex) {
		        	ex.getStackTrace();
		        }
		        catch (IOException ex) {
		        	ex.getStackTrace();
		        }
		        catch (ClassNotFoundException ex) {
		        	ex.getStackTrace();
		        }
			}
			
		});
		
		// Create button and action listener for saving deck to disk
		JButton saveButton = new JButton("Save Deck");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// create a new file chooser
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				// customize filter for chooser (only .deck files)
				chooser.setFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						if (f.isDirectory()) {
							return false;
						} 
						
						String ext = null;
				        String s = f.getName();
				        int i = s.lastIndexOf('.');

				        if (i > 0 &&  i < s.length() - 1) {
				            ext = s.substring(i+1).toLowerCase();
				        }
				        
				        if (ext != null) {
				            if (ext.equals("deck")) {
				                    return true;
				            } 
				            else {
				                return false;
				            }
				        }
				 
				        return false;
					}
					
					@Override    
					public String getDescription() {
				        return "MTG Deck Lists (*.deck)";
				    }
					
				});
				
				// holds name of file to write to
				String filename;
			    chooser.showDialog(chooser, "Save Deck");
			    
			    // get the name of file
				String ext = null;
		        String s = chooser.getSelectedFile().getName();
		        int i = s.lastIndexOf('.');

		        if (i > 0 &&  i < s.length() - 1) {
		            ext = s.substring(i+1).toLowerCase();
		        }
		        
		        // make sure that there is a .deck on the end, and append it if there is not
		        if (ext == null || !ext.equals(new String("deck")))
		        	filename = chooser.getSelectedFile().getAbsolutePath()+".deck";
		        else
		        	filename = chooser.getSelectedFile().getAbsolutePath();
		        
		        System.out.println("Saving to: " + filename);
		        
		        // write to file
		        try {
			        ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(new File(filename)));
			        os.writeObject(deck);
			        os.close();
		        }
		        // handle exceptions
		        catch (FileNotFoundException ex) {
		        	ex.getStackTrace();
		        }
		        catch (IOException ex) {
		        	ex.getStackTrace();
		        }
			}
			
		});
		
		// align buttons to center
		addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		jopen.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// add items to the right panel, and set custom spacing
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(saveButton);
		rightPanel.add(Box.createVerticalStrut(10));
		rightPanel.add(jopen);
		
		// setup middle panel
		midPanel.add(new JPanel());
		
		// initialize the middle picture
		try 
		{
			URL url = new URL("http://magiccards.info/scans/en/nph/104.jpg");
			BufferedImage bi = ImageIO.read(url);
			picLabel = new JLabel(new ImageIcon(bi));
			picLabel.setSize(70,70);
			((JPanel)midPanel.getComponent(0)).add(picLabel);
		} 
		catch (IOException e) { e.printStackTrace(); }
		
		// initialize card text area
		JTextArea cardText = new JTextArea("Name: Birthing Pod");
		cardText.setEditable(false);
		cardText.setCursor(null);
		cardText.setOpaque(true);
		cardText.setFocusable(false);
		cardText.setColumns(10);
		cardText.setLineWrap(true);
		cardText.setFont(new Font("Arial", Font.PLAIN, 11));
		JPanel cardTextPanel = new JPanel();
		cardTextPanel.setLayout(new BorderLayout());
		cardTextPanel.add(BorderLayout.CENTER, cardText);
		midPanel.add(cardTextPanel);
		
		// names of columns for card table
		columnNames = new String[]{"Name", "Type", "Sub Type"};
		
		// initialize card table
		table = new JTable(db.getTable(), columnNames);
		
		// initialize scroll pane for scrolling on table
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setModel(new UneditableTableModel(db.getTable(), columnNames));
		table.getRowSorter().toggleSortOrder(0);
		
		// name of columns for deck list table
		deckColumns = new String[]{"Name", "Quantity"};
		
		// initialize deck list table
		deckTable = new JTable(null, deckColumns);
		
		// make table scrollable
		JScrollPane deckScrollPane = new JScrollPane(deckTable);
		//deckTable.setFillsViewportHeight(true);
		//deckTable.setAutoCreateRowSorter(true);
		deckTable.setModel(new UneditableTableModel(null, deckColumns));
		rightPanel.add(Box.createRigidArea(new Dimension(0,10)));
		rightPanel.add(deckScrollPane);		
		
		// Buttons for removing cards from deck
		JButton removeOne = new JButton("Remove a copy from Deck");
		JButton removeAll = new JButton("Remove all copies from Deck");
		removeOne.setAlignmentX(Component.CENTER_ALIGNMENT);
		removeAll.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// create a custom actionlistener for removing the cards
		removeAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// get the card to remove from the table
				int row = deckTable.getSelectedRow();
				if (row >= 0) {
					deck.removeCard(((String)deckTable.getValueAt(row, 0)));
					System.out.println(((String)deckTable.getValueAt(row, 0)));
					ArrayList<Object[]> objs = new ArrayList<Object[]>();
					
					// update the dcek table
					DeckObject[] deckRows = deck.getTable();
					for (int i = 0; i < deckRows.length; i++) {
						objs.add(deckRows[i].get());
					}
					
					String[][] rows = objs.toArray(new String[0][0]);
					
					// display the new table
					deckTable.setModel(new UneditableTableModel(rows, deckColumns));
					int newSelectRow = row;
					if (row == deckTable.getRowCount())
						newSelectRow = deckTable.getRowCount()-1;
					
					// select the next item on the table
					if (newSelectRow >= 0)
						deckTable.setRowSelectionInterval(newSelectRow,newSelectRow);
					System.out.println(deckTable.getRowCount());
				}
			}
		});
		
		// create a custom action listener for removing one card from deck
		removeOne.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// get the card to remove
				int row = deckTable.getSelectedRow();
				if (row >= 0) {
					// remove a single copy of the card
					deck.removeCard(((String)deckTable.getValueAt(row, 0)), 1);
					System.out.println(((String)deckTable.getValueAt(row, 0)));
					ArrayList<Object[]> objs = new ArrayList<Object[]>();
					
					// update the deck list
					DeckObject[] deckRows = deck.getTable();
					for (int i = 0; i < deckRows.length; i++) {
						objs.add(deckRows[i].get());
					}
					
					String[][] rows = objs.toArray(new String[0][0]);

					// update the table
					deckTable.setModel(new UneditableTableModel(rows, deckColumns));
					int newSelectRow = row;
					if (row == deckTable.getRowCount())
						newSelectRow = deckTable.getRowCount()-1;
					
					// select the next object in table
					if (newSelectRow >= 0)
						deckTable.setRowSelectionInterval(newSelectRow,newSelectRow);
					System.out.println(deckTable.getRowCount());
				}
			}
		});
		
		// setup the right panel
		rightPanel.add(Box.createRigidArea(new Dimension(0,10)));
		rightPanel.add(removeOne);
		rightPanel.add(Box.createRigidArea(new Dimension(0,5)));
		rightPanel.add(removeAll);
		
		// select the first item on the table
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        ListSelectionModel rowSelected = table.getSelectionModel();
        
        // setup an action listener for when a new row is selected
		rowSelected.addListSelectionListener(new ListSelectionListener() {
			@Override
			// event for when row is selected
			public void valueChanged(ListSelectionEvent event) {
				
				int row;
				
                if (event.getValueIsAdjusting()) return;

                ListSelectionModel lsm = (ListSelectionModel)event.getSource();
                if (lsm.isSelectionEmpty()) {
                    return;
                } 
                else {
                	// Update the picture on selection
                    row = lsm.getMinSelectionIndex();
                    
    				String sURL = db.getCardURL((String)table.getValueAt(row, 0));
    				try 
    				{
    					// get picture from card's URL
    					System.out.println(sURL);
    					URL url = new URL(sURL);
    					BufferedImage bi = ImageIO.read(url);					
    					((JLabel)((JPanel)midPanel.getComponent(0)).getComponent(0)).setIcon(new ImageIcon(bi));
    					cardInfo = db.getCardInfo((String)table.getValueAt(row,0));
    					System.out.println(cardInfo);
    					((JTextArea)((JPanel)midPanel.getComponent(1)).getComponent(0)).setText(cardInfo);
    				} 
    				catch (IOException e) { e.printStackTrace(); }
                }
			}
		});
		
		// setup the text in the middle panel
		midPanel.add(new JLabel(cardInfo));
		midPanel.add(Box.createRigidArea(new Dimension(0,5)));
		
		// setup action listener for adding cards
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// get card to add to deck
				Card temp = db.getCard((String)table.getValueAt(table.getSelectedRow(), 0));
				System.out.println(temp.getName());
				deck.addCard(temp);
				
				// update the deck list
				ArrayList<Object[]> objs = new ArrayList<Object[]>();
				
				DeckObject[] deckRows = deck.getTable();
				for (int i = 0; i < deckRows.length; i++) {
					objs.add(deckRows[i].get());
				}
				
				String[][] rows = objs.toArray(new String[0][0]);
				
				// display updated deck list
				deckTable.setModel(new UneditableTableModel(rows, deckColumns));
			}
		});
		// add the button to the middle panel
		midPanel.add(addButton);
		
		// set the size of the database table
		scrollPane.setSize(500, 200);
		
		// create search field
		text = new JTextField(20);
		text.setAlignmentX(Component.RIGHT_ALIGNMENT);
		text.setMaximumSize(text.getPreferredSize());

		// create nested panel for search fields
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		// create label for searching
		searchPanel.add(new JLabel("Search for a Card:"));
		searchPanel.add(text);
		
		// create a search button
		JButton searchButton = new JButton("Search");
		rootPane = f.getRootPane();
		rootPane.setDefaultButton(searchButton);
		
		// make an action listener for searching for cards
		ActionListener search = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				// trim the list of cards to match the users search
				table.setModel(new UneditableTableModel(db.searchCards(text.getText()), columnNames));
				table.setCellSelectionEnabled(true);
				table.changeSelection(0, 0, false,false);
				table.requestFocus();
			}
		};
		
		// update the list when a user types in words 
		text.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e)
			{
				table.setModel(new UneditableTableModel(db.searchCards(text.getText()), columnNames));
			}
			
			@Override
			public void changedUpdate(DocumentEvent e)
			{
				table.setModel(new UneditableTableModel(db.searchCards(text.getText()), columnNames));
			}
			@Override
			public void removeUpdate(DocumentEvent e)
			{
				table.setModel(new UneditableTableModel(db.searchCards(text.getText()), columnNames));
			}
		});
		// add the listener to the button
		searchButton.addActionListener(search);
		
		searchPanel.add(searchButton);
		//searchPanel.setMaximumSize(searchPanel.getPreferredSize());
		
		// setup the left panel
		leftPanel.add(searchPanel);
		leftPanel.add(Box.createRigidArea(new Dimension(0,5)));
		leftPanel.add(scrollPane);
		
		// setup borders on the panels
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		midPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
		rightPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

		// create the 3-panel display
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, midPanel, rightPanel);
		JSplitPane basePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, splitPane);
	
		// setup the base frame, and display to the screen
		f.add(basePane);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		f.setSize(1000, screenSize.height);
	    basePane.setDividerLocation(400);
		splitPane.setDividerLocation(312);
		f.setLocationRelativeTo(null);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}
