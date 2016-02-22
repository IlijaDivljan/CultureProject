package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.state.Ready;
import model.Application;

import com.javadocking.DockingManager;
import com.javadocking.dock.Position;
import com.javadocking.dock.SplitDock;
import com.javadocking.dock.TabDock;
import com.javadocking.dockable.DefaultDockable;
import com.javadocking.dockable.Dockable;
import com.javadocking.dockable.DockingMode;
import com.javadocking.model.FloatDockModel;

/**
 * Klasa za inicijalizovanje grafickih komponenti  i prikaza aplikacije.
 * 
 * @author Grupa 1
 */
public class View extends JFrame
{	
	private static final long serialVersionUID = 1L;
	
	public TableComponent tablePanel;
	public MenuBar menuBar;
	public ToolBar toolBar;
	public StatusBar statusBar;
	public DatabaseExplorer dbExplorer;	
	private InputView inputPanel;
	private Application model;
		
	public View(Application model)
	{
		this.model = model;
		tablePanel = new TableComponent(this);
		menuBar = new MenuBar();
		toolBar = new ToolBar();
		statusBar = new StatusBar();
		dbExplorer = new DatabaseExplorer();
		inputPanel = new InputView(this);
		
		/*
		 * Registrovanje observera. Postoji i ljepsi nacin za ovo, tako da se sam Subject
		 * proslijedi Observeru unutar konstruktora i registruje ga iznutra. Ali za ovu priliku
		 * i ovo radi.
		 */
		
		model.regiserObserver(statusBar);
		model.regiserObserver(toolBar);
		model.regiserObserver(menuBar);
		model.regiserObserver(inputPanel);
		model.regiserObserver(tablePanel);
		
		model.setCurrentState(new Ready());
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//********javadocking*************
		FloatDockModel docking = new FloatDockModel();
		docking.addOwner("Kultura+", this);
		DockingManager.setDockModel(docking);		

		TabDock dbExplorerTab = new TabDock();
		TabDock tablePanelTab = new TabDock();
		TabDock inputPanelTab = new TabDock();
		
		Dockable dbExplorerDock = new DefaultDockable("dck1", dbExplorer, "DB Browser", null,
				DockingMode.ALL - DockingMode.FLOAT);
		Dockable tablePanelDock = new DefaultDockable("dck2", tablePanel, "Table View", null, DockingMode.ALL
				- DockingMode.FLOAT);
		Dockable inputPanelDock = new DefaultDockable("dck3", inputPanel, "Input Fields", null,
				DockingMode.ALL - DockingMode.FLOAT);

		dbExplorerTab.addDockable(dbExplorerDock, new Position(0));
		tablePanelTab.addDockable(tablePanelDock, new Position(0));
		inputPanelTab.addDockable(inputPanelDock, new Position(0));

		SplitDock mainSplit = new SplitDock();
		SplitDock rightSplit = new SplitDock();

		rightSplit.addChildDock(tablePanelTab, new Position(Position.TOP));
		rightSplit.addChildDock(inputPanelTab, new Position(Position.BOTTOM));
		rightSplit.setDividerLocation((int) (toolkit.getScreenSize().getHeight() / 2 -150));
		
		mainSplit.addChildDock(dbExplorerTab, new Position(Position.LEFT));
		mainSplit.addChildDock(rightSplit, new Position(Position.RIGHT));
		mainSplit.setDividerLocation((int) (toolkit.getScreenSize().getWidth() / 6));
		
		docking.addRootDock("window", mainSplit, this);
		//********************************
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(toolBar, BorderLayout.NORTH);
		panel.add(statusBar, BorderLayout.SOUTH);
		panel.add(mainSplit, BorderLayout.CENTER);
		
		//sakrivanje komandi na toolbaru i menibaru
		//toolBar.disableCommands();
		//menuBar.disableCommands();

		setJMenuBar(menuBar);
		setContentPane(panel);
		
		
		
		this.setSize(screenSize.width*2/3, screenSize.height*2/3);
		this.setLocation(screenSize.width/2-(screenSize.width*2/3)/2,
				screenSize.height/2-(screenSize.height*2/3)/2);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		this.setTitle("Kultura+");
		ImageIcon img = new ImageIcon("images/appIcon.png");
		this.setIconImage(img.getImage());
		this.setVisible(true);		
	}

	public TableComponent getTablePanel() {
		return tablePanel;
	}

	public ToolBar getToolBar() {
		return toolBar;
	}

	public StatusBar getStatusBar() {
		return statusBar;
	}

	public DatabaseExplorer getDbExplorer() {
		return dbExplorer;
	}

	public InputView getInputPanel() {
		return inputPanel;
	}

	public Application getModel() {
		return model;
	}
	

}
