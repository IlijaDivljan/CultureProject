package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.View;
import view.state.Read;
import model.Application;

/**
 * Controller InputView klase.
 * @author Ilija Divljan
 *
 */
public class InputViewController implements ActionListener 
{
	Application model;
	View view;
	
	public InputViewController(Application model, View view) 
	{
		this.model = model;
		this.view = view;
		
		this.view.getInputPanel().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String action = e.getActionCommand();
		
		switch (action) 
		{
			case "accept":
				this.view.getStatusBar().setHidden();
				model.getCurrentState().doAction(this.view);
				break;
			case "discard":
				this.model.setCurrentState(new Read());
				break;
			default:
				break;
		}
	}

}
