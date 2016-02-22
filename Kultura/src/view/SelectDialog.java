package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import model.Application;
import view.components.LinkedField;
import view.renderers.TableBooleanFieldRenderer;
import view.renderers.TableCellRenderer;


/**
 * Klasa SelectDialog sluzi za dohvacanje vrijednosti stranog kljuca u LinkedField.
 * @author Grupa 1
 *
 */
public class SelectDialog extends JDialog implements ActionListener
{
	
	private static final long serialVersionUID = 1L;
	
	private LinkedField field;
	
	private JButton acceptButton, cancelButton;
	
	private TableComponent table;
	
	private SelectTableModel stModel;
	
	
	public SelectDialog(LinkedField field) 
	{
		this.field = field;
		
		setTitle(field.getColumn().getRefTable());
		setSize(600,500);
		setMinimumSize(new Dimension(600, 500));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setModal(true);
		
		initializeWindow();
		
		setVisible(true);
	}
	
	private void initializeWindow()
	{
		//setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		setLayout(new BorderLayout());
		
		ToolBar tb = new ToolBar();
		tb.setSelectDisplay();
		tb.setListeners(this);
		tb.setVisible(true);
		
		
		add(tb, BorderLayout.NORTH);
		
		
		table = new TableComponent(null);
		stModel = new SelectTableModel();
		stModel.loadTable(field.getColumn().getRefTable());
		
		table.getTable().setModel(stModel);
		table.getTable().setRowHeight(25);
		table.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getTable().setRowSelectionInterval(0, 0);
		table.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
		
		table.getTable().setDefaultRenderer(Object.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(Integer.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(String.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(Boolean.class, new TableBooleanFieldRenderer());
		table.getTable().setDefaultRenderer(Date.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(Timestamp.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(BigDecimal.class, new TableCellRenderer());
		table.getTable().setDefaultRenderer(Double.class, new TableCellRenderer());
		
		
		table.setViewportView(table.getTable());
		table.getRowHeader().setVisible(false);
		
		
		
		
		
		
		add(table, BorderLayout.CENTER);
		
		
		acceptButton = new JButton("Select", new ImageIcon("images/acceptIcon.png"));
		cancelButton = new JButton("Cancel", new ImageIcon("images/cancelIcon.png"));
		
		acceptButton.setActionCommand("ok");
		cancelButton.setActionCommand("cancel");
		
		acceptButton.addActionListener(this);
		cancelButton.addActionListener(this);
		
		Container cnt = new Container();
		cnt.setPreferredSize(new Dimension(300, 40));
		cnt.setLayout(new BoxLayout(cnt, BoxLayout.LINE_AXIS));
		cnt.add(Box.createHorizontalStrut(60));
		cnt.add(acceptButton);
		cnt.add(Box.createHorizontalGlue());
		cnt.add(cancelButton);
		cnt.add(Box.createHorizontalStrut(60));
		
		add(cnt, BorderLayout.SOUTH);
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		switch(e.getActionCommand())
		{
			case "ok":
				//System.out.println(stModel.getValueAt(table.getTable().getSelectedRow(), stModel.getColumnIndex(field.getColumn().getRefColumn())));
				field.setValue(stModel.getValueAt(table.getTable().getSelectedRow(), stModel.getColumnIndex(field.getColumn().getRefColumn())));
				this.dispose();
				break;
			case "cancel":
				this.dispose();
				break;
			case "First":
				table.getTable().setRowSelectionInterval(0, 0);
				break;
			case "Last": 
				table.getTable().setRowSelectionInterval(table.getTable().getRowCount() - 1, table.getTable().getRowCount() - 1);
				break;
			case "Next":
				table.getTable().setRowSelectionInterval(table.getTable().getSelectedRow() + 1, table.getTable().getSelectedRow() + 1);
				break;
			case "Previous":
				table.getTable().setRowSelectionInterval(table.getTable().getSelectedRow() - 1, table.getTable().getSelectedRow() - 1);
				break;
		}
		
	}
	
	/**
	 * Abstraktni table model koji dohvaca tabelu iz baze na osnovu njenog imena.
	 * @author Grupa 1
	 *
	 */
	
	public class SelectTableModel extends AbstractTableModel
	{
		private static final long serialVersionUID = 1L;
		
		
		private Vector<String> columnNames;
		private Vector<Vector<Object>> rows;
		private ResultSet rs = null;
		private ResultSetMetaData rsmd = null;
		private Statement stmt = null;
		
		public void loadTable(String table)
		{
			String sql = "SELECT * FROM "+ table;
			try 
			{
				Application.connection.connect();
				this.stmt = Application.connection.conn.createStatement(SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
				this.rs = this.stmt.executeQuery(sql);
				this.rsmd = rs.getMetaData();
				
				this.columnNames = new Vector<>();
				this.rows = new Vector<Vector<Object>>();
				
				this.columnNames.add("#");
				for(int i = 0; i < rsmd.getColumnCount(); i++)
				{
					this.columnNames.add(rsmd.getColumnName(i+1));
				}
				int j=0;
				while(rs.next())
				{					
					Vector<Object> row = new Vector<Object>();
					row.add(++j +".");
					for(int i = 0; i < rsmd.getColumnCount(); i++)
					{
						row.add(rs.getObject(i+1));
					}
					this.rows.add(row);
				}
				
				
			} catch (SQLException e) 
			{
				JOptionPane.showMessageDialog(null, "Greska, provjerite konekciju na bazu!");
			}
			
		}
		
		
		@Override
		public String getColumnName(int column) 
		{
			// TODO Auto-generated method stub
			return columnNames.get(column);
		}
		@Override
		public int getColumnCount() 
		{
			return columnNames.size();
		}

		@Override
		public int getRowCount() 
		{
			return rows.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) 
		{
			try
			{
				return rows.get(arg0).get(arg1);	
			}catch(Exception e)	
			{			
				return null;
			}
				
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) 
		{
			// TODO Auto-generated method stub
			try
			{
				return getValueAt(0, columnIndex).getClass();
			}
			catch (Exception e)
			{
				return Object.class;
			}
		}



		public Vector<String> getColumnNames() 
		{
			return columnNames;
		}
		
		public int getColumnIndex(String column)
		{
			return columnNames.indexOf(column);	
		}
		
		public Vector<Vector<Object>> getRows() 
		{
			return rows;
		}
	}

}
