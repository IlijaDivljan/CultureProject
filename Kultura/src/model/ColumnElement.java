package model;


/**
 * Klasa predstavlja strukturu kolone i mapira atribude kolone iz baze podataka.
 * @author Grupa 1
 *
 */
public class ColumnElement extends TreeElement
{
	private TableElement parent = null;
	private boolean nullable = false;
	private boolean primary = false;
	private int size = 0;
	private int group = 0;
	private String type = "";
	private String refTable = null;
	private String refColumn = null;
			
	public ColumnElement() {}
	
	public ColumnElement(String name, String code, TableElement parent, boolean nullable, boolean primary, int size, int group, String type, String refTable, String refColumn)
	{
		super.name = name;
		super.code = code;
		this.parent = parent;
		this.nullable = nullable;
		this.primary = primary;
		this.size = size;
		this.group = group;
		this.type = type;
		this.refTable = refTable;
		this.refColumn = refColumn;
	}
	
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
	
	public TableElement getParent() {
		return parent;
	}

	public void setParent(TableElement parent) {
		this.parent = parent;
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
		catch(Exception e){
			this.group = 0;
		}
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
	
	public Boolean isForeign()
	{
		if(this.refColumn != null && this.refTable != null)
		{
			return true;
		}
		return false;
	}
}
