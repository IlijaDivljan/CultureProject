package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import report.BasicReport;
import view.View;
import view.state.Delete;
import view.state.Insert;
import view.state.Read;
import view.state.Select;
import view.state.Update;
import model.Application;


/**
 *	Klasa koja predstavlja kontroler Toolbar-a.
 * 
 * @author Grupa 1
 */
public class ToolbarController implements ActionListener
{	
	Application model = null;
	View view = null;
	
	public ToolbarController(Application model, View view) 
	{
		this.model = model;
		this.view = view;
		this.view.toolBar.setListeners(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String actionCommand = e.getActionCommand();
		
		switch (actionCommand) 
		{
			case "Open":				
				File workingDirectory = new File(System.getProperty("user.dir")); //da uzme adresu projekta
				JFileChooser fileCh = new JFileChooser(workingDirectory + "/xmlfiles");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "xml");
				fileCh.setFileFilter(filter);
				int option = fileCh.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile =  fileCh.getSelectedFile();					
					view.dbExplorer.loadTree(selectedFile.toString());
					model.setCurrentState(new Select());					
				}
			break;
			case "Switch":
				workingDirectory = new File(System.getProperty("user.dir")); //da uzme adresu projekta
				fileCh = new JFileChooser(workingDirectory + "/xmlfiles");
				filter = new FileNameExtensionFilter(null, "xml");
				fileCh.setFileFilter(filter);
				option = fileCh.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile =  fileCh.getSelectedFile();					
					view.dbExplorer.loadTree(selectedFile.toString());	
					model.setCurrentState(new Select());
				}				
				break;
			case "First":
				view.getTablePanel().getTable().setRowSelectionInterval(0, 0);
				break;
			case "Previous":
				view.getTablePanel().getTable().setRowSelectionInterval(view.getTablePanel().getTable().getSelectedRow() - 1, view.getTablePanel().getTable().getSelectedRow() - 1);
				break;
			case "Next":
				view.getTablePanel().getTable().setRowSelectionInterval(view.getTablePanel().getTable().getSelectedRow() + 1, view.getTablePanel().getTable().getSelectedRow() + 1);
				break;
			case "Last":
				view.getTablePanel().getTable().setRowSelectionInterval(view.getTablePanel().getTable().getRowCount() - 1, view.getTablePanel().getTable().getRowCount() - 1);
				break;
			case "Add":
				this.model.setCurrentState(new Insert());
				
				break;
			case "Edit":
				if(this.view.getTablePanel().getTable().getSelectedRow() != -1)
				{
					this.model.setCurrentState(new Update());
				}
				else
				{
					JOptionPane.showMessageDialog(this.view, "Za operaciju Update je potrebno prvo selektovati red u tabeli.");
				}
				
				break;
			case "Delete":
				if(this.view.getTablePanel().getTable().getSelectedRow() != -1)
				{
					this.model.setCurrentState(new Delete());
				}
				else
				{
					JOptionPane.showMessageDialog(this.view, "Za operaciju Delete je potrebno prvo selektovati red u tabeli.");
				}
				break;
			case "Accept":
				//Ljepota state sablona.
				this.view.getStatusBar().setHidden();
				this.model.getCurrentState().doAction(this.view);
				break;
			case "Cancel":
				this.model.setCurrentState(new Read());
				break;
			case "Report":
				BasicReport report = new BasicReport(view, model);
				report.createReport();
				break;			
			default:
				JOptionPane.showMessageDialog(null, "Komanda nije prepoznata.");
			break;
		}

	}

}
