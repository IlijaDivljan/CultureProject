package view.state;

import model.DbCRUD;
import view.View;

/**
 * Klasa definise delete stanje.
 * 
 * @author Grupa 1
 */
public class Delete implements State 
{

	@Override
	public void doAction(View view)
	{
		DbCRUD.delete(view);
	}

	@Override
	public String toString() 
	{
		return "Delete";
	}

	
}
