package view.state;

import view.View;

/**
 * State interfejs.
 * 
 * @author Grupa 1
 *
 */
public interface State 
{
	public void doAction(View view);
	@Override
	public String toString();
}
