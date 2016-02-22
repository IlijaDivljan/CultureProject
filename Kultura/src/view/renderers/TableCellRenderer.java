package view.renderers;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Klasa koja formatira ispis podataka u tabeli.
 * 
 * @author Grupa 1
 *
 */
public class TableCellRenderer extends DefaultTableCellRenderer
{
	private static final long serialVersionUID = 1L;
	
	SimpleDateFormat sdfD = new SimpleDateFormat("dd.MM.yyyy");
	SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm dd.MM.yyyy");
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) 
	{
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		if (isSelected)
		{
			l.setBackground(new Color(51, 153, 255));
			l.setForeground(Color.white);
		}
		else
		{
			if(row % 2 == 0)
			{
				l.setBackground(new Color(234, 244, 255));
				l.setForeground(Color.black);
			}
			else
			{
				l.setBackground(Color.white);
				l.setForeground(Color.black);
			}
		}
		
		if(value instanceof Integer & value != null)
		{
			l.setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(value instanceof BigDecimal & value != null)
		{
			l.setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(value instanceof Double & value != null)
		{
			l.setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(value instanceof Float & value != null)
		{
			l.setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(value instanceof Long & value != null)
		{
			l.setHorizontalAlignment(JLabel.RIGHT);
		}
		else if(value instanceof Date & value != null)
		{
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setText(sdfD.format(value));
		
		}
		else if(value instanceof Timestamp & value != null)
		{
			l.setHorizontalAlignment(JLabel.CENTER);
			l.setText(sdfT.format(value));
		}
		else if(value instanceof String & value != null)
		{
			if (value.toString().length() > 20) 
			{
				l.setToolTipText(value.toString());
			}
			else
			{
				l.setToolTipText("");
			}
	
			l.setHorizontalAlignment(JLabel.LEFT);
			l.setText(value.toString());
		}
		
		/*if(row == 0 || row == 1)
		{
			if(value instanceof Integer & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(120);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			}
			else if(value instanceof BigDecimal & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(120);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			}
			else if(value instanceof Double & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(120);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			}
			else if(value instanceof Float & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(120);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			}
			else if(value instanceof Long)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(120);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			}
			else if(value instanceof Date & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(100);
				table.getColumnModel().getColumn(column).setMinWidth(70);	
			
			}
			else if(value instanceof Timestamp & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(150);
				table.getColumnModel().getColumn(column).setMinWidth(100);	
			}
			else if(value instanceof String & value != null)
			{
				if(value.toString().length() < 5)
				{
					table.getColumnModel().getColumn(column).setPreferredWidth(100);
					table.getColumnModel().getColumn(column).setMinWidth(70);	
				}
				else
				{
					table.getColumnModel().getColumn(column).setPreferredWidth(175);
					table.getColumnModel().getColumn(column).setMinWidth(100);	
				}
			}
			else if(value instanceof Object & value != null)
			{
				table.getColumnModel().getColumn(column).setPreferredWidth(100);
				table.getColumnModel().getColumn(column).setMinWidth(70);
			}
			
		}*/
		return l;
	}
}
