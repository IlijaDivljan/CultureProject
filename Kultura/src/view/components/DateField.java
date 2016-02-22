package view.components;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

import model.ColumnElement;

/**
 * Klasa definise polja podataka.
 * 
 * @author Grupa 1
 */
public class DateField extends BaseField
{

	private static final long serialVersionUID = 1L;

	private JFormattedTextField valueField;
	
	
	public DateField(ColumnElement columnv) 
	{
		super(columnv);
		
		MaskFormatter mf = null;
		
		try
		{
			mf = new MaskFormatter("##.##.####");
		}
		catch(ParseException e1)
		{
			e1.printStackTrace();
		}
		
		valueField = new JFormattedTextField(mf);
		valueField.setEnabled(false);
		
		add(valueField);
	}

	@Override
	public void setValue(Object value) 
	{
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		
		try
		{
			valueField.setText(sdf.format(value));
		}
		catch (Exception e)
		{
			valueField.setText("00.00.0000");
		}
	}

	@Override
	public Object getValue() 
	{
		if(!valueField.getText().equals("00.00.0000") && !valueField.getText().equals("  .  .    "))
		{
			try 
			{
				return new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd.MM.yyyy").parse(valueField.getText()));
			} catch (ParseException e) 
			{
				return valueField.getText();
			}
		}
		return "";
	}

	@Override
	public void enableEdit(Boolean enable) 
	{
		valueField.setEnabled(enable);
	}

}
