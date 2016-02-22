package model;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

/**
 * Klasa predstavlja model tabele koja se prikazuje u TableView-u (klasa TableComponent).
 * 
 * @author Grupa 1
 */
public class TableModel extends AbstractTableModel
{
	private static final long serialVersionUID = 1L;
	
	//table.getTable().getColumnModel().getColumn(0).setMaxWidth(20);
	private Vector<String> columnNames;
	private Vector<String> columnCodes;
    private Vector<Vector<Object>> rows;
    public ResultSet rs = null;
    private CallableStatement callStat = null;
    private TableElement table = null;
    
    private Vector<ColumnElement> columnElements; //lista kolona
    private AbstractTableModel fixedColumn = null; //kolona za prikaz rednih brojeva
    private Vector<Integer> serialNumber = new Vector<>(); //redni brojevi za prikaz kod redova
    
    
    public TableModel()
    {
    	setFixedColumn(new AbstractTableModel()
    	{			
    		private static final long serialVersionUID = 1L;

    		@Override
    		public Object getValueAt(int row, int col) {
    			return serialNumber.elementAt(row) + ".";
    		}
    		@Override
    		public int getRowCount() {
    			return serialNumber.size();
    		}
    		@Override
    		public int getColumnCount() {
    			return 1;
    		}
    		@Override
    		public String getColumnName(int column) {
    			return "#";
    		}
    	});
    }
    //metoda koja dohvaca tabelu iz baze na osnovu imena iz database explorera
    public void loadTable(TableElement table)
    {	
    	//kreiranje kolone za prikaz rednih brojeva (kreira se kao posebna tabela)
    	
    	
    	//prikupljanje informacija o tabeli iz baze i kreiranje modela
    	this.table = table;
    	try
    	{
    		String procedureName = this.table.getRetrieveSProc();
    		if(procedureName.isEmpty())
    		{
    			JOptionPane.showMessageDialog(null, "Procedura nije ispravna.");    			
    		}    		
    		
    		String sql = "{ call " + procedureName + "() }";    		
    		model.Application.connection.connect();    		
    		callStat = model.Application.connection.conn.prepareCall(sql, SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			rs = callStat.executeQuery();
			System.out.println("exec "+sql);
			
			rows = new Vector<Vector<Object>>();
    		this.columnNames = new Vector<>(); 
    		this.columnCodes = new Vector<>();
    		this.columnElements = new Vector<>();
        	for (TreeElement col : this.table.getAllElements())
    		{
    			this.columnNames.add(col.getName());  
    			this.columnCodes.add(col.getCode());
    			this.columnElements.add((ColumnElement) col);
    		}
	        	
    		while (rs.next()) 
	        {
    			Vector<Object> row = new Vector<Object>();
    			
    			for(int i=1; i<columnNames.size()+1; i++)
    			{
    				row.add(rs.getObject(i));
    				//row.add(rs.getString(i));
    			}
    			
    			rows.add(row);
	        }
    		
    		this.fireTableDataChanged();
    		loadFixedTable();
    		
    		
    	}
    	catch(Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "Greska, provjerite konekciju na bazu!");
    	}
    	    	
    	
    	
    	
    }	
    
    public void loadFixedTable()
    {	
		if(serialNumber.size() > 0)
		{
			serialNumber.clear();
		}
		for (int i = 0; i < this.rows.size(); i++) 
		{
			serialNumber.add(i + 1);
		}    
		
		fixedColumn.fireTableDataChanged();		
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

	@Override
	public int getColumnCount() {
		return columnNames.size();		
	}

	@Override
	public int getRowCount() {
		return rows.size();		
	}
	
	@Override
	public java.lang.Class<?> getColumnClass(int c) {
		if(getValueAt(0, c) != null)
		{
			return getValueAt(0, c).getClass();
		}
		else
		{
			if(((ColumnElement)this.table.getElementAt(c)).getType().contains("char"))
			{
				return String.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("numeric"))
			{
				return BigDecimal.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("int"))
			{
				return Integer.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("datetime"))
			{
				return java.sql.Timestamp.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("date"))
			{
				return Date.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("float"))
			{
				return Double.class;
			}
			else if(((ColumnElement)this.table.getElementAt(c)).getType().contains("bit"))
			{
				return Boolean.class;
			}		
			return Object.class;
		}
	};
	
	@Override
	public Object getValueAt(int arg0, int arg1)
	{
		try{
		return rows.get(arg0).get(arg1);	
		}catch(Exception e)	{			
		}
		return null;
	}
	
	public Vector<String> getColumnNames(){
		return this.columnNames;
	}
	
	public Vector<String> getColumnCodes(){
		return this.columnCodes;
	}
	
	public Vector<ColumnElement> getColumns(){
		return this.columnElements;
	}
	
	public Vector<Vector<Object>> getRows() {
		return rows;
	}
	
	public Vector<Object> getRow(int r)
	{
		return rows.get(r);
	}
	public TableElement getTable() {
		return table;
	}
	
	public AbstractTableModel getFixedColumn()
	{
		return fixedColumn;
	}

	public void setFixedColumn(AbstractTableModel fixedColumn)
	{
		this.fixedColumn = fixedColumn;
	}

}
