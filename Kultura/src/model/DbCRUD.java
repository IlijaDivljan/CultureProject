package model;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Vector;

import javax.swing.JOptionPane;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import view.View;
import view.components.BaseField;
import view.state.Insert;

/**
 * Klasa koja sadrzi staticke metode za rad sa bazom i osnovne CRUD operacije.
 * 
 * @author Grupa 1
 *
 */
public class DbCRUD 
{
	private static Vector<ColumnElement> columns = null;
	private static Vector<BaseField> fields = null;
	private static Vector<String> values = null;
	
	
	
	/**
	 * CRUD operacija insert.
	 * @param view
	 */
	public static void insert(View view)
	{
		columns = view.getInputPanel().getColumns();
		fields = view.getInputPanel().getFields();
		
		if(!allowInput(fields))
		{
			return;
		}
		
		String procedure = view.getModel().getCurrentTable().getCreateSProc() + "(";
		
		for(int i = 0; i < columns.size()-1; i++)
		{
			procedure +="?,";
		}
		procedure += "?)";
		
		try 
		{
			CallableStatement calstmt = Application.getConnection().conn.prepareCall("{ call "+procedure+" }", SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			
			for(int i = 1; i <= fields.size(); i++)
			{
				if(fields.get(i-1).getValue().toString().equals(""))
				{
					String type = columns.get(i-1).getType();
					
					if(type.contains("varchar"))
					{
						calstmt.setNull(i, Types.VARCHAR);
					}
					else if(type.contains("char"))
					{
						calstmt.setNull(i, Types.CHAR);
					}
					else if(type.contains("numeric"))
					{
						calstmt.setNull(i, Types.NUMERIC);
					}
					else if(type.contains("int"))
					{
						calstmt.setNull(i, Types.INTEGER);
					}
					else if(type.contains("bit"))
					{
						calstmt.setNull(i, Types.BIT);
					}
					else if(type.contains("float"))
					{
						calstmt.setNull(i, Types.FLOAT);
					}
					else if(type.contains("datetime"))
					{
						calstmt.setNull(i, Types.TIMESTAMP);
					}
					else if(type.contains("date"))
					{
						calstmt.setNull(i, Types.DATE);
					}
				}
				else
				{
					calstmt.setObject(i, fields.get(i-1).getValue().toString());
				}	
			}
			
			calstmt.execute();
			
			view.getModel().getTableModel().loadTable(view.getModel().getCurrentTable());

			
			
			
			System.out.println("exec { call "+procedure+" } ");
			
			view.getTablePanel().getTable().setRowSelectionInterval(view.getTablePanel().getTable().getRowCount() - 1, view.getTablePanel().getTable().getRowCount() - 1);
			
			view.getModel().setCurrentState(new Insert());
			calstmt.close();
			
			view.getStatusBar().setVisible("Insert successful.");
		} catch (SQLException e) 
		{

			JOptionPane.showMessageDialog(null, "<html><body><p style='width: 240px; word-wrap: normal;'>Operacija INSERT nije izvrsena. Izvjestaj:<br><br>"+e.toString()+"</p></body></html>", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	/**
	 * CRUD operacija update.
	 * Svi primarni kljucevi ulaze u "Where" klauzu stored procedure na serveru. Jedini genericki nacin.
	 * 
	 * @param view
	 */
	public static void update(View view)
	{
		columns = view.getInputPanel().getColumns();
		fields = view.getInputPanel().getFields();
		values = view.getInputPanel().getClonedValues();
		
		
		if(!allowInput(fields))
		{
			return;
		}
		
		int j = 1;
		
		String procedure = view.getModel().getCurrentTable().getUpdateSProc() + "(";
		
		for(int i = 0; i < columns.size()-1; i++)
		{
			procedure +="?,";
			if(columns.elementAt(i).isPrimary())
			{
				procedure += "?,";
			}
			
		}
		if(columns.elementAt(columns.size()-1).isPrimary())
		{
			procedure += "?,?)";
		}
		else
		{
			procedure += "?)";
		}
		
		try 
		{
			CallableStatement calstmt = Application.getConnection().conn.prepareCall("{ call "+procedure+" }", SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			
			//Update dio
			for(int i = 1; i <= fields.size(); i++)
			{
				if(fields.get(i-1).getValue().toString().equals(""))
				{
					String type = columns.get(i-1).getType();
					
					if(type.contains("varchar"))
					{
						calstmt.setNull(j++, Types.VARCHAR);
					}
					else if(type.contains("char"))
					{
						calstmt.setNull(j++, Types.CHAR);
					}
					else if(type.contains("numeric"))
					{
						calstmt.setNull(j++, Types.NUMERIC);
					}
					else if(type.contains("int"))
					{
						calstmt.setNull(j++, Types.INTEGER);
					}
					else if(type.contains("bit"))
					{
						calstmt.setNull(j++, Types.BIT);
					}
					else if(type.contains("float"))
					{
						calstmt.setNull(j++, Types.FLOAT);
					}
					else if(type.contains("datetime"))
					{
						calstmt.setNull(j++, Types.TIMESTAMP);
					}
					else if(type.contains("date"))
					{
						calstmt.setNull(j++, Types.DATE);
					}
				}
				else
				{
					calstmt.setObject(j++, fields.get(i-1).getValue().toString());
				}	

			}
			//Where dio
			for(int i = 0; i < values.size(); i++)
			{
				if(columns.get(i).isPrimary())
				{
					calstmt.setObject(j++, values.get(i));
				}
			}
			
			calstmt.execute();
			
			int selectedRow = view.getTablePanel().getTable().getSelectedRow();
			
			view.getModel().getTableModel().loadTable(view.getModel().getCurrentTable());
		
			System.out.println("exec { call "+procedure+" } ");
			
			view.getTablePanel().getTable().setRowSelectionInterval(selectedRow, selectedRow);
			
			calstmt.close();
			
			view.getStatusBar().setVisible("Update successful.");
		} catch (SQLException e) 
		{
			JOptionPane.showMessageDialog(null, "<html><body><p style='width: 240px; word-wrap: normal;'>Operacija UPDATE nije izvrsena. Izvjestaj:<br><br>"+e.toString()+"</p></body></html>", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
	
	/**
	 * CRUD operacija delete.
	 * @param view
	 */
	public static void delete(View view)
	{
		columns = view.getInputPanel().getColumns();
		fields = view.getInputPanel().getFields();
		
		String procedure = view.getModel().getCurrentTable().getDeleteSProc() + "(?";
		
		for(int i = 1; i < columns.size()-1; i++)
		{
			if(columns.elementAt(i).isPrimary())
			{
				procedure += ",?";
			}
			
		}
		if(columns.elementAt(columns.size()-1).isPrimary())
		{
			procedure += ",?)";
		}
		else
		{
			procedure += ")";
		}
		
		
		try 
		{
			CallableStatement calstmt = Application.getConnection().conn.prepareCall("{ call "+procedure+" }", SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			
			for(int i = 1; i <= fields.size(); i++)
			{
				if(fields.get(i-1).getColumn().isPrimary())
				{
					calstmt.setObject(i, fields.get(i-1).getValue().toString());
				}	
			}
			
			calstmt.execute();
			
			int selectedRow = view.getTablePanel().getTable().getSelectedRow();
			
			view.getModel().getTableModel().loadTable(view.getModel().getCurrentTable());
			
			System.out.println("exec { call "+procedure+" } ");
			
			if(selectedRow ==0)
			{
				view.getTablePanel().getTable().setRowSelectionInterval(0, 0);
			}
			else
			{
				view.getTablePanel().getTable().setRowSelectionInterval(selectedRow-1, selectedRow-1);
			}
			
			calstmt.close();
			
			view.getStatusBar().setVisible("Delete successful.");
		} catch (SQLException e) 
		{

			JOptionPane.showMessageDialog(null, "<html><body><p style='width: 240px; word-wrap: normal;'>Operacija DELETE nije izvrsena. Izvjestaj:<br><br>"+e.toString()+"</p></body></html>", 
					"Error", 
					JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Metoda testira da li su popunjena odgovarajuca polja.
	 * @param inputFields
	 * @return
	 */
	private static boolean allowInput(Vector<BaseField> inputFields)
	{
		String outputS = "Polja sa * pored naziva:\n\n";
		Boolean outputB = true;
		
		for(int i = 0; i < inputFields.size(); i++)
		{
			if(!inputFields.get(i).getColumn().isNullable())
			{
				if(inputFields.get(i).getValue().toString().equals(""))
				{
					outputS +="\t" + inputFields.get(i).getColumn().getName() + ";\n";
					outputB = false;
				}
			}
		}
		
		if(!outputB)
		{
			outputS += "\nMoraju biti popunjena.";
			JOptionPane.showMessageDialog(null, outputS);
			return outputB;
		}
		return outputB;
	}
}
