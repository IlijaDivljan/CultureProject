package view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import net.miginfocom.swing.MigLayout;
import model.Application;
import model.ColumnElement;
import model.TreeElement;
import view.observer.Observer;
import view.components.*;



/**
 * Klasa sluzi za prikaz polja za upis podataka.
 * 
 * @author Grupa 1
 */


public class InputView extends JScrollPane implements Observer
{
	
	private static final long serialVersionUID = 1L;

	private View view;
	public JPanel pnl;
	private Vector<BaseField> fields;
	private Vector<ColumnElement> columns = new Vector<ColumnElement>();
	private Vector<String> values = new Vector<String>();
	private JButton acceptButton, resetButton;
	private HashMap<Integer, JPanel> panelGroups;
	
	
	public InputView(View view) 
	{
		this.view = view;
		
		pnl = new JPanel();
		panelGroups = new HashMap<>();
		
		pnl.setLayout(new MigLayout("wrap 1"));
		
		setViewportView(pnl);
		
		fields = new Vector<>();
		
		acceptButton = new JButton("Accept", new ImageIcon("images/acceptIcon.png"));
		resetButton = new JButton("Discard", new ImageIcon("images/cancelIcon.png"));
		
		acceptButton.setActionCommand("accept");
		resetButton.setActionCommand("discard");
		
		
	}
	
	public void addActionListener(ActionListener al)
	{
		acceptButton.addActionListener(al);
		resetButton.addActionListener(al);
	}
	
	public Vector<BaseField> getFields() 
	{
		return fields;
	}


	public void setFields(Vector<BaseField> fields) 
	{
		this.fields = fields;
	}


	public Vector<ColumnElement> getColumns() 
	{
		return columns;
	}

	public void setColumns(Vector<TreeElement> columns) 
	{
		if(columns.size() > 0)
		{
			this.columns.clear();
		}
		for (TreeElement treeElement : columns) 
		{
			this.columns.add((ColumnElement)treeElement);
		}
	}
	
	public Vector<String> getClonedValues()
	{
		return this.values;
	}
	/**
	 * Metoda klonira vrijednosti BaseFields polja u vektor values. Ovo se radi da bi se mogli lakse
	 * pokupiti podaci za "Where" klauzulu kod procedura Update i Delete. Ti podaci se mogu pokupiti i direktno
	 * iz tabele preko selektovanog reda, medjutim lakse je odavde posto su ovdje u poljima vec formatirani preko getValue().
	 * (npr. Timestamp u formaty hh.mm.ss dd.MM.YYYY)
	 */
	public void cloneValues()
	{
		if(values.size() > 0)
		{
			this.values.clear();
		}
		for(BaseField field : fields)
		{
			this.values.add(field.getValue().toString());
		}
	}
	/**
	 * Metoda koja postavlja vrijednost svih polja u listi.
	 * @param view
	 */
	public void setValues(View view)
	{
		int selectedRow = view.getTablePanel().getTable().getSelectedRow();
		for(int i = 0; i < fields.size(); i++)
		{
			fields.get(i).setValue(view.getTablePanel().getModel().getValueAt(selectedRow, i));
		}
		cloneValues();
	}
	
	/**
	 * Metoda postavlja vrijednost Enabled svih polja u listi, kao i dugmadi accept i reset.
	 * @param enable
	 */
	public void enableFields(Boolean enable)
	{
		for (BaseField field : fields) 
		{
			field.enableEdit(enable);
		}
	}
	
	/**
	 * Metoda postavlja panel sa poljima za unos.
	 */
	public void setInputFieldsPanel(Vector<ColumnElement> columns)
	{
		if(columns.size() == 0)
		{
			return;
		}
		
		pnl.removeAll();
		fields.clear();
		panelGroups.clear();
		pnl.repaint();
		
		
		
		JPanel noGroup = null;
		
		for (ColumnElement column : columns)
		{
			if (!panelGroups.containsKey(column.getGroup()))
			{
				if (column.getGroup() != -1)
				{
					JPanel pn = new JPanel();
					pn.setLayout(new MigLayout(
							"wrap 4",
							"[grow][grow][grow][grow]",
							""));
					
					pn.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("0x003399")), "Grupa 0"));
					panelGroups.put(column.getGroup(), pn);
				}
				else
				{
					if (noGroup != null)
					{
						noGroup = new JPanel();
						noGroup.setLayout(new MigLayout(
								"wrap 4",
								"[grow][grow][grow][grow]",
								""));
					}
				}
			}
		}
		//Kreiranje Polja na osnovu tipa kolone.
		for (ColumnElement column : columns)
		{
			BaseField f;
			
			if (column.isForeign())
			{
				f = new LinkedField(column);
			}
			else
			{
				if(column.getType().contains("char")){
					f = new StringField(column);
				}
				else if(column.getType().contains("numeric") || column.getType().contains("int")){
					f = new IntegerField(column);
				}
				else if(column.getType().contains("float")){
					f = new DecimalField(column);
				}
				else if(column.getType().contains("datetime")){
					f = new DateTimeField(column);
				}
				else if(column.getType().contains("date")){
					f = new DateField(column);
				}
				else if(column.getType().contains("bit") || column.getType().contains("boolean")){
					f = new BooleanField(column);
				}
				else {
					f = new StringField(column);
				}
			}
			
			panelGroups.get(column.getGroup()).add(f, "width 150:200:300");
			fields.add(f);
			
			
		}
		
		for(Integer p : panelGroups.keySet())
		{
			pnl.add(panelGroups.get(p), "wrap");
		}
		
		if(noGroup != null)
		{
			pnl.add(noGroup, "wrap");
		}
		
		pnl.add(Box.createHorizontalStrut(10), "wrap");
		
		Container cnt = new Container();
		
		cnt.setLayout(new BoxLayout(cnt, BoxLayout.LINE_AXIS));
		
		acceptButton.setVisible(false);
		resetButton.setVisible(false);
		
		cnt.add(acceptButton);
		cnt.add(Box.createHorizontalStrut(60));
		cnt.add(resetButton);
		
		cnt.setPreferredSize(new Dimension(300, 60));
		
		pnl.add(cnt, "wrap");
		
		pnl.repaint();
		
		enableFields(false);
	}
	
	
	@Override
	public void updateStateChanged(Application model) 
	{
		String state = model.getCurrentState().toString();
		
		switch (state) 
		{
		case "Select":
			this.pnl.removeAll();
			break;
		case "Read":		
			setInputFieldsPanel(this.columns);
			if(view.getTablePanel().getTable().getSelectedRow() != -1)
			{
					setValues(view);
			}
			acceptButton.setVisible(false);
			resetButton.setVisible(false);
			break;
		case "Insert":
			setInputFieldsPanel(this.columns);
			acceptButton.setText("Insert");
			acceptButton.setVisible(true);
			resetButton.setVisible(true);
			enableFields(true);
			break;
		case "Update":
			acceptButton.setText("Update");
			if(view.getTablePanel().getTable().getSelectedRow() != -1 && fields.get(0).getValue().equals(""))
			{
					setValues(view);
			}
			acceptButton.setVisible(true);
			resetButton.setVisible(true);
			enableFields(true);
			break;
		case "Delete":
			acceptButton.setText("Delete");
			if(view.getTablePanel().getTable().getSelectedRow() != -1 && fields.get(0).getValue().equals(""))
			{
					setValues(view);
			}
			acceptButton.setVisible(true);
			resetButton.setVisible(true);
			enableFields(false);
			break;

		default:
			break;
		}
		
	}

	@Override
	public void updateSelectionChanged(View view) 
	{
		if(view.getTablePanel().getTable().getSelectedRow() != -1)
		{
				setValues(view);
		}
		
	}
	
	@Override
	public void updateTableChanged(View view) 
	{
		setColumns(view.getModel().getCurrentTable().getAllElements());
	}

	
}
