package view.components;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ColumnElement;

/**
 * Roditeljska klasa svih polja editora.
 * @author Grupa 1
 *
 */

public abstract class BaseField extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	protected ColumnElement column;
	
	protected Container componentContainer;
	
	protected JLabel columnLabel;
	
	//protected Object value;
	
	public BaseField(ColumnElement column)
	{
		this.column = column;
		
		this.setMaximumSize(new Dimension(150, 40));
		this.setMinimumSize(new Dimension(150, 40));
		
		this.setLayout(new GridLayout(2, 0));
		
		columnLabel = new JLabel(" " + column.getName() + (column.isNullable() ? "" : " *"));
		
		add(columnLabel);
	}
	
	public ColumnElement getColumn()
	{
		return this.column;
	}
	
	public abstract void setValue(Object value);
	
	public abstract Object getValue();
	
	public abstract void enableEdit(Boolean enable);
	
}
