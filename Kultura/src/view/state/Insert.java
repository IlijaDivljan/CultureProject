package view.state;

import java.lang.Override;

import model.DbCRUD;
import view.View;

/**
 * Klasa definise insert stanje.
 * 
 * @author Grupa 1
 */
public class Insert implements State 
{

	@Override
	public void doAction(View view)
	{
		DbCRUD.insert(view);
	}

	@Override
	public String toString() 
	{
		return "Insert";
	}

	
}
