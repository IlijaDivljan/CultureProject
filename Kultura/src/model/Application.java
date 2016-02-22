package model;

import java.util.Vector;

import view.View;
import view.observer.Observer;
import view.observer.Subject;
import view.state.State;

/**
 * 
 * Klasa predstavlja model koji se koristi u MVC arhitekturi ove aplikacije.
 * 
 * @author Grupa1
 *
 */
public class Application implements Subject
{
	public static DatabaseData connection; //singleton konekcija
	public static String userName; //ime korisnika
	public TableModel tableModel;
	public TableElement currentTable;
	public State currentState = null;
	public Vector<Observer> observers = new Vector<Observer>();
	
	private static Application instance = null;
	
	
	protected Application(){}
	
	/*
	 * Klasa Application je singleton klasa.
	 */
	public static Application getInstance()
	{
		if(instance == null)
		{
			instance = new Application();
		}
		return instance;
	}
	
	
	public TableModel getTableModel() 
	{
		return tableModel;
	}
	public void setTableModel(TableModel tableModel) 
	{
		this.tableModel = tableModel;
	}
	public TableElement getCurrentTable() 
	{
		return currentTable;
	}
	public void setCurrentTable(TableElement currentTable) 
	{
		this.currentTable = currentTable;	
	}
	public State getCurrentState() 
	{
		return currentState;
	}
	public void setCurrentState(State currentState) 
	{
		this.currentState = currentState;
		/*
		 * Obavjestavamo observere o promjeni stanja. Sve promjene stanja idu preko ovog setera.
		 */
		notifyObserversStateChanged(this);
		
	}

	public static DatabaseData getConnection() 
	{
		return connection;
	}

	/*
	 * Metode za obavjestavanje observera o promjenama stanja ili selekcije u tabeli.
	 * 
	 */
	@Override
	public void regiserObserver(Observer observer)
	{
		this.observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) 
	{
		this.observers.remove(observer);
	}

	@Override
	public void notifyObserversStateChanged(Application model) 
	{
		for(Observer ob : observers)
		{
			ob.updateStateChanged(model);
		}
	}

	@Override
	public void notifyObserversSelectionChanged(View view) 
	{
		for(Observer ob : observers)
		{
			ob.updateSelectionChanged(view);
		}
	}


	@Override
	public void notifyObserversTableChanged(View view) 
	{
		for(Observer ob : observers)
		{
			ob.updateTableChanged(view);
		}
	}
		
}
