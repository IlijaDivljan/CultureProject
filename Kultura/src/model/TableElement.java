package model;

import java.util.Vector;

/**
 * Klasa predstavlja strukturu tabele i mapira njena svojstva iz baze podataka.
 * 
 * @author Grupa 1
 *
 */

public class TableElement extends TreeElement
{

	private Vector<TableElement> reference = new Vector<>();
	
	private String createSProc = null;
	private String retrieveSProc = null;
	private String updateSProc = null;
	private String deleteSProc = null;
	
	public TableElement() {}
	
	public void addReferenca(TableElement referenca) 
	{
		reference.add(referenca);
	}
	
	public Vector<TableElement> getAllReference() 
	{
		return this.reference;
	}
	
	public TableElement getRefTabelaAt(int indeks) 
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
