package view.observer;

import view.View;
import model.Application;


/**
 * Subject interfejs za observer dizajn sablon.
 * 
 * @author Grupa 1
 *
 */
public interface Subject 
{
	public void regiserObserver(Observer observer);
	public void removeObserver(Observer observer);
	
	
	public void notifyObserversStateChanged(Application model);
	public void notifyObserversSelectionChanged(View view);
	public void notifyObserversTableChanged(View view);
}
