package view.observer;

import view.View;
import model.Application;

/**
 * Observer interfejs.
 * 
 * @author Grupa 1
 *
 */
public interface Observer 
{
	public void updateStateChanged(Application model);
	public void updateSelectionChanged(View view);
	public void updateTableChanged(View view);
}
