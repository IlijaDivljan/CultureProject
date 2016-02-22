package report;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.Application;
import model.TableElement;
import model.TableModel;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Klasa sluzi za generisanje statickog izvjestaja za odredjene tabele.
 * 
 * @author Grupa 1
 */
public class StaticReport
{
	
	public StaticReport(String repLocation, String tablRetrieve)
	{
		InputStream inputStream;
		TableModel helpTable = new TableModel();
		try
		{	
			//kreiranje inputa i ubacivanje parametara
			inputStream = new FileInputStream(repLocation);
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("ime", Application.userName);

			JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
			JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
			
			//staticki definisanje tabele
			TableElement benTable = new TableElement();					
			benTable.setRetrieveSProc(tablRetrieve);
			helpTable.loadTable(benTable);
			JRDataSource dataSource = new JRResultSetDataSource(helpTable.rs);					
			helpTable.rs.beforeFirst();
			
			//generisanje izvejsaja
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);	
			
			//kreiranje prozora za izbor i prikaz/stampanje izvjestaja
			String[] buttons = { "Preview", "Export to PDF", "Cancel" };    
			int returnValue = JOptionPane.showOptionDialog(null, "Choose Option", "Generate Basic Report",
			        JOptionPane.PLAIN_MESSAGE, 0, null, buttons, buttons[0]);				
			if(returnValue == 0)
			{
				JasperViewer.viewReport(jasperPrint, false);
			}
			else if(returnValue == 1)
			{
				String location = System.getProperty("user.dir");
				JFileChooser chooser = new JFileChooser(location);
				int opt = chooser.showDialog(null, "Save");
				File file = chooser.getSelectedFile();

				if (opt == JFileChooser.APPROVE_OPTION)
				{
					try
					{
						JasperExportManager.exportReportToPdfFile(jasperPrint, file + ".pdf");
						JOptionPane.showMessageDialog(null, "Izvjestaj je uspjesno eksportovan.");
					}
					catch (JRException e3)
					{
						e3.printStackTrace();
					}
				}
			}			
		}
		catch (Exception e2) {
			// TODO: handle exception
			e2.printStackTrace();
		}	
	}

}
