package main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Controller;
import model.Application;
import view.SplashScreen;
import view.View;

public class Main 
{

	public static void main(String[] args)
	{
		
		try 
		{
	        UIManager.setLookAndFeel(
	            UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException| IllegalAccessException e) 
		{
	    
			e.printStackTrace();
		}
		
		
		@SuppressWarnings("unused")
		SplashScreen sp = new SplashScreen();
		Application model = Application.getInstance();
		View view = new View(model);
		@SuppressWarnings("unused")
		Controller controller = new Controller(model, view);

	}

}
