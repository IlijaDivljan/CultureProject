package view;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;

import model.Application;
import view.observer.Observer;

/**
 * Klasa koja sadrzi meni bar aplikacije.
 * 
 * @author Grupa 1
 */
public class MenuBar extends JMenuBar implements Observer
{	
	private static final long serialVersionUID = 1L;
	
	private JMenu file;
	private JMenu edit;
	private JMenu report;
	private JMenu help;
	
	public ArrayList<MenuItem> listOfItems = new ArrayList<MenuItem>();
	
	public MenuBar()
	{
		//kreiranje file menija
		file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		MenuItem menuItem = new MenuItem(new ImageIcon("images/openIcon.png"), "Loads Database from XML", "Load Database");		
		file.add(menuItem);	
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/switchIcon.png"), "Switchs Database from XML", "Switch Database");		
		file.add(menuItem);	
		listOfItems.add(menuItem);
		file.add(new JSeparator(JSeparator.HORIZONTAL), 2);
		menuItem = new MenuItem(new ImageIcon("images/exitIcon.png"), "Closes Application", "Exit");		
		file.add(menuItem);	
		listOfItems.add(menuItem);
		
		//kreiranje edit menija
		edit = new JMenu("Edit");
		edit.setMnemonic(KeyEvent.VK_E);
		menuItem = new MenuItem(new ImageIcon("images/firstIcon.png"), "Position on First Row in Table", "First");		
		edit.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/previousIcon.png"), "Next Row in Table", "Next");		
		edit.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/nextIcon.png"), "Previous Row in Table", "Previous");		
		edit.add(menuItem);	
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/lastIcon.png"), "Position on Last Row in Table", "Last");		
		edit.add(menuItem);
		listOfItems.add(menuItem);
		edit.add(new JSeparator(JSeparator.HORIZONTAL), 4);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/addIcon.png"), "New Row in Table", "New");		
		edit.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/editIcon.png"), "Edit Table Row", "Edit");		
		edit.add(menuItem);	
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/deleteIcon.png"), "Delete Table Row", "Delete");		
		edit.add(menuItem);	
		listOfItems.add(menuItem);
		edit.add(new JSeparator(JSeparator.HORIZONTAL), 8);
		menuItem = new MenuItem(new ImageIcon("images/acceptIcon.png"), "Accept Changes", "Accept");		
		edit.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/cancelIcon.png"), "Discard Changes", "Cancel");		
		edit.add(menuItem);	
		listOfItems.add(menuItem);
		//menuItem = new MenuItem(new ImageIcon("images/reportIcon.png"), "Generate Basic Report", "Report");	
		//edit.add(menuItem);
		//listOfItems.add(menuItem);
		
		//kreiranje report menija
		report = new JMenu("Report");
		report.setMnemonic(KeyEvent.VK_R);
		//ubacivanje podmenija
		JMenu staticReport = new JMenu("Static Report");		
		menuItem = new MenuItem(null,"Report Beneficije", "Beneficije");		
		staticReport.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(null,"Report Manifestacije", "Manifestacije");
		staticReport.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(null,"Report Clanovi kulturne ustanove", "Clanovi kulturne ustanove");		
		staticReport.add(menuItem);		
		report.add(staticReport);
		listOfItems.add(menuItem);
		//kraj podmenija
		menuItem = new MenuItem(new ImageIcon("images/reportIcon.png"), "Generate Basic Report", "Basic Report");		
		report.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/customReportIcon.png"), "Generate Custom Report", "Custom Report");		
		report.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/customReportIcon.png"), "Generate Advanced Report", "Advanced Report");		
		report.add(menuItem);
		listOfItems.add(menuItem);
		
		//kreiranje help menija
		help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		menuItem = new MenuItem(new ImageIcon("images/aboutIcon.png"), "About Application", "About");		
		help.add(menuItem);
		listOfItems.add(menuItem);
		menuItem = new MenuItem(new ImageIcon("images/helpIcon.png"), "JavaDoc Documentation", "Documentation");		
		help.add(menuItem);
		listOfItems.add(menuItem);
		
		this.add(file);
		this.add(edit);
		this.add(report);
		this.add(help);
	}
	
	public void setListeners(ActionListener al)
	{
		for (MenuItem menuItem : listOfItems)
		{
			menuItem.addActionListener(al);
		}
	}
	
/*	public void disableCommands()
	{
		file.setEnabled(true);				
		file.getMenuComponent(1).setEnabled(false);
		edit.setEnabled(false);
		report.setEnabled(false);
		help.setEnabled(false);
	}
	public void enableSwitch()
	{
		file.setEnabled(true);	
		file.getMenuComponent(0).setEnabled(false);
		file.getMenuComponent(1).setEnabled(true);
		edit.setEnabled(false);
		report.setEnabled(false);
		help.setEnabled(false);
	}
	public void enableCommands()
	{
		file.setEnabled(true);
		edit.setEnabled(true);
		report.setEnabled(true);
		help.setEnabled(true);
	}*/

	@Override
	public void updateStateChanged(Application model) 
	{
		
		String state = model.getCurrentState().toString();
		
		switch (state) 
		{
		case "Ready":
			file.setEnabled(true);				
			file.getMenuComponent(1).setEnabled(false);
			edit.setEnabled(false);
			report.setEnabled(false);
			break;
		case "Select":
			file.setEnabled(true);	
			file.getMenuComponent(0).setEnabled(false);
			file.getMenuComponent(1).setEnabled(true);
			edit.setEnabled(false);
			report.setEnabled(false);
			break;
		case "Read":
			file.setEnabled(true);
			file.getMenuComponent(1).setEnabled(true);
			edit.setEnabled(true);
			edit.getMenuComponent(5).setEnabled(true);
			edit.getMenuComponent(6).setEnabled(true);
			edit.getMenuComponent(7).setEnabled(true);
			edit.getMenuComponent(9).setEnabled(false);
			edit.getMenuComponent(10).setEnabled(false);
			report.setEnabled(true);
			break;
		case "Insert":
			file.setEnabled(true);
			file.getMenuComponent(1).setEnabled(false);
			edit.setEnabled(true);
			edit.getMenuComponent(5).setEnabled(false);
			edit.getMenuComponent(6).setEnabled(true);
			edit.getMenuComponent(7).setEnabled(true);
			edit.getMenuComponent(9).setEnabled(true);
			edit.getMenuComponent(10).setEnabled(true);
			report.setEnabled(true);
			break;
		case "Delete":
			file.setEnabled(true);
			file.getMenuComponent(1).setEnabled(false);
			edit.setEnabled(true);
			edit.getMenuComponent(5).setEnabled(true);
			edit.getMenuComponent(6).setEnabled(false);
			edit.getMenuComponent(7).setEnabled(true);
			edit.getMenuComponent(9).setEnabled(true);
			edit.getMenuComponent(10).setEnabled(true);
			report.setEnabled(true);
			break;
		case "Update":
			file.setEnabled(true);
			file.getMenuComponent(1).setEnabled(false);
			edit.setEnabled(true);
			edit.getMenuComponent(5).setEnabled(true);
			edit.getMenuComponent(6).setEnabled(true);
			edit.getMenuComponent(7).setEnabled(false);
			edit.getMenuComponent(9).setEnabled(true);
			edit.getMenuComponent(10).setEnabled(true);
			report.setEnabled(true);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void updateSelectionChanged(View view) {}

	@Override
	public void updateTableChanged(View view) {}
	

}
