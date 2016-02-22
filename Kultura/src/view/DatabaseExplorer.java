package view;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

import view.renderers.TreeRenderer;
import model.XMLTreeModel;

/**
 * Klasa predstavlja pretrazivac baze podataka sa lijeve strane.
 * 
 * @author Grupa 1
 */
public class DatabaseExplorer extends JPanel
{	
	private static final long serialVersionUID = 1L;
		
	public JTree tree = null;
	public XMLTreeModel treeModel = null;	
		
	public DatabaseExplorer()
	{
		setLayout(new BorderLayout());			
		treeModel = new XMLTreeModel(null);		
		tree = new JTree(treeModel);
		tree.setRowHeight(0);
		tree.setCellRenderer(new TreeRenderer());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);	
		tree.setToggleClickCount(0); //da ne rasiruje na dvostruki klik

		JScrollPane scrollPane = new JScrollPane(tree, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		javax.swing.ToolTipManager.sharedInstance().registerComponent(tree);
		add(scrollPane, BorderLayout.CENTER);
	}	
	
	//metoda za updejtovanje stabla
	public void loadTree(String path)
	{
		treeModel = new XMLTreeModel(null, path);		
		tree.setModel(treeModel);		
	}
}
