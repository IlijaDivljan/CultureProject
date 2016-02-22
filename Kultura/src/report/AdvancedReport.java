package report;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.File;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Application;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;
import view.View;
import ar.com.fdvs.dj.core.DynamicJasperHelper;
import ar.com.fdvs.dj.core.layout.ClassicLayoutManager;
import ar.com.fdvs.dj.domain.AutoText;
import ar.com.fdvs.dj.domain.DynamicReport;
import ar.com.fdvs.dj.domain.Style;
import ar.com.fdvs.dj.domain.builders.ColumnBuilderException;
import ar.com.fdvs.dj.domain.builders.FastReportBuilder;
import ar.com.fdvs.dj.domain.constants.Font;
import ar.com.fdvs.dj.domain.constants.HorizontalAlign;
import ar.com.fdvs.dj.domain.constants.VerticalAlign;

/**
 * Klasa sluzi za generisanje naprednog izvjestaja na osnovu povezivanja dvije ili vise tabela.
 * 
 * @author Grupa 1
 */
public class AdvancedReport
{
	@SuppressWarnings("unused")
	private View view;
	@SuppressWarnings("unused")
	private Application model;
	private ArrayList<JCheckBox> checkedColumns = new ArrayList<>();
	private boolean option = false;
			
	private JasperPrint jprint;	

	//konstruktor
	public AdvancedReport(View view, Application model)
	{
		this.view = view;
		this.model = model;			
	}	
	
	//pozivanje izvjestaja
	public void createReport()
	{		
		generateAdvancedReport();
		String[] buttons = { "Preview", "Export to PDF", "Cancel" };    
		int returnValue = JOptionPane.showOptionDialog(null, "Choose Option", "Generate Basic Report",
		        JOptionPane.PLAIN_MESSAGE, 0, null, buttons, buttons[0]);				
		if(returnValue == 0)
		{
			//generateAdvancedReport();
			if(option)
			preview();
		}
		else if(returnValue == 1)
		{
			//generateAdvancedReport();
			if(option)
			exportPDF();
		}				
	}
	
	//metoda za generisanje izvjestaja
	public void generateAdvancedReport()
	{
		try
		{
			FastReportBuilder rbuilder = new FastReportBuilder();

			Style titleSt = new Style("Stil");
			titleSt.setBackgroundColor(new Color(0, 150, 0));
			titleSt.setTransparent(false);
			titleSt.setFont(new Font(35, "Verdana", true));
			titleSt.setTextColor(Color.white);
			titleSt.setVerticalAlign(VerticalAlign.MIDDLE);

			Style colSt = new Style();
			colSt.setBackgroundColor(new Color(0, 76, 153));
			colSt.setTransparent(false);
			colSt.setFont(new Font(8, "SansSerif", true));
			colSt.setTextColor(Color.white);
			colSt.setVerticalAlign(VerticalAlign.MIDDLE);
			colSt.setHorizontalAlign(HorizontalAlign.CENTER);

			Style rowSt = new Style();
			rowSt.setBackgroundColor(new Color(234, 244, 255));
			rowSt.setTransparent(false);
			rowSt.setFont(new Font(8, "SansSerif", false));
			rowSt.setTextColor(Color.black);
			rowSt.setVerticalAlign(VerticalAlign.MIDDLE);
			rowSt.setHorizontalAlign(HorizontalAlign.CENTER);

			rbuilder.setDefaultStyles(titleSt, null, colSt, rowSt);
			
			rbuilder.addAutoText("ELEKTROTEHNICKI FAKULTET ISTOCNO SARAJEVO       Korisnik: " + Application.userName,
					AutoText.POSITION_HEADER,
					AutoText.ALIGNMENT_LEFT, 400);
			rbuilder.addAutoText(AutoText.AUTOTEXT_PAGE_X_SLASH_Y,
					AutoText.POSITION_FOOTER, AutoText.ALIGNMENT_CENTER, 30, 30);
			
			Application.connection.connect();
			String select = "SELECT MANIFESTACIJA.MA_NAZIV, MANIFESTACIJA.MA_POCETAK, MANIFESTACIJA.MA_ZAVRSETAK, MANIFESTACIJA.MA_BROJ_KARATA_U_PRODAJI, " +
					  "MANIFESTACIJA.MA_VAZECA, CLAN_KULTURNE_USTANOVE.CKU_IME, " + 
                      "CLAN_KULTURNE_USTANOVE.CKU_PREZIME, CLAN_KULTURNE_USTANOVE.CKU_DATUM_RODJENJA, CLAN_KULTURNE_USTANOVE.CKU_BROJ_TELEFONA, " + 
                      "CLAN_KULTURNE_USTANOVE.CKU_EMAIL_ADRESA FROM MANIFESTACIJA INNER JOIN " + 
                      "KUPLJENE_KARTE ON MANIFESTACIJA.MA_OZNAKA_MANIFESTACIJE = KUPLJENE_KARTE.MA_OZNAKA_MANIFESTACIJE INNER JOIN " + 
                      "CLAN_KULTURNE_USTANOVE ON KUPLJENE_KARTE.CKU_CLANSKI_BROJ = CLAN_KULTURNE_USTANOVE.CKU_CLANSKI_BROJ";			 
			
			Statement stmt  = Application.connection.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery(select);
			ResultSetMetaData rsmd = rs.getMetaData();	
			option = showOptions(rsmd);
			
			if(option)
			{
			for (int i = 0; i < rsmd.getColumnCount(); i++)
			{	
				if(checkedColumns.get(i).isSelected())
					rbuilder.addColumn(rsmd.getColumnName(i+1), rsmd.getColumnLabel(i+1), String.class.getName(), 5);
			}			
						
			@SuppressWarnings("deprecation")
			DynamicReport dr = rbuilder.setTitle("Napredni izvjestaj - Kupljene karte")
					.setTitleHeight(60).setTitleStyle(titleSt)
					.setSubtitle("Datum: " + new Date().toLocaleString())
					.setPrintBackgroundOnOddRows(true)
					.setUseFullPageWidth(true).build();
						
			ResultSet reportData = rs;
			reportData.beforeFirst();
			
			JRDataSource ds = new JRResultSetDataSource(reportData);
			this.jprint = DynamicJasperHelper.generateJasperPrint(dr,
					new ClassicLayoutManager(), ds);
			}

		}
		catch (JRException | ColumnBuilderException | ClassNotFoundException
				| SQLException e1)
		{
			JOptionPane.showMessageDialog(null, "Generisanje izvjestaja nije uspjelo.");
			e1.printStackTrace();
		}
	}
	
	public boolean showOptions(ResultSetMetaData rsmd)
	{
		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelCentral = new JPanel(new BorderLayout());
		panelCentral.setBackground(Color.white);
		JPanel panelLeft = new JPanel();
		JPanel panelRight = new JPanel();
		panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		
		try{		
		for (int i = 0; i < rsmd.getColumnCount(); i++)
		{	
			JPanel panel1 = new JPanel(new BorderLayout());
			JLabel label = new JLabel(rsmd.getColumnName(i+1));
			panel1.add(label);			
			panelLeft.add(panel1, BorderLayout.CENTER);
			
			JPanel panel2 = new JPanel(new BorderLayout());			
			checkedColumns.add(new JCheckBox());
			checkedColumns.get(i).setSelected(true);
			checkedColumns.get(i).setOpaque(false);
			panel2.add(checkedColumns.get(i), BorderLayout.CENTER);
			panelRight.add(panel2);	
		}
		
		JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel title = new JLabel("Odaberite kolone koje zelite da prikazete.");
		titlePanel.add(title);		
		
		panelLeft.setVisible(true);
		panelRight.setVisible(true);
		JPanel empty = new JPanel();
		empty.setMinimumSize(new Dimension(20, 20));

		panelCentral.add(panelLeft, BorderLayout.CENTER);
		panelCentral.add(panelRight, BorderLayout.EAST);
		panel.add(panelCentral, BorderLayout.CENTER);
		panel.add(titlePanel, BorderLayout.NORTH);		
		panel.add(empty, BorderLayout.EAST);		
		
		int option = JOptionPane.showConfirmDialog(null, panel, "Client Dialog", JOptionPane.OK_CANCEL_OPTION);
		
		if(option == JOptionPane.OK_OPTION)
		{
			return true;
		}
		}
		catch(SQLException e1){
			JOptionPane.showMessageDialog(null, "Generisanje izvjestaja nije uspjelo.");
			e1.printStackTrace();
		}
		return false;
	}

	//metoda za prikaz izvjetaja
	public void preview()
	{		
		JasperViewer.viewReport(jprint, false);
	}

	//metoda za eksportovanje u pdf
	public void exportPDF()
	{
		String location = System.getProperty("user.dir");
		JFileChooser chooser = new JFileChooser(location);
		int option = chooser.showDialog(null, "Save");
		File file = chooser.getSelectedFile();

		if (option == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				JasperExportManager.exportReportToPdfFile(jprint, file + ".pdf");
				JOptionPane.showMessageDialog(null, "Izvjestaj je uspjesno eksportovan.");
			}
			catch (JRException e)
			{
				e.printStackTrace();
			}
		}
	}
	
}
