package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTree;

import view.View;
import view.state.Read;
import model.Application;
import model.TableElement;
import model.TableModel;
//import model.TreeElement;
//import model.TreeElement.Table;

/**
 * Klasa predstavlja kontroler za stablo u db exploreru.
 * 
 * @author Grupa 1
 */
public class DBExplorerController implements MouseListener
{
	private JTree tree;
	private Object node;
	private Application model;
	private View view;
	
	public DBExplorerController(Application model, View view) 
	{
		this.model = model;
		this.view = view;
		this.view.dbExplorer.tree.addMouseListener(this);
	}


	@Override
	public void mouseClicked(MouseEvent e)
	{
		if ((e.getSource() instanceof JTree))
		{			
			tree = (JTree) e.getSource();
			node = tree.getLastSelectedPathComponent();					
			
			if ((e.getClickCount() == 2) && (node instanceof TableElement))
			{
				model.tableModel = new TableModel();
				model.tableModel.loadTable((TableElement) node);
				model.setCurrentTable((TableElement) node);
				view.tablePanel.setModel(model.tableModel);
				model.notifyObserversTableChanged(view);
				
				
				
				model.setCurrentState(new Read());
				
			}
		
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
