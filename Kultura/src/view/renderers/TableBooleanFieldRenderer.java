package view.renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer za polja tipa boolean.
 * 
 * @author Grupa 1
 *
 */
public class TableBooleanFieldRenderer extends JCheckBox implements TableCellRenderer
{

	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		if (isSelected)
		{
			setBackground(new Color(51, 153, 255));
			setForeground(Color.white);
		}
		else
		{
			if(row % 2 == 0)
			{
				setBackground(new Color(234, 244, 255));
				setForeground(Color.black);
			}
			else
			{
				setBackground(Color.white);
				setForeground(Color.black);
			}
		}
		
		setSelected((value != null && ((Boolean) value).booleanValue()));
		setHorizontalAlignment(SwingConstants.CENTER);
		
		return this;
	}

}
