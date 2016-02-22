package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * About dijalog.
 * 
 * @author Grupa1
 *
 */
public class About 
{

	public static void initialize()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		Font font = new Font("Verdana", Font.BOLD, 20);
		Font font1 = new Font("Verdana", Font.BOLD, 12);
		
		JLabel naslov = new JLabel("Kultura+");
		naslov.setForeground(Color.RED);
		naslov.setFont(font);
		naslov.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		JLabel tekst2 = new JLabel("Projektovanje Informacionih Sistema");
		tekst2.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel tekst3 = new JLabel("Grupa G1:");
		tekst3.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel drugi = new JLabel("Miljan Skipina");
		drugi.setFont(font1);
		drugi.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel prvi = new JLabel("Ilija Divljan");
		prvi.setFont(font1);
		prvi.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel treci = new JLabel("Nenad Plemic");
		treci.setFont(font1);
		treci.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel kraj = new JLabel("Jun 2015.");
		kraj.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel empty1 = new JPanel();
		empty1.setPreferredSize(new Dimension(10, 10));
		JPanel empty2 = new JPanel();
		empty2.setPreferredSize(new Dimension(10, 10));
		JPanel empty3 = new JPanel();
		empty3.setPreferredSize(new Dimension(10, 10));
		JPanel empty4 = new JPanel();
		empty4.setPreferredSize(new Dimension(10, 10));
		
		panel.add(naslov);
		panel.add(empty1);
		panel.add(tekst2);
		panel.add(empty2);
		panel.add(tekst3);
		panel.add(empty3);
		panel.add(prvi);
		panel.add(drugi);
		panel.add(treci);
		panel.add(empty4);
		panel.add(kraj);
		JOptionPane.showMessageDialog(null, panel, "About", JOptionPane.PLAIN_MESSAGE, null);
	}
}
