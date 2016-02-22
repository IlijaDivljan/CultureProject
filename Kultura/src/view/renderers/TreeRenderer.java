package view.renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import model.ColumnElement;
import model.TreeElement;

/**
 * Klasa predstavlja CustomTreeCellRenderer koji definise izgled stabla.
 * 
 * @author Grupa 1
 */
public class TreeRenderer extends DefaultTreeCellRenderer 
{	
	private static final long serialVersionUID = 1L;	

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus)
	{
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		//setFont(new Font("monospaces", Font.PLAIN, 14));
		setFont(new Font("Segoe UI", Font.PLAIN, 14));
		setText(value.toString());		
		setBackgroundSelectionColor(Color.LIGHT_GRAY);
		setBorderSelectionColor(Color.LIGHT_GRAY);
		

		String option = value.getClass().getSimpleName();
		if(leaf)
		{
			String tooltip = "<html><p>";
			tooltip += "Name: " +  ((ColumnElement)value).getName() + "<br>";
			tooltip += "Code: " +  ((ColumnElement)value).getCode() + "<br>";
			tooltip += "Primary: " +  (((ColumnElement)value).isPrimary() ? "Yes" : "No")+ "<br>";
			tooltip += "Nullable: " +  (((ColumnElement)value).isNullable() ? "Yes" : "No")+ "<br>";
			tooltip += "Type: " +((ColumnElement)value).getType() +"<br>";
			tooltip += "Size: " +((ColumnElement)value).getSize() +"<br>";
			tooltip += "Foreign: " +  (((ColumnElement)value).isForeign() ? "Yes" : "No")+ "<br>";
			if(((ColumnElement)value).isForeign())
			{
				tooltip += "Ref. Column: " + ((ColumnElement)value).getRefColumn() +"<br>";
				tooltip += "Ref. Table: " + ((ColumnElement)value).getRefTable() +"<br>";
			}
			tooltip += "</p></html>";
			setToolTipText(tooltip);
		}
		else if(value instanceof TreeElement)
		{
			String tooltip = "<html><p>";
			tooltip += ((TreeElement)value).getName() + "<br>";
			tooltip += "Contains: <br><br>";
			
			for(TreeElement treeEl : ((TreeElement) value).getAllElements())
			{
				tooltip += treeEl.getName() + "<br>";
			}
			
			tooltip += "</p></html>";
			setToolTipText(tooltip);
		}
		switch(option)
		{		
		case "Package":
			//setIcon(new ImageIcon("images/packageIconLarge.png"));
			setIcon(new ImageIcon("images/package24.png"));
			//setIcon(new ImageIcon("images/packageIcon24.png"));
			break;
		case "TableElement":
			//setIcon(new ImageIcon("images/tableIconLarge.png"));
			setIcon(new ImageIcon("images/table24.png"));
			//setIcon(new ImageIcon("images/tableIcon24.png"));
			break;
		case "ColumnElement":
			//setIcon(new ImageIcon("images/columnIconLarge.png"));
			setIcon(new ImageIcon("images/element24.png"));
			//setIcon(new ImageIcon("images/columnIcon24.png"));
			break;
		default:
			//setIcon(new ImageIcon("images/unknowIconLarge.png"));
			setIcon(new ImageIcon("images/unknowIconLarge.png"));
			break;
		}
		//da obuhvati cvor
		if(value.toString().equals("PIS_Projekti"))
			//setIcon(new ImageIcon("images/databaseIconLarge.png"));
			setIcon(new ImageIcon("images/database24.png"));
			//setIcon(new ImageIcon("images/databaseIcon24.png"));

		return this;
	}

}
