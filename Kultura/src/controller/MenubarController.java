package controller;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import report.AdvancedReport;
import report.BasicReport;
import report.CustomReport;
import report.StaticReport;
import view.About;
import view.View;
import view.state.Delete;
import view.state.Insert;
import view.state.Read;
import view.state.Select;
import view.state.Update;
import model.Application;

/**
 * Controller meni bara.
 *
 * @author Grupa 1
 */
public class MenubarController implements ActionListener
{
	Application model;
	View view;
	
	public MenubarController(Application model, View view)
	{
		this.model = model;
		this.view = view;
		this.view.menuBar.setListeners(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String actionCommand = e.getActionCommand();
				
		switch (actionCommand) 
		{
			case "Load Database":				
				File workingDirectory = new File(System.getProperty("user.dir")); //da uzme adresu projekta
				JFileChooser fileCh = new JFileChooser(workingDirectory + "/xmlfiles");
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null, "xml");
				fileCh.setFileFilter(filter);
				int option = fileCh.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile =  fileCh.getSelectedFile();					
					view.dbExplorer.loadTree(selectedFile.toString());
					//view.toolBar.enableSwitch();
					//view.menuBar.enableSwitch();
					model.setCurrentState(new Select());
				}
			break;
			case "Switch Database":
				workingDirectory = new File(System.getProperty("user.dir")); //da uzme adresu projekta
				fileCh = new JFileChooser(workingDirectory + "/xmlfiles");
				filter = new FileNameExtensionFilter(null, "xml");
				fileCh.setFileFilter(filter);
				option = fileCh.showOpenDialog(null);
				if(option == JFileChooser.APPROVE_OPTION)
				{
					File selectedFile =  fileCh.getSelectedFile();					
					view.dbExplorer.loadTree(selectedFile.toString());	
					//view.toolBar.enableSwitch();
					//view.menuBar.enableSwitch();
					
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
				this.view.getStatusBar().setHidden();
				this.model.getCurrentState().doAction(this.view);
				break;
			case "Cancel":
				this.model.setCurrentState(new Read());
				break;				
			case "Basic Report":
				BasicReport basicReport = new BasicReport(view, model);
				basicReport.createReport();				
				break;
			case "Custom Report":
				CustomReport customReport = new CustomReport(view, model);
				customReport.showOptions();
				break;
			case "Advanced Report":
				AdvancedReport advancedReport = new AdvancedReport(view, model);
				advancedReport.createReport();
				break;
			case "Beneficije":				
				new StaticReport("reports/report1.jrxml", "getBeneficija");					
				break;
			case "Manifestacije":
				new StaticReport("reports/report2.jrxml", "getManifestacija");
				break;
			case "Clanovi kulturne ustanove":
				new StaticReport("reports/report3.jrxml", "getClanKulturneUstanove");
				break;
			case "About":
				About.initialize();
				break;
			case "Documentation":
				File htmlFile = new File(System.getProperty("user.dir") + "/doc/index.html");;
				try {
					Desktop.getDesktop().browse(htmlFile.toURI());
				} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				}
				break;
			case "Exit":
				System.exit(0);
				break;
			default:
				JOptionPane.showMessageDialog(null, "Komanda nije prepoznata.");
			break;
		}

	}
}
