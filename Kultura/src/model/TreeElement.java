package model;

import java.util.Vector;

/**
 * Klasa predstavlja opis strukture stabla iz xml fajla baze.
 * 
 * @author Grupa 1
 */
public abstract class TreeElement 
{		
	private Vector<TreeElement> treeElements = new Vector<>();
	
	protected String code = null;
	protected String name = null;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString() 
	{
		return this.name;
	}
	
	public int getIndexOfElement(TreeElement el) 
	{
		return treeElements.indexOf(el);
	}
	
	public void addElement(TreeElement element)
	{
		treeElements.add(element);
	}
	
	public Vector<TreeElement> getAllElements() 
	{
		return this.treeElements;
	}
	
	public TreeElement getElementAt(int indeks) 
	{
		return treeElements.elementAt(indeks);
	}
	
	/**
	 * Specijalizacija paket.
	 * @author Grupa 1
	 */
	public static class Package extends TreeElement
	{
		public Package() {}
		
		public Package(String name) 
		{
			this.name = name;
		}
	}
	
/*	*//**
	 * Specijalizacija tabela.
	 * @author Grupa 1
	 *//*
	public static class Table extends TreeElement 
	{
		private Vector<Table> reference = new Vector<>();
		
		private String createSProc = null;
		private String retrieveSProc = null;
		private String updateSProc = null;
		private String deleteSProc = null;
		
		public Table() {}
		
		public void addReferenca(Table referenca) 
		{
			reference.add(referenca);
		}
		
		public Vector<Table> getAllReference() 
		{
			return this.reference;
		}
		
		public Table getRefTabelaAt(int indeks) 
		{
			return reference.elementAt(indeks);
		}

		public String getCreateSProc() {
			return createSProc;
		}

		public void setCreateSProc(String createSProc) {
			this.createSProc = createSProc;
		}

		public String getRetrieveSProc() {
			return retrieveSProc;
		}

		public void setRetrieveSProc(String retrieveSProc) {
			this.retrieveSProc = retrieveSProc;
		}

		public String getUpdateSProc() {
			return updateSProc;
		}

		public void setUpdateSProc(String updateSProc) {
			this.updateSProc = updateSProc;
		}

		public String getDeleteSProc() {
			return deleteSProc;
		}

		public void setDeleteSProc(String deleteSProc) {
			this.deleteSProc = deleteSProc;
		}
	}

	*//**
	 * Specijalizacija kolona.
	 * @author Grupa 1
	 *//*
	public static class Column extends TreeElement 
	{
		private boolean nullable = false;
		private boolean primary = false;
		private int size = 0;
		private int group = 0;
		private String type = "";
		private String refTable = null;
		private String refColumn = null;
				
		public Column() {}
		
		public Boolean isNullable() 
		{
			return nullable;
		}
		
		public void setNullable(Boolean nullable) 
		{
			this.nullable = nullable;
		}
		
		public Boolean isPrimary() 
		{
			return primary;
		}
		
		public void setPrimary(Boolean primary) 
		{
			this.primary = primary;
		}
		
		public void setSize(String size)
		{
			try
			{
				this.size = Integer.parseInt(size);
			}
			catch(Exception e){}
		}
		
		public int getSize()
		{
			return this.size;
		}
		
		public void setType(String type)
		{
			this.type = type;
		}
		
		public String getType()
		{
			return this.type;
		}
		
		public void setGroup(String group)
		{
			try
			{
				this.group = Integer.parseInt(group);
			}
			catch(Exception e){}
		}
		
		public int getGroup()
		{
			return this.group;
		}

		public String getRefTable() 
		{
			return refTable;
		}

		public void setRefTable(String refTable) 
		{
			this.refTable = refTable;
		}

		public String getRefColumn() 
		{
			return refColumn;
		}

		public void setRefColumn(String refColumn) 
		{
			this.refColumn = refColumn;
		}
	}*/
}
