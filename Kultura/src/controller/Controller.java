package controller;

import view.View;
import model.Application;

/**
 * Glavni kontroler aplikacije.
 * 
 * @author Grupa 1
 */

public class Controller 
{
	public Controller(Application model, View view)
	{
		@SuppressWarnings("unused")
		DBExplorerController explorerContr = new DBExplorerController(model, view);
		@SuppressWarnings("unused")
		ToolbarController toolbarContr = new ToolbarController(model, view);
		@SuppressWarnings("unused")
		MenubarController menubarContr = new MenubarController(model, view);
		@SuppressWarnings("unused")
		TableController tableContr = new TableController(view, model);
		@SuppressWarnings("unused")
		InputViewController inputContr = new InputViewController(model, view);
	}
}
