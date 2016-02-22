package view;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Application;

import com.microsoft.sqlserver.jdbc.SQLServerResultSet;

import dbManipulation.DBManipulation;
import dbType.DatabaseType;

/**
 * Klasa predstavlja prozor za logovanje korisnika.
 * 
 * @author Grupa 1
 */
public class LogInScreen extends JDialog
{	
	private static final long serialVersionUID = 1L;
	
	private JButton ok;
	private JButton cancel;
	private JPanel panel;
	private JTextField username;
	private JTextField password;	
	private JLabel usernameLabel;	
	private JLabel passwordLabel;
	private JLabel info;

	public LogInScreen()
	{
	    //inicijalizacija komponenti	    
	    ok = new JButton("Login");
	    cancel = new JButton("Cancel");
	    panel = new JPanel();
	    username = new JTextField(10);
	    password = new JPasswordField(10);	    
	    usernameLabel = new JLabel("Username:  ");
	    passwordLabel = new JLabel("Password:  ");
	    info = new JLabel("Enter username and password.");
	
	    setSize(300,200);
	    setResizable(false);
	    setLocation(500,280);
	    panel.setLayout (new BoxLayout(panel, BoxLayout.Y_AXIS)); 	
	    
	    //kreiranje kontejnera
	    JPanel infoPanel = new JPanel();
	    infoPanel.setLayout(new FlowLayout());	    
	    JPanel userPanel = new JPanel();
	    userPanel.setLayout(new FlowLayout());	    
	    JPanel passPanel = new JPanel();
	    passPanel.setLayout(new FlowLayout());	    
	    JPanel butPanel = new JPanel();
	    butPanel.setLayout(new FlowLayout());	    
	    JPanel emptyPanel = new JPanel();
	    emptyPanel.setSize(300, 20);
	   
	    //dodavanje elemenata
	    infoPanel.add(info);
	    userPanel.add(usernameLabel);
	    userPanel.add(username);
	    passPanel.add(passwordLabel);	    
	    passPanel.add(password);
	    butPanel.add(ok);
	    butPanel.add(cancel);
	    panel.add(emptyPanel);
	    panel.add(infoPanel);
	    panel.add(userPanel);
	    panel.add(passPanel);
	    panel.add(butPanel);
	    
	    //pozivanje prozora	
	    getContentPane().add(panel);
	    //setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
	    	    
	    //postavljanje listenera
	    ok.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				checkLogin();				
			}
		});
	    
	    cancel.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);				
			}
		});
	}
	
	//funkcija za provjeru logovanja
	public void checkLogin()
	{
		@SuppressWarnings("unused")
		boolean log = false;		
		String user = username.getText();
		String pass = password.getText();
		String hashPass = "";
		
		//pocetak enkripcije sifre
		try
		{
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(pass.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1,digest);
			hashPass = bigInt.toString(16);			
		} 
		catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		//kraj enkripcije
		
		boolean logged = loginDB(user, hashPass);
		if(logged)
		{
			log = true;
			setVisible(false);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Pogresno korisnicko ime ili lozinka.");
			username.setText("");
			password.setText("");
		}		
	}
	
	//funkcija koja se konektuje na bazu i provjerava da li postoje korisnicko ime i lozinka
	public boolean loginDB(String userName, String passWord)
	{
		DBManipulation DBM = new DBManipulation(DatabaseType.MsSQL_JDBC, "78.28.157.8", "1433", "PIS2015", "EtfPIS2015G1", "EtfPIS2015G16728");
		Connection conn = DBM.getDbConnection();
		
		String select = "SELECT * FROM LOGIN";		 
		Statement stmt;
		try 
		{			
			stmt = conn.createStatement(SQLServerResultSet.TYPE_SCROLL_INSENSITIVE, SQLServerResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(select);
			@SuppressWarnings("unused")
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) 
			{
				if(rs.getString(1).equals(userName) && rs.getString(2).equals(passWord))
				{
					Application.userName = rs.getString(3) + " " + rs.getString(4); //da upise korisnika koji se logovao
					return true;
				}				
			}			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return false;
	}

}
