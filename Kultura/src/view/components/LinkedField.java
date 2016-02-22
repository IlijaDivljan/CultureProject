package view.components;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;

import view.SelectDialog;
import model.ColumnElement;

/**
 * Klasa definise polja vezana polja (Linked Fields).
 * 
 * @author Grupa 1
 */
public class LinkedField extends BaseField 
{

	private static final long serialVersionUID = 1L;

	
	private JButton newSelection;
	
	private JTextField valueField;
	
	private Container linkedField;
	
	
	public LinkedField(ColumnElement columnv) 
	{
		super(columnv);
		
		linkedField = new Container();
		
		linkedField.setLayout(new BoxLayout(linkedField, BoxLayout.LINE_AXIS));
		
		newSelection = new JButton("...");
		
		valueField = new JTextField();
		valueField.setEnabled(false);
		
		linkedField.add(valueField);
		linkedField.add(Box.createHorizontalGlue());
		linkedField.add(newSelection);
		
		
		newSelection.addActionListener(new ActionListener() 
		{
			/**
			 * Pozivanje dijaloga za preuzimanje novih vrijednosti.
			 */
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new SelectDialog(getField());
				
			}
		});
		
		newSelection.setEnabled(false);
		add(linkedField);
	}
	
	public LinkedField getField()
	{
		return this;
	}
	
	@Override
	public void setValue(Object value) 
	{
		try
		{
			valueField.setText(value.toString());
		}
		catch (Exception e)
		{
			valueField.setText("");
		}
		
	}

	@Override
	public Object getValue() 
	{
		return valueField.getText();
	}

	@Override
	public void enableEdit(Boolean enable) 
	{
		newSelection.setEnabled(enable);
	}

}
