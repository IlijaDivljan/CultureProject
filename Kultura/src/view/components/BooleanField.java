package view.components;

import javax.swing.JComboBox;

import model.ColumnElement;

/**
 * Klasa definise polja tipa boolean.
 * 
 * @author Grupa 1
 */
public class BooleanField extends BaseField {

	private static final long serialVersionUID = 1L;
	
	private JComboBox<String> valueField;
	

	public BooleanField(ColumnElement columnv) 
	{
		super(columnv);
		
		valueField = new JComboBox<>();
		
		
		valueField.addItem("Da");
		valueField.addItem("Ne");
		
		valueField.setSelectedIndex(1);
		
		valueField.setEnabled(false);
		
		add(valueField);
	}

	@Override
	public void setValue(Object value) 
	{
		if(value != null && value instanceof Boolean)
		{

			if((Boolean)value == true)
			{
				valueField.setSelectedIndex(0);
			}
			else
			{
				valueField.setSelectedIndex(1);
			}
		}
	}

	@Override
	public Object getValue() 
	{
		if(valueField.getSelectedIndex() == 0)
		{
			return 1;
		}
		return 0;
	}

	@Override
	public void enableEdit(Boolean enable) 
	{
		valueField.setEnabled(enable);
	}
	

}
