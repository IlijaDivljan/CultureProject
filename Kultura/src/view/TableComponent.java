package view;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;











import javax.swing.ScrollPaneConstants;

import view.observer.Observer;
import view.renderers.TableBooleanFieldRenderer;
import view.renderers.TableCellRenderer;
import view.state.Read;
import model.Application;
import model.ColumnElement;
import model.TableModel;


/** 
 * Klasa koja se koristi za generisanje tabele na osnovu DatabaseData modela.
 * 
 * @author Grupa 1
 *
 */
public class TableComponent extends JScrollPane implements Observer
{
	private static final long serialVersionUID = 1L;	
	
	private JTable table;
	private TableModel model;
	@SuppressWarnings("unused")
	private View view;
	JButton jb = new JButton();
	
	private JTable fixedTable = null; //tabela za prikaz rednih brojeva
	
	public TableComponent(View view)
	{
		this.view = view;
		
		table = new JTable()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void setRowSelectionInterval(int index0, int index1)
			{
				int rowCount = super.getRowCount();
				if (rowCount > 0) 
				{
					if (index0 > (rowCount - 1)) 
					{
						super.setRowSelectionInterval(rowCount-1, rowCount-1);
					}
					else if (index0 < 0)
					{
						super.setRowSelectionInterval(0, 0);
					}
					else 
					{
						super.setRowSelectionInterval(index0, index1);
					}
				}
			}	
		};
		
		table.setDefaultRenderer(Object.class, new TableCellRenderer());
		table.setDefaultRenderer(Integer.class, new TableCellRenderer());
		table.setDefaultRenderer(String.class, new TableCellRenderer());
		table.setDefaultRenderer(Boolean.class, new TableBooleanFieldRenderer());
		table.setDefaultRenderer(Date.class, new TableCellRenderer());
		table.setDefaultRenderer(Timestamp.class, new TableCellRenderer());
		table.setDefaultRenderer(BigDecimal.class, new TableCellRenderer());
		table.setDefaultRenderer(Double.class, new TableCellRenderer());
		
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		//kreiranje fiksne tabele za prikaz rednih brojeva
		fixedTable = new JTable()
		{			
			private static final long serialVersionUID = 1L;
			@Override
			public void setRowSelectionInterval(int index0, int index1)
			{				
				int rowCount = super.getRowCount();
				if (rowCount > 0) 
				{
					if (index0 > (rowCount - 1)) 
					{
						super.setRowSelectionInterval(rowCount-1, rowCount-1);
					}
					else if (index0 < 0)
					{
						super.setRowSelectionInterval(0, 0);
					}
					else 
					{
						super.setRowSelectionInterval(index0, index1);
					}
				}
			}
		};
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
		setRowHeaderView(fixedTable);

		getRowHeader().setPreferredSize(
				new Dimension(20, getRowHeader().getPreferredSize().height));	
		
		
		jb.setEnabled(false);
		setCorner(JScrollPane.LOWER_LEFT_CORNER, jb);
	}
			
	public void setModel(TableModel model)
	{		
		this.model = model;
		this.table.setModel(model);
		this.table.setRowSelectionInterval(0, 0);		
		table.setRowHeight(25);
		
		setColumnWidths(this.model.getColumns());
		
		this.fixedTable.setModel(((TableModel) model).getFixedColumn()); 
		fixedTable.setRowHeight(25);
		this.fixedTable.setRowSelectionInterval(0, 0);
		
		this.setViewportView(table);
						
		
	}
	
	public TableModel getModel() 
	{
		return model;
	}

	public JTable getTable() 
	{
		return table;
	}
	
	public void setTable(JTable table)
	{
		this.table = table;
	}
	
	public void setColumnWidths(Vector<ColumnElement> columns)
	{
		Vector<ColumnElement> cols = new Vector<ColumnElement>();
		cols = columns;
		for(int i = 0; i < cols.size(); i++)
		{
			if(cols.get(i).getType().contains("varchar"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(150);
				table.getColumnModel().getColumn(i).setMinWidth(100);
			}
			if(cols.get(i).getType().contains("char"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(130);
				table.getColumnModel().getColumn(i).setMinWidth(70);
			}
			else if(cols.get(i).getType().contains("numeric"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(120);
				table.getColumnModel().getColumn(i).setMinWidth(70);
			}
			else if(cols.get(i).getType().contains("int"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(120);
				table.getColumnModel().getColumn(i).setMinWidth(70);
			}
			else if(cols.get(i).getType().contains("datetime"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(150);
				table.getColumnModel().getColumn(i).setMinWidth(100);	
			}
			else if(cols.get(i).getType().contains("date"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(130);
				table.getColumnModel().getColumn(i).setMinWidth(70);	
			}
			else if(cols.get(i).getType().contains("float"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(120);
				table.getColumnModel().getColumn(i).setMinWidth(70);
			}
			else if(cols.get(i).getType().contains("bit"))
			{
				table.getColumnModel().getColumn(i).setPreferredWidth(100);
				table.getColumnModel().getColumn(i).setMinWidth(70);
			}		
		}
		
	}

	@Override
	public void updateStateChanged(Application model) 
	{
		if(model.getCurrentState().toString() == "Select")
		{
			table.setVisible(false);
			table.getTableHeader().setVisible(false);
			fixedTable.setVisible(false);
			fixedTable.getTableHeader().setVisible(false);
			getHorizontalScrollBar().setVisible(false);
			jb.setVisible(false);
		}
		else
		{
			table.setVisible(true);
			table.getTableHeader().setVisible(true);
			fixedTable.setVisible(true);
			fixedTable.getTableHeader().setVisible(true);
			getHorizontalScrollBar().setVisible(true);
			jb.setVisible(true);
		}
	}

	@Override
	public void updateSelectionChanged(View view) 
	{
		/*
		 * Ovaj dio koda znaci da ako se promjeni selekcija reda tabele
		 * prilikom operacija Insert, Update i Delete aplikacija ce izaci iz tog stanja,
		 * ponistiti unijete promjene i vratiti se u stanje Read. 
		 */
		String state = view.getModel().getCurrentState().toString();
		if((state == "Insert") || (state == "Update"))
		{
			view.getModel().setCurrentState(new Read());
		}
	}

	@Override
	public void updateTableChanged(View view) {}

	public JTable getFixedTable() {
		return fixedTable;
	}

	public void setFixedTable(JTable fixedTable) {
		this.fixedTable = fixedTable;
	}	
}
