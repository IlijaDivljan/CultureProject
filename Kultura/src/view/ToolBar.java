package view;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToolBar;

import model.Application;
import view.observer.Observer;

/**
 * Klasa koja sadrzi tulbar aplikacije.
 * 
 *  @author Grupa 1
 */
public class ToolBar extends JToolBar implements Observer
{	
	private static final long serialVersionUID = 1L;
	
	private ToolItem load, change, first, next, previous, last, add, edit, delete, accept, cancel, report;
	
	public ToolBar()
	{	
		setRollover(true);	
		
		load = new ToolItem(new ImageIcon("images/openIconLarge.png"), "Open Database", "Open");		
		add(load);
		change = new ToolItem(new ImageIcon("images/switchIconLarge.png"), "Switch Database", "Switch");
		add(change);	
		addSeparator();
		
		
		first = new ToolItem(new ImageIcon("images/firstIconLarge.png"), "First Row", "First");
		add(first);
		previous = new ToolItem(new ImageIcon("images/previousIconLarge.png"), "Previous Row", "Previous");
		add(previous);
		next = new ToolItem(new ImageIcon("images/nextIconLarge.png"), "Next Row", "Next");
		add(next);
		last = new ToolItem(new ImageIcon("images/lastIconLarge.png"), "Last Row", "Last");
		add(last);
		addSeparator();
		
		add = new ToolItem(new ImageIcon("images/addIconLarge.png"), "Add Row", "Add");		
		add(add);
		edit = new ToolItem(new ImageIcon("images/editIconLarge.png"), "Edit Row", "Edit");
		add(edit);	
		delete = new ToolItem(new ImageIcon("images/deleteIconLarge.png"), "Delete Row", "Delete");
		add(delete);
		addSeparator();
		
		accept = new ToolItem(new ImageIcon("images/acceptIconLarge.png"), "Accept Changes", "Accept");		
		add(accept);
		cancel = new ToolItem(new ImageIcon("images/cancelIconLarge.png"), "Discard Changes", "Cancel");
		add(cancel);
		addSeparator();
		
		report = new ToolItem(new ImageIcon("images/reportIconLarge.png"), "Generate Report", "Report");
		add(report);		
	}
	
	//funkcija za dodavanje listenera
	public void setListeners(ActionListener al)
	{		
		load.addActionListener(al);
		change.addActionListener(al);
		first.addActionListener(al);
		next.addActionListener(al);
		previous.addActionListener(al);
		last.addActionListener(al);
		add.addActionListener(al);
		edit.addActionListener(al);
		delete.addActionListener(al);
		accept.addActionListener(al);
		cancel.addActionListener(al);
		report.addActionListener(al);
	}
	
/*	//funkcija za sakrivanje komandi
	public void disableCommands()
	{
		load.setEnabled(true);
		change.setEnabled(false);
		first.setEnabled(false);
		next.setEnabled(false);
		previous.setEnabled(false);
		last.setEnabled(false);
		add.setEnabled(false);
		edit.setEnabled(false);
		delete.setEnabled(false);
		accept.setEnabled(false);
		cancel.setEnabled(false);
		report.setEnabled(false);		
	}
	
	//funkcija za omogucavanje komandi
	public void enableCommands()
	{
		load.setEnabled(false);
		change.setEnabled(true);
		first.setEnabled(true);
		next.setEnabled(true);
		previous.setEnabled(true);
		last.setEnabled(true);
		add.setEnabled(true);
		edit.setEnabled(true);
		delete.setEnabled(true);
		accept.setEnabled(true);
		cancel.setEnabled(true);
		report.setEnabled(true);		
	}
	
	//funkcija za sakrivanje komandi
	public void enableSwitch()
	{
		load.setEnabled(false);
		change.setEnabled(true);
		first.setEnabled(false);
		next.setEnabled(false);
		previous.setEnabled(false);
		last.setEnabled(false);
		add.setEnabled(false);
		edit.setEnabled(false);
		delete.setEnabled(false);
		accept.setEnabled(false);
		cancel.setEnabled(false);
		report.setEnabled(false);		
	}*/

	@Override
	public void updateStateChanged(Application model) 
	{
		String state = model.getCurrentState().toString();
		
		switch (state) 
		{
		case "Ready":
			load.setEnabled(true);
			change.setEnabled(false);
			first.setEnabled(false);
			next.setEnabled(false);
			previous.setEnabled(false);
			last.setEnabled(false);
			add.setEnabled(false);
			edit.setEnabled(false);
			delete.setEnabled(false);
			accept.setEnabled(false);
			cancel.setEnabled(false);
			report.setEnabled(false);
			break;
		case "Select":
			load.setEnabled(false);
			change.setEnabled(true);
			first.setEnabled(false);
			next.setEnabled(false);
			previous.setEnabled(false);
			last.setEnabled(false);
			add.setEnabled(false);
			edit.setEnabled(false);
			delete.setEnabled(false);
			accept.setEnabled(false);
			cancel.setEnabled(false);
			report.setEnabled(false);		
			break;
		case "Read":
			load.setEnabled(false);
			change.setEnabled(true);
			first.setEnabled(true);
			next.setEnabled(true);
			previous.setEnabled(true);
			last.setEnabled(true);
			add.setEnabled(true);
			edit.setEnabled(true);
			delete.setEnabled(true);
			accept.setEnabled(false);
			cancel.setEnabled(false);
			report.setEnabled(true);
			break;
		case "Insert":
			load.setEnabled(false);
			change.setEnabled(false);
			first.setEnabled(true);
			next.setEnabled(true);
			previous.setEnabled(true);
			last.setEnabled(true);
			add.setEnabled(false);
			edit.setEnabled(true);
			delete.setEnabled(true);
			accept.setEnabled(true);
			cancel.setEnabled(true);
			report.setEnabled(true);
			break;
		case "Delete":
			load.setEnabled(false);
			change.setEnabled(false);
			first.setEnabled(true);
			next.setEnabled(true);
			previous.setEnabled(true);
			last.setEnabled(true);
			add.setEnabled(true);
			edit.setEnabled(true);
			delete.setEnabled(false);
			accept.setEnabled(true);
			cancel.setEnabled(true);
			report.setEnabled(true);
			break;
		case "Update":
			load.setEnabled(false);
			change.setEnabled(false);
			first.setEnabled(true);
			next.setEnabled(true);
			previous.setEnabled(true);
			last.setEnabled(true);
			add.setEnabled(true);
			edit.setEnabled(false);
			delete.setEnabled(true);
			accept.setEnabled(true);
			cancel.setEnabled(true);
			report.setEnabled(true);
			break;

		default:
			break;
		}
		
	}
	
	public void setSelectDisplay()
	{
		//load, change, first, next, previous, last, add, edit, delete, accept, cancel, report
		/*load.setVisible(false);
		change.setVisible(false);
		add.setVisible(false);
		edit.setVisible(false);
		delete.setVisible(false);
		accept.setVisible(false);
		cancel.setVisible(false);
		report.setVisible(false);*/
		
		//this.setAlignmentX(CENTER_ALIGNMENT);
		this.setFloatable(false);
		
		
		for(Component com : getComponents())
		{
			com.setVisible(false);
		}
		
		getComponent(2).setVisible(true);
		
		first.setVisible(true);
		next.setVisible(true);
		previous.setVisible(true);
		last.setVisible(true);
		
		
		first.setIcon(new ImageIcon("images/firstIcon.png"));
		next.setIcon(new ImageIcon("images/previousIcon.png"));
		previous.setIcon(new ImageIcon("images/nextIcon.png"));
		last.setIcon(new ImageIcon("images/lastIcon.png"));
	}

	@Override
	public void updateSelectionChanged(View view) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTableChanged(View view) {
		// TODO Auto-generated method stub
		
	}

}
