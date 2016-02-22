package view.state;

import model.DbCRUD;
import view.View;

/**
 * Klasa definise update stanje.
 * 
 * @author Grupa 1
 */
public class Update implements State {

	@Override
	public void doAction(View view) 
	{
		DbCRUD.update(view);
	}

	@Override
	public String toString() 
	{
		return "Update";
	}
}
