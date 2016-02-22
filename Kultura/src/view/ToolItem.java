package view;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Klasa za definisanje toolItem-a pri cemu se parametri salju preko konstruktora.
 * 
 * @author Grupa 1
 */
public class ToolItem extends JButton
{	
	private static final long serialVersionUID = 1L;

	public ToolItem(ImageIcon icon, String text, String actionCommand)
	{
		this.setIcon(icon);
		this.setToolTipText(text);
		this.setActionCommand(actionCommand);
	}
	

}
