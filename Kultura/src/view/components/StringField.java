package view.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import model.ColumnElement;

/**
 * Klasa koja implementira String polje
 * @author Grupa 1
 *
 */

public class StringField extends BaseField 
{
	
	private static final long serialVersionUID = 1L;

	
	
	protected JTextField valueField;
	
	
	public StringField(ColumnElement columnv) 
	{
		super(columnv);
		
		Container c = new Container();
		
		c.setLayout(new BorderLayout());
		
		valueField = new JTextField();
		
		c.add(valueField);
		
		valueField.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) 
			{
				JTextField tf = (JTextField)e.getSource();
				if((column.getSize() != 0) && (tf.getText().length() > column.getSize()))
				{
					columnLabel.setForeground(Color.red);
				}
				else
				{
					columnLabel.setForeground(Color.black);
				}
				
			}
		});
		valueField.setEnabled(false);
		add(c, BorderLayout.CENTER);
	}
	
	@Override
	public void setValue(Object value) 
	{
		try
		{
			valueField.setText(value.toString());
		}
		catch(Exception e)
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
		valueField.setEnabled(enable);
	}

}
