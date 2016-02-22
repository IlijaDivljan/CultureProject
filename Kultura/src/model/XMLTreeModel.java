package model;

import javax.swing.JOptionPane;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

import org.w3c.dom.*;

/**
 * Klasa sluzi za parsiranje xml fajla i kreiranje strukture baze.
 *  
 * @author Grupa 1
 */
public class XMLTreeModel extends DefaultTreeModel 
{
	private static final long serialVersionUID = 1L;
	
	private Document document;
	private XPathExpression expression;
	private XPath xpath;
	private TreeElement root;	
	
	//default konstruktor
	public XMLTreeModel(TreeNode rootElement)
	{
		super(rootElement);
	}
	
	//konstruktor sa otvaranjem baze
	public XMLTreeModel(TreeNode rootElement, String path) 
	{
		super(rootElement);	
		setConnection(path);
		try 
		{
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();	
			document = builder.parse(path);	
			XPathFactory factory = XPathFactory.newInstance();
			xpath = factory.newXPath();
							
			expression = xpath.compile("/database");
			Node database = (Node) expression.evaluate(document, XPathConstants.NODE);
			
			root = new TreeElement.Package();
			root.setCode(null);
			root.setName(database.getAttributes().getNamedItem("name").getNodeValue());
			
			expression = xpath.compile("package");
			NodeList packages = (NodeList) expression.evaluate(database, XPathConstants.NODESET);
			
			subPackages(packages, root);
		} 
		catch (Exception e) 
		{
			JOptionPane.showMessageDialog(null, "Greska pri parsiranju xml fajla. XML fajl nije u odgovarajucem formatu.");
		} 
	}
	
	//rekurzivna metoda za kreiranje strukture stabla
	private void subPackages(NodeList packages, TreeElement rootPackage) throws XPathExpressionException 
	{
		for (int i = 0; i < packages.getLength(); i++) 
		{
			TreeElement.Package tmpPackage = new TreeElement.Package();
			tmpPackage.setCode(null);
			tmpPackage.setName(packages.item(i).getAttributes().getNamedItem("name").getNodeValue());
			rootPackage.addElement(tmpPackage);
			
			expression = xpath.compile("package");
			NodeList subpacks = (NodeList) expression.evaluate(packages.item(i), XPathConstants.NODESET);
			
			subPackages(subpacks, tmpPackage);
			
			expression = xpath.compile("table");
			NodeList tables = (NodeList) expression.evaluate(packages.item(i), XPathConstants.NODESET);
						
			for (int j = 0; j < tables.getLength(); j++) 
			{
				TableElement tmpTable = new TableElement();
				
				tmpPackage.addElement(tmpTable);
				
				tmpTable.setCode(tables.item(j).getAttributes().getNamedItem("code").getNodeValue());
				tmpTable.setName(tables.item(j).getAttributes().getNamedItem("name").getNodeValue());
				
				expression = xpath.compile("column");
				NodeList columns = (NodeList) expression.evaluate(tables.item(j), XPathConstants.NODESET);
								
				for (int k = 0; k < columns.getLength(); k++) 
				{	
					ColumnElement tmpColumn = new ColumnElement();
					
					tmpColumn.setCode(columns.item(k).getAttributes().getNamedItem("code").getNodeValue());
					tmpColumn.setName(columns.item(k).getAttributes().getNamedItem("name").getNodeValue());
					
					tmpColumn.setNullable(columns.item(k).getAttributes().getNamedItem("nullable").getNodeValue().equalsIgnoreCase("true"));
					tmpColumn.setPrimary(columns.item(k).getAttributes().getNamedItem("primary").getNodeValue().equalsIgnoreCase("true"));
					
					tmpColumn.setSize(columns.item(k).getAttributes().getNamedItem("size").getNodeValue());
					tmpColumn.setType(columns.item(k).getAttributes().getNamedItem("type").getNodeValue());
					tmpColumn.setGroup(columns.item(k).getAttributes().getNamedItem("group").getNodeValue());
					
					try
					{	
						expression = xpath.compile("references");
						NodeList refereneces = (NodeList) expression.evaluate(columns.item(k), XPathConstants.NODESET);
						
						tmpColumn.setRefTable(refereneces.item(0).getAttributes().getNamedItem("refTable").getNodeValue());
						tmpColumn.setRefColumn(refereneces.item(0).getAttributes().getNamedItem("refColumn").getNodeValue());
					}
					catch(Exception e){}
					tmpColumn.setParent(tmpTable);
					tmpTable.addElement(tmpColumn);
				}
								
				expression = xpath.compile("crud/create");
				Node create = (Node) expression.evaluate(tables.item(j), XPathConstants.NODE);
				tmpTable.setCreateSProc(create.getTextContent());
				
				expression = xpath.compile("crud/retrieve");
				Node retrieve = (Node) expression.evaluate(tables.item(j), XPathConstants.NODE);
				tmpTable.setRetrieveSProc(retrieve.getTextContent());
				
				expression = xpath.compile("crud/update");
				Node update = (Node) expression.evaluate(tables.item(j), XPathConstants.NODE);
				tmpTable.setUpdateSProc(update.getTextContent());
				
				expression = xpath.compile("crud/delete");
				Node delete = (Node) expression.evaluate(tables.item(j), XPathConstants.NODE);
				tmpTable.setDeleteSProc(delete.getTextContent());
				
			}
		}	
	}	
	
	//funkcija za parsiranje i uspostavljanje konekcije
	public void setConnection(String path)
    {	
    	try
    	{	
    	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	    DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
    	    Document doc = docBuilder.parse(path);
    	    
    	    NodeList params = doc.getDocumentElement().getElementsByTagName("connection");
    	    
    	    for (int i = 0; i < params.getLength(); i++)
    	    {
    	    	String address = ((Element) params.item(i)).getAttribute("address");
	    		String type = ((Element) params.item(i)).getAttribute("database_type");
	    		String databaseName = ((Element) params.item(i)).getAttribute("database");
	    		String user = ((Element) params.item(i)).getAttribute("username");
	    		String pass = ((Element) params.item(i)).getAttribute("password");
	    		String port = ((Element) params.item(i)).getAttribute("port");
	    		
	    		model.Application.connection = new DatabaseData(address, databaseName, type, pass, port, user);	    		    		
    	    }
    	    	
    	} 
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "Greska pri konekciji!\nXML fajl nije u dobrom formatu.");	    		
    	}	    	
    }
			
	@Override
	public Object getChild(Object parent, int index) 
	{
		if (parent instanceof TreeElement.Package) 
		{
			return ((TreeElement.Package)parent).getElementAt(index);
		}
		else if (parent instanceof TableElement) 
		{
			return ((TableElement)parent).getElementAt(index);
		}
		return null;
	}
	
	@Override
	public int getChildCount(Object parent) 
	{
		if (parent instanceof TreeElement.Package) 
		{
			return ((TreeElement.Package)parent).getAllElements().size();
		}
		else if (parent instanceof TableElement) 
		{
			return ((TableElement)parent).getAllElements().size();
		}
		return 0;
	}
	
	@Override
	public int getIndexOfChild(Object parent, Object child) 
	{
		if (parent instanceof TreeElement.Package) 
		{
			return ((TreeElement.Package)parent).getIndexOfElement((TreeElement) child);
		}
		else if (parent instanceof TableElement) 
		{
			return ((TableElement)parent).getIndexOfElement((TreeElement) child);
		}
		return -1;
	}
	
	@Override
	public Object getRoot() 
	{
		return this.root;
	}
	
	@Override
	public boolean isLeaf(Object node) 
	{
		return (node instanceof ColumnElement);
	}	
}


