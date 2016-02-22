package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.border.Border;

import model.Application;

/**
 * Klasa predstavlja SplashScreen koji ce se prikazivati dok se aplikacija ucita.
 * 
 * @author Grupa 1
 */
public class SplashScreen extends JWindow 
{	 
	private static final long serialVersionUID = 1L;

    private JProgressBar progressBar = new JProgressBar();

    public SplashScreen()
    {        	
    	int width = 750;
		int height = 400;
		int x;
		int y;
		JPanel splashScreen = (JPanel) getContentPane();
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();		
		 x = (screen.width - width) / 2;
		 y = (screen.height - height) / 2;		 
		setBounds(x, y, width, height);

		JLabel screenPicture = new JLabel(new ImageIcon("images/SplashScreen.png"));		
		splashScreen.add(screenPicture, BorderLayout.CENTER);			
                
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Loading...");
        progressBar.setBorder(border);
        splashScreen.add(progressBar, BorderLayout.SOUTH);
        setVisible(true);
        progressBar.setVisible(false);
        loadProgressBar();        
        setVisible(false);
    }

    //funkcija koja popunjava progressbar
    private void loadProgressBar()
    {
    	for(int i = 0; i<100; i++)
    	{
    		try {
				Thread.sleep(30);
			} catch (InterruptedException e) 
    		{
				e.printStackTrace();
			}
    		progressBar.setValue(i);
    		if(i==0)
    		{
    			Application.userName="";
    			LogInScreen login = new LogInScreen();    		
    			login.setModal(true);
    			login.setVisible(true);
    			login.setAlwaysOnTop(true);
    			if(Application.userName.isEmpty())
    				System.exit(0);
    			progressBar.setVisible(true);    			
    			this.toFront();    			
    		}
    		
    	} 
    }
}
