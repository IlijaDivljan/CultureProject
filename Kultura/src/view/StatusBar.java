package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;








import model.Application;
import view.observer.Observer;

/**
 * Klasa predstavlja statusbar aplikacije.
 * 
 * @author Grupa 1
 */
public class StatusBar extends JPanel implements Observer
{	
	private static final long serialVersionUID = 1L;
	
	private JLabel activeElement = new JLabel("Active table: NA");
	private JLabel appState = new JLabel("Current state: NA");
	private JLabel selectedRow = new JLabel("Selected row: NA");
	
	private JLabel insert = new JLabel("Testni Tekst");
	private JLabel rows = new JLabel(" (1 row(s) affected)");
	private JLabel icon = new JLabel(new ImageIcon("images/acceptIcon.png"));
	
	private String previousState = null;
	
	Font f = new Font("Segoe UI", Font.PLAIN, 12);
	
	
	/**
	 * Konstruktor klase koji inicijalizuje elemente status bar-a.
	 */
	
	public StatusBar()
	{
		setLayout(new BorderLayout());
		
		activeElement.setFont(f);
		appState.setFont(f);
		selectedRow.setFont(f);
		insert.setFont(f);
		rows.setFont(f);
		
		insert.setForeground(new Color(46,139,87));
		
		insert.setVisible(false);
		rows.setVisible(false);
		icon.setVisible(false);
		
		
		JPanel statusBar = new JPanel(new BorderLayout());
		
		JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPanel element = new JPanel(new GridLayout());
		element.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		element.add(activeElement);
		left.add(element);
		
		JPanel center = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		

		center.add(icon);
		center.add(insert);
		center.add(rows);
		
		center.add(Box.createHorizontalStrut(80));
		
		JPanel state = new JPanel(new GridLayout());
		state.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		state.add(appState);
		center.add(state);
		
		
		
		
		JPanel right = new JPanel();
		JPanel row = new JPanel(new GridLayout());
		row.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		row.add(selectedRow);
		right.add(row);
		
		statusBar.add(left, BorderLayout.WEST);
		statusBar.add(center, BorderLayout.CENTER);
		statusBar.add(right, BorderLayout.EAST);
		
		add(statusBar);
	}


	public void setActiveElement(String activeElement) {
		this.activeElement.setText(activeElement);
	}

	public void setAppState(String appState) {
		this.appState.setText(appState);
	}


	public void setSelectedRow(String selectedRow) {
		this.selectedRow.setText(selectedRow);
	}

	public void setVisible(String s)
	{
		this.insert.setText(s);
		
		this.icon.setVisible(true);
		this.insert.setVisible(true);
		this.rows.setVisible(true);
	}
	public void setHidden()
	{
		this.icon.setVisible(false);
		this.insert.setVisible(false);
		this.rows.setVisible(false);
	}

	@Override
	public void updateStateChanged(Application model) 
	{
		String s = model.getCurrentState().toString();
		this.setAppState("Current state: " + s);
		
		if(s == "Select")
		{
			this.setSelectedRow("Selected row: NA");
			this.setActiveElement("Active table: NA");
		}
		
		//Regulisanje povratne informacije o unosu. Insert, Update ili Delete.
		
		if(!(previousState == "Update" && s == "Read"))
		{
			setHidden();
		}

		
		previousState = s;
	}


	@Override
	public void updateSelectionChanged(View view) 
	{
		this.setSelectedRow("Selected row: " + (view.getTablePanel().getTable().getSelectedRow()+1) + "/" + view.getTablePanel().getTable().getRowCount());
		//setHidden();
	}


	@Override
	public void updateTableChanged(View view) 
	{
		this.setActiveElement("Active table: " + view.getModel().getCurrentTable().getName());
		setHidden();
	}



}
