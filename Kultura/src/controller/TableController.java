package controller;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Application;
import view.View;

/**
 * Table kontroler za aplikaciju.
 * 
 * @author Grupa 1
 */

public class TableController implements ListSelectionListener 
{
	Application model = null;
	View view = null;
	
	public TableController(View view, Application model)
	{
		this.view = view;
		this.model = model;
		
		this.view.getTablePanel().getTable().getSelectionModel().addListSelectionListener(this);
		this.view.getTablePanel().getFixedTable().getSelectionModel().addListSelectionListener(this);
	}

	
	
	@Override
	public void valueChanged(ListSelectionEvent arg0) 
	{		
		int selectedRow = view.getTablePanel().getTable().getSelectedRow();
		this.view.getTablePanel().getFixedTable().setRowSelectionInterval(selectedRow, selectedRow);
		
		this.model.notifyObserversSelectionChanged(this.view);
	}

}
