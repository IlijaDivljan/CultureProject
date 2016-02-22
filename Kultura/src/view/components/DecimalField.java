package view.components;

import java.awt.Color;
import java.text.DecimalFormat;

import javax.swing.JFormattedTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import model.ColumnElement;

/**
 * Klasa definise polja tipa decimal.
 * 
 * @author Grupa 1
 */
public class DecimalField extends BaseField 
{
	private static final long serialVersionUID = 1L;
	
	
	private JFormattedTextField valueField;
	

	public DecimalField(ColumnElement columnv) 
	{
		super(columnv);
		
		valueField = new JFormattedTextField(new DecimalFormat("#.#"));
		
		valueField.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) 
			{
				JFormattedTextField jftf = (JFormattedTextField) e.getSource();
				if((column.getSize() != 0) && (jftf.getText().length() > column.getSize()))
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
		add(valueField);
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
