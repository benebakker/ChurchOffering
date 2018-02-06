
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.*;
//import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.ss.usermodel.CreationHelper;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.*;

public class FormController implements ActionListener {
	
	private DataEntryForm form;
	private static ArrayList<Donation> offering = new ArrayList<Donation>();

	public FormController(DataEntryForm f) {
		super();
		form = f;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("event occured - " + e.getActionCommand());
		if(e.getActionCommand().compareTo("add-name")==0) {
			Donation d = getFormData();
			System.out.println("adding "+d.getDonor() + " to the database ");
			form.getChurchDB().add(d.getDonor());
			updateChurchDB();
			form.getNameInDBLabel().setVisible(false);
			form.getAddNameToDBButton().setVisible(false);
			form.getAddNameToDBButton().setEnabled(false);	
		}
		if(e.getActionCommand().compareTo("enter-data")==0) {
			if(illegalEnvelope())
				return;
			Donation d = getFormData();
			if (illegalEntry(d))
				return;
			addDonationToOffering(d);
			form.getLastNameField().setActionCommand("lastname-event-off");
			resetForm();
			form.getLastNameField().setActionCommand("lastname-event-on");
		}
		if(e.getActionCommand().compareTo("show-data")==0) {
			System.out.println("in the show-data event block...");
			showAllEntries();
		}
		if(e.getActionCommand().compareTo("excel-report")==0) {
			exportToExcel();
		}
		if(e.getActionCommand().compareTo("lastname-event-on")==0) {
			form.getLastNameField().setActionCommand("lastname-event-off");
			resetNonLastNameFields();
			String s = (String)form.getLastNameField().getText();
			int c = countNameMatches(s);
			System.out.println("number of matches =" + c);
			if(c==1)
				fillInDataUsingLastName(s);
	//		else	 if (updateLastNameComboBox(s)==0) {
	//			form.getNameInDBLabel().setText("Name Not In DataBase");
	//			form.getNameInDBLabel().setVisible(true);
	//			form.getNameInDBLabel().setOpaque(true);
	//			form.getNameInDBLabel().setBackground(Color.red);
	//			form.getAddNameToDBButton().setVisible(true);
	//			form.getAddNameToDBButton().setEnabled(true);
	//		}
			form.getLastNameField().setActionCommand("lastname-event-on");
		}
		if(e.getActionCommand().compareTo("envelope-event")==0) {
			String en = (String)form.getEnvelopeField().getText();
			System.out.println(en);
			boolean envelopeExists=false;
			
			System.out.println(form.getChurchDB().size());
			
			for(Donor d1: form.getChurchDB()) {
				
				System.out.println(d1.getEnvelopeNumber());
				if(d1.getEnvelopeNumber()!=null) 
					if (d1.getEnvelopeNumber().compareTo(en)==0)
						envelopeExists=true;
			}
			if(envelopeExists) {
				fillInDataUsingEnvelopeNumber(en);
			}
			else {
				//default title and icon
				form.alertMessage("Envelope number does not exist.");
			}	
		}
		if(e.getActionCommand().compareTo("lastname-event")==0) {
			System.out.println("last name event has occurred...");

			/* - the Array list implementation which leads to a drop down selection
			ArrayList<String> namesList = new ArrayList<String>();
			
			for(Donor d: form.getChurchDB()) {
				String searchName = form.getLastNameField().getText();
				if(d.getLastName().length()>=searchName.length()) {
					if(d.getLastName().substring(0,searchName.length()).compareToIgnoreCase(searchName)==0) {
						namesList.add( d.getLastName()+", " + d.getFirstName() );
					}
				}
			}
			
	        String n = (String)JOptionPane.showInputDialog(null, "Select a person ",
	                "names", JOptionPane.QUESTION_MESSAGE, null, namesList.toArray(), namesList.toArray()[0]);
	        System.out.println(n);
			*/
			
			// the array implementation which leads to a scroll box
			String[] namesList = new String [form.getChurchDB().size()];
			
			int i=0;
			for( Donor d: form.getChurchDB()) {
				String searchName = form.getLastNameField().getText();
				if(d.getLastName().length()>=searchName.length()) {
					if(d.getLastName().substring(0,searchName.length()).compareToIgnoreCase(searchName)==0) {
						namesList[i] = d.getLastName()+", " + d.getFirstName()+"  ";
						i++;
					}
				}
			}
			
			System.out.println("i="+i);
			
			// name not in database
			if (i==0) {
				resetNonLastNameFields();
				form.getNameInDBLabel().setText("Name Not In DataBase");
				form.getNameInDBLabel().setVisible(true);
				form.getNameInDBLabel().setOpaque(true);
				form.getNameInDBLabel().setBackground(Color.red);
				form.getAddNameToDBButton().setVisible(true);
				form.getAddNameToDBButton().setEnabled(true);
			}  // only one name match
			else if(i==1) {
				System.out.println("in the i==1 section...");
				boolean match=false;
				
				for(Donor d: form.getChurchDB()) {
					if (d.getLastName().compareToIgnoreCase(form.getLastNameField().getText())==0) {
						System.out.println("one name match found, filling in name fields...");
						form.getLastNameField().setText(d.getLastName());
						form.getFirstNameField().setText(d.getFirstName());
						form.getEnvelopeField().setText(d.getEnvelopeNumber());
						form.getAddressField().setText(d.getAddress());
						form.getCityField().setText(d.getCity());
						form.getStateField().setSelectedItem(d.getState());
						form.getZipField().setText(d.getZip());
						match=true;
					}
				}
				if(!match) {
					String n = (String)JOptionPane.showInputDialog(null, "Select a person ",
			                "names", JOptionPane.QUESTION_MESSAGE, null, namesList, namesList[0]);
						System.out.println(n);

						if(n!=null) {
							for(Donor d: form.getChurchDB()) {
								if(d.getLastName().compareToIgnoreCase(n.substring(0, n.indexOf(',')))==0)
									if(d.getFirstName().compareToIgnoreCase(n.substring(n.indexOf(',')+2, n.length()-2))==0){
									form.getLastNameField().setText(d.getLastName());
									form.getFirstNameField().setText(d.getFirstName());
									form.getEnvelopeField().setText(d.getEnvelopeNumber());
									form.getAddressField().setText(d.getAddress());
									form.getCityField().setText(d.getCity());
									form.getStateField().setSelectedItem(d.getState());
									form.getZipField().setText(d.getZip());
									break;
								}
							}
						}
				}
			}
			 else{
			
				String n = (String)JOptionPane.showInputDialog(null, "Select a person ",
	                "names", JOptionPane.QUESTION_MESSAGE, null, namesList, namesList[0]);
				System.out.println(n);

				if(n!=null) {
					for(Donor d: form.getChurchDB()) {
						if(d.getLastName().compareToIgnoreCase(n.substring(0, n.indexOf(',')))==0)
							if(d.getFirstName().compareToIgnoreCase(n.substring(n.indexOf(',')+2, n.length()-2))==0){
							form.getLastNameField().setText(d.getLastName());
							form.getFirstNameField().setText(d.getFirstName());
							form.getEnvelopeField().setText(d.getEnvelopeNumber());
							form.getAddressField().setText(d.getAddress());
							form.getCityField().setText(d.getCity());
							form.getStateField().setSelectedItem(d.getState());
							form.getZipField().setText(d.getZip());
							break;
						}
					}
				}
			}
		}
			
				//int c = countNameMatches(s);
			//System.out.println("number of matches =" + c);
			//if(c==1)
			//	fillInDataUsingLastName(s);
			//else if (c>1) {
			/*
				ArrayList<String> matchingNames = new ArrayList<String>();
				for(Donor d: form.getChurchDB()) {
					if(d.getLastName().length()>=s.length()) {
						if( d.getLastName().substring(0, s.length()-1).compareToIgnoreCase(s)==0)  {
							matchingNames.add(d.getLastName() + ", " +d.getFirstName());
						}
					}
				JList list = new JList(matchingNames.toArray());
			*/	
				

	}
	
	public void addDonationToOffering(Donation d) {
		
		offering.add(d);
		System.out.println("the number of donations is " + offering.size());
		
	}
	
	private void resetForm() {
		//updateLastNameComboBox("");
		form.getLastNameField().setText("");
		form.getFirstNameField().setText("");
		form.getEnvelopeField().setText("");
		form.getAddressField().setText("");
		form.getCityField().setText("");
		form.getZipField().setText("");
		form.getStateField().setSelectedIndex(0);
		form.getCategoryField().setSelectedIndex(0);
		form.getDesignationField().setSelectedIndex(0);
		form.getDescriptionField().setText("");
		form.getAmountField().setText("");
		form.getNameInDBLabel().setVisible(false);
		form.getAddNameToDBButton().setVisible(false);
		form.getAddNameToDBButton().setEnabled(false);
	}
	
	private void resetNonLastNameFields() {
		form.getNameInDBLabel().setText("");
		form.getNameInDBLabel().setOpaque(false);
		form.getFirstNameField().setText("");
		form.getEnvelopeField().setText("");
		form.getAddressField().setText("");
		form.getCityField().setText("");
		form.getZipField().setText("");
		form.getStateField().setSelectedIndex(0);
		form.getAddNameToDBButton().setVisible(false);
		form.getAddNameToDBButton().setEnabled(false);
	}
	
	private void updateChurchDB() {

	    String churchDBfile = "churchDB1.csv";

	    try {
	    		Writer fileWriter = new FileWriter(churchDBfile, false); //overwrites file
	    		
   			fileWriter.write("Env #,Last Name,First Name,Address,City,State,Zip\n");
   			
   			Donor d;
	    		for(int i=1; i<form.getChurchDB().size();i++) {
	    			d=form.getChurchDB().get(i);
	    			fileWriter.write(d.getEnvelopeNumber()+","+
	    							d.getLastName()+","+
	    							d.getFirstName()+","+
	    							d.getAddress()+","+
	    							d.getCity()+","+
	    							d.getState()+","+
	    							d.getZip()+"\n");
	    		}
  		
		    // Always close files.
		    fileWriter.close(); 
	    }catch(Exception e) {
	    }
	}


	public String nullToEmptyString(String s) {
		if (s==null)
			return "";
		else 
			return s;
	}
	
	public Integer valueOf(String s) {
		if (s==null  || s.compareTo("")==0)
			return 0;
		else 
			return Integer.parseInt(s);
	}
	
	public Donation getFormData() {

		Donor d = new Donor(
				nullToEmptyString((String)form.getLastNameField().getText()),
				nullToEmptyString(form.getFirstNameField().getText()),
				nullToEmptyString(form.getEnvelopeField().getText()),
				nullToEmptyString(form.getAddressField().getText()),
				nullToEmptyString(form.getCityField().getText()),
				nullToEmptyString((String)form.getStateField().getSelectedItem()),
				nullToEmptyString(form.getZipField().getText())
				);
		
		Donation s = new Donation(
				d,
				nullToEmptyString((String)form.getCategoryField().getSelectedItem()),
				nullToEmptyString((String)form.getDesignationField().getSelectedItem()),
				nullToEmptyString((String)form.getDescriptionField().getText()),
				valueOf(form.getAmountField().getText())
				);
	
		return s;
	}
	
	private void fillInDataUsingLastName(String s) {
		for(Donor d: form.getChurchDB()) {
			if (d.getLastName().compareToIgnoreCase(s)==0) {
				form.setFirstNameField(d.getFirstName());
				form.setEnvelopeField(d.getEnvelopeNumber());
				form.setAddressField(d.getAddress());
				form.setCityField(d.getCity());
				form.setStateField(d.getState());
				form.setZipField(d.getZip());
				return;
			}
		}
			
	}

	private void fillInDataUsingEnvelopeNumber(String s) {
		for(Donor d: form.getChurchDB()) {
			if (d.getEnvelopeNumber().compareToIgnoreCase(s)==0) {
				form.setFirstNameField(d.getFirstName());
				form.getLastNameField().setText(d.getLastName());
				form.setAddressField(d.getAddress());
				form.setCityField(d.getCity());
				form.setStateField(d.getState());
				form.setZipField(d.getZip());
				return;
			}
		}
			
	}
	
	private int countNameMatches(String s) {
		int matches=0;
		for(Donor d: form.getChurchDB()) {
			if(d.getLastName().compareToIgnoreCase(s)==0) {
				matches++;
			}
		}
		return matches;
	}
	
	/*
	private int updateLastNameComboBox(String s) {
		System.out.println("in updateLastNameComboBox()....");
		int count = 0;
		form.getLastNameField().removeAllItems();
		form.getLastNameField().addItem(s);
		for(Donor d: form.getChurchDB()) {
			if(d.getLastName().length()>=s.length()) {
				System.out.print("d.getLastName().substring(0, s.length() = " + d.getLastName().substring(0, s.length()));
				System.out.println(" s = " + s);
				if (d.getLastName().substring(0, s.length()).compareToIgnoreCase(s)==0){
					System.out.println("adding : " + d.getLastName());
					form.getLastNameField().insertItemAt(d.getLastName(),count);
					count++;
				}
			}
		}
	return count;
}
*/
	
	private void showAllEntries(){
		
		System.out.println("in the show all entries block...number of entries in offering is " + offering.size());
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object columnNames[] = { "Last Name", "First Name", "Envelope Number", "Amount",
				"Designation","Category","Description","Address","City","State","Zip"};

		Object rowData[][] = new Object [offering.size()][columnNames.length];

		int i=0;
		for(Donation d: offering) {
			System.out.println("adding item "+i);
			rowData[i][0] = d.getDonor().getLastName();
			rowData[i][1] = d.getDonor().getFirstName();
			rowData[i][2] = d.getDonor().getEnvelopeNumber();
			rowData[i][3] = d.getAmount();
			rowData[i][4] = d.getDesignation();
			rowData[i][5] = d.getCategory();
			rowData[i][6] = d.getDescription();
			rowData[i][7] = d.getDonor().getAddress();
			rowData[i][8] = d.getDonor().getCity();
			rowData[i][9] = d.getDonor().getState();
			rowData[i][10] = d.getDonor().getZip();

			i++;
		}

		JTable table = new JTable(rowData, columnNames);

		//table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
	
		System.out.println("preferred width is " + table.getColumnModel().getColumn(1).getPreferredWidth());
		System.out.println("max width is " + table.getColumnModel().getColumn(1).getMaxWidth());
		System.out.println("min width is " + table.getColumnModel().getColumn(1).getMinWidth());
		
		//table.getColumnModel().getColumn(1).setMinWidth(10);
		//table.getColumnModel().getColumn(1).setMaxWidth(10);
		//table.getColumnModel().getColumn(1).setPreferredWidth(10);
		
		JScrollPane scrollPane = new JScrollPane(table);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setSize(1200, 400);
		
		frame.setVisible(true);
	}
	
	private boolean illegalEnvelope() {
		
		if(form.getEnvelopeField().getText().compareTo("")==0) {
			System.out.println("envelope field is empty...");
			return false;
		}
		else
			for(Donor d: form.getChurchDB()) {
				if(form.getEnvelopeField().getText().compareTo(d.getEnvelopeNumber())==0) {
					if(((String) form.getLastNameField().getText()).compareTo(d.getLastName())!=0){
						form.alertMessage("Your envelope number does not match the church database name for that number");
						return true;
					}
				}
			}
		return false;
	}
	
	private boolean illegalEntry(Donation d) {
		if(d.getDesignation().compareToIgnoreCase("envelope")==0)
			if(d.getDonor().getEnvelopeNumber().compareTo("")==0) {
				form.alertMessage("All entries with designation=envelope must have an envelope number");
				return true;
			}
		if(d.getCategory().compareTo("")==0) {
			form.alertMessage("All entries must have a category");
			return true;
		}
		if(d.getDesignation().compareTo("")==0) {	
			form.alertMessage("All entries must have a designation");
			return true;
		}
		return false;
	}
	
	private void exportToExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
	
	    Sheet envSheet = wb.createSheet("Envelope");
	    Sheet plateSheet = wb.createSheet("Plate");
	    Sheet dfSheet = wb.createSheet("Designated Funds");
	    Sheet miscSheet = wb.createSheet("Misc");
	    Sheet dataSheet = wb.createSheet("Data");
	    
	    CreationHelper createHelper = wb.getCreationHelper();
	    
	    createEnvSheet(wb, createHelper, envSheet);
	    createPlateSheet(wb, createHelper, plateSheet);
	    createDfSheet(wb, createHelper, dfSheet);
	    createMiscSheet(wb, createHelper, miscSheet);
	    createDataSheet(wb, createHelper, dataSheet);
	    
	    try {
    			FileOutputStream fileOut = new FileOutputStream("workbook.xls");
    			wb.write(fileOut);
    			fileOut.close();
	    }catch(Exception e) {
	    	
	    }
	    
	    // Note that sheet name is Excel must not exceed 31 characters
	    // and must not contain any of the any of the following characters:
	    // 0x0000
	    // 0x0003
	    // colon (:)
	    // backslash (\)
	    // asterisk (*)
	    // question mark (?)
	    // forward slash (/)
	    // opening square bracket ([)
	    // closing square bracket (])

	    // You can use org.apache.poi.ss.util.WorkbookUtil#createSafeSheetName(String nameProposal)}
	    // for a safe way to create valid names, this utility replaces invalid characters with a space (' ')
	    // returns " O'Brien's sales   "

	    // Create a row and put some cells in it. Rows are 0 based.
	    //Row row = sheet1.createRow((short)0);
	    // Create a cell and put a value in it.
	    //Cell cell = row.createCell(0);
	    //cell.setCellValue(1);
	}
	
    private void createEnvSheet(HSSFWorkbook wb, CreationHelper createHelper, Sheet envSheet) {
    	
		System.out.println("in the create envelope sheet method....");
    	
		// setup column widths
	    envSheet.setColumnWidth(0, 3200);
		
	    // setup worksheet font
	    HSSFFont font= wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    // setup background colors for sheet
	    HSSFPalette palette = wb.getCustomPalette();
	    HSSFColor myColor = palette.findSimilarColor(255, 202, 146);
	    short palIndex = myColor.getIndex();

	    // setup the cell style for titles on the page
	    CellStyle styleTopBar = wb.createCellStyle();
	    styleTopBar.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTopBar.setFillForegroundColor(palIndex);
	    styleTopBar.setAlignment(HorizontalAlignment.CENTER);
	    styleTopBar.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleTopBar.setBorderBottom(BorderStyle.THIN);
	    styleTopBar.setFont(font);
	    
	    // setup the cell style for currency entries
	    CellStyle styleData = wb.createCellStyle();
	    styleData.setAlignment(HorizontalAlignment.RIGHT);
	    styleData.setDataFormat(wb.createDataFormat().getFormat( BuiltinFormats.getBuiltinFormat( 6 )));
	    
	    //setup Styles for totals
	    CellStyle styleRightBorder = wb.createCellStyle();
	    styleRightBorder.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle styleLeftBorder = wb.createCellStyle();
	    styleLeftBorder.setBorderLeft(BorderStyle.THIN);
	    
	    CellStyle styleBottomLeftCorner = wb.createCellStyle();
	    styleBottomLeftCorner.setBorderBottom(BorderStyle.THIN);
	    styleBottomLeftCorner.setBorderLeft(BorderStyle.THIN);
	    
	    CellStyle styleBottomRightCorner = wb.createCellStyle();
	    styleBottomRightCorner.setBorderBottom(BorderStyle.THIN);
	    styleBottomRightCorner.setBorderRight(BorderStyle.THIN);
	    
	    CellStyle styleTopLeftCorner = wb.createCellStyle();
	    styleTopLeftCorner.setBorderTop(BorderStyle.THIN);
	    styleTopLeftCorner.setBorderLeft(BorderStyle.THIN);
	    
	    CellStyle styleTopRightCorner = wb.createCellStyle();
	    styleTopRightCorner.setBorderTop(BorderStyle.THIN);
	    styleTopRightCorner.setBorderRight(BorderStyle.THIN);
	    
	    
	    // create the titles
	    int r=0;
	    Row row = envSheet.createRow((short)r);
	    row.setHeightInPoints(20);
	    
	    Cell cell = row.createCell(0);
	    cell.setCellStyle(styleTopBar);
	    cell.setCellValue(createHelper.createRichTextString("Envelope #"));

	    cell = row.createCell(1);
	    cell.setCellStyle(styleTopBar);
	    cell.setCellValue(createHelper.createRichTextString("Check"));
	    
	    cell = row.createCell(2);
	    cell.setCellStyle(styleTopBar);
	    cell.setCellValue(createHelper.createRichTextString("Cash"));
	    
	    cell = row.createCell(3);
	    cell.setCellStyle(styleTopBar);
	    cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    
	    for(Donation d: offering) {
	    		if(d.getDesignation().compareToIgnoreCase("Envelope")==0){
	    			
	    			r++;
	    		    row = envSheet.createRow((short)r);
	    		    row.setHeightInPoints(18);
	    		    
	    		    row.createCell(0).setCellValue(createHelper.createRichTextString(d.getDonor().getEnvelopeNumber()));
	    		    if(d.getCategory().compareToIgnoreCase("check")==0) {
	    		    		cell = row.createCell(1);
	    		    		cell.setCellStyle(styleData);
	    		    		cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("cash")==0) {
    		    			cell = row.createCell(2);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("eft")==0) {
    		    			cell = row.createCell(3);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		}		
	    }
	    
	    if(envSheet.getRow((short)4)==null) {
			row = envSheet.createRow((short)4);
			row.setHeightInPoints(18);
			cell = row.createCell(5);
			cell.setCellStyle(styleTopLeftCorner);
			cell.setCellValue(createHelper.createRichTextString("TOTALS"));
			
			cell = envSheet.getRow((short)4).createCell(6);
			cell.setCellStyle(styleTopRightCorner);
			cell.setCellValue(createHelper.createRichTextString(" "));
	    }
	    else {
			cell = envSheet.getRow((short)4).createCell(5);
			cell.setCellStyle(styleTopLeftCorner);
			cell.setCellValue(createHelper.createRichTextString("TOTALS"));
			
			cell = envSheet.getRow((short)4).createCell(6);
			cell.setCellStyle(styleTopRightCorner);
			cell.setCellValue(createHelper.createRichTextString(" "));
	    }
	    
	    if(envSheet.getRow((short)5)==null) {
    			row = envSheet.createRow((short)5);
	    		row.setHeightInPoints(18);
	    		row.createCell(5).setCellValue(createHelper.createRichTextString("CHECK"));
	    }	
	    else
    			envSheet.getRow((short)5).createCell(5).setCellValue(createHelper.createRichTextString("CHECK"));
    
	    if(envSheet.getRow((short)6)==null) {
			row=envSheet.createRow((short)6);
			row.setHeightInPoints(18);
			//styleTitles.setBorderBottom(BorderStyle.NONE);
			//styleTitles.setBorderLeft(BorderStyle.THIN);
			row.createCell(5).setCellValue(createHelper.createRichTextString("CASH"));
	    }
	    else
			envSheet.getRow((short)6).createCell(5).setCellValue(createHelper.createRichTextString("CASH"));
    
	    if(envSheet.getRow((short)7)==null) {
	    		row=envSheet.createRow((short)7);
	    		row.setHeightInPoints(18);
	    		row.createCell(5).setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    else
	    		envSheet.getRow((short)7).createCell(5).setCellValue(createHelper.createRichTextString("EFT PP"));

	    if(envSheet.getRow((short)8)==null) {
    			row = envSheet.createRow((short)8);
    			row.setHeightInPoints(20);
    			row.createCell(5).setCellValue(createHelper.createRichTextString("ALL"));
	    }
	    else
    			envSheet.getRow((short)8).createCell(5).setCellValue(createHelper.createRichTextString("ALL"));
	    	    
	    row = envSheet.getRow(5);
		cell = row.createCell(6);
		cell.setCellStyle(styleData);
	    cell.setCellFormula("SUM(B:B)");
	    
	    row = envSheet.getRow(6);
		cell = row.createCell(6);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(C:C)");
		
		row = envSheet.getRow(7);
		cell = row.createCell(6);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(D:D)");
		
		row = envSheet.getRow(8);
		cell = row.createCell(6);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(G6:G8)");    	
    }
 
    private void createPlateSheet(HSSFWorkbook wb, CreationHelper createHelper, Sheet plateSheet) {
    	
		System.out.println("in the create plate sheet method....");
    	
		// set the column widths for the sheet
		plateSheet.setColumnWidth(0, 5200);
		plateSheet.setColumnWidth(1, 5200);
		
		// set up font for the sheet
	    HSSFFont font= wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    // set up the color for the top row
	    HSSFPalette palette = wb.getCustomPalette();
	    HSSFColor myColor = palette.findSimilarColor(255, 202, 146);
	    short palIndex = myColor.getIndex();

	    // setup the style / format of the cell
	    CellStyle styleTitles = wb.createCellStyle();
	    styleTitles.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTitles.setFillForegroundColor(palIndex);
	    styleTitles.setAlignment(HorizontalAlignment.CENTER);
	    styleTitles.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleTitles.setBorderBottom(BorderStyle.THIN);
	    styleTitles.setFont(font);
	    
	    // setup the cell style for currency entries
	    CellStyle styleData = wb.createCellStyle();
	    styleData.setAlignment(HorizontalAlignment.RIGHT);
	    styleData.setDataFormat(wb.createDataFormat().getFormat( BuiltinFormats.getBuiltinFormat( 6 )));
	    
	    // create the titles
	    int r=0;
	    Row row = plateSheet.createRow((short)r);
	    row.setHeightInPoints(20);
	    
	    Cell cell = row.createCell(0);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("plate - First Name"));
	    
	    cell = row.createCell(1);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("plate - Last Name"));

	    cell = row.createCell(2);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Checks"));

	    cell = row.createCell(3);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Cash"));

	    cell = row.createCell(4);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("EFT PP"));

	    cell = row.createCell(5);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Address"));

	    cell = row.createCell(6);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("City"));

	    cell = row.createCell(7);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("State"));

	    cell = row.createCell(8);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Zip"));
	
		System.out.println("in the create plate sheet method 2....");
	    
	    for(Donation d: offering) {
	    		if(d.getDesignation().compareToIgnoreCase("Plate")==0) {
	    			
	    			r++;
	    		    row = plateSheet.createRow((short)r);
	    		    row.setHeightInPoints(18);

	    		    row.createCell(0).setCellValue(createHelper.createRichTextString(d.getDonor().getFirstName()));
	    		    row.createCell(1).setCellValue(createHelper.createRichTextString(d.getDonor().getLastName()));
	    		    if(d.getCategory().compareToIgnoreCase("check")==0) {
    		    			cell = row.createCell(2);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("cash")==0) {
    		    			cell = row.createCell(3);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("eft")==0) {
    		    			cell = row.createCell(4);
	    		    		cell.setCellStyle(styleData);
	    		    		cell.setCellValue(d.getAmount());
	    		    }
	    		    
	    		    row.createCell(5).setCellValue(createHelper.createRichTextString(d.getDonor().getAddress()));
	    		    row.createCell(6).setCellValue(createHelper.createRichTextString(d.getDonor().getCity()));
	    		    row.createCell(7).setCellValue(createHelper.createRichTextString(d.getDonor().getState()));
	    		    row.createCell(8).setCellValue(createHelper.createRichTextString(d.getDonor().getZip()));
	    		}		
	    }
	    
		System.out.println("in the create plate sheet method 3....");
	    
	    if(plateSheet.getRow((short)5)==null) {
    			row = plateSheet.createRow((short)5);
    			row.setHeightInPoints(18);
    			cell = row.createCell(10);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    else	{
	    		row = plateSheet.getRow((short)5);
	    		cell = row.createCell(10);
	    		cell.setCellStyle(styleTitles);
	    		cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    if(plateSheet.getRow((short)6)==null) {
			row = plateSheet.createRow((short)6);
			row.setHeightInPoints(18);
			cell = row.createCell(10);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    else	{
	    		row = plateSheet.getRow((short)6);
    			cell = row.createCell(10);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    if(plateSheet.getRow((short)7)==null) {
			row = plateSheet.createRow((short)7);
			row.setHeightInPoints(18);
			cell = row.createCell(10);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    else	{
	    		row = plateSheet.getRow((short)7);
    			cell = row.createCell(10);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    if(plateSheet.getRow((short)8)==null) {
	 		row = plateSheet.createRow((short)8);
	 		row.setHeightInPoints(18);
	 		cell = row.createCell(10);
	 		cell.setCellStyle(styleTitles);
	 		cell.setCellValue(createHelper.createRichTextString("Total"));
	 	}
	    else	{
	 	    	row = plateSheet.getRow((short)8);
	     	cell = row.createCell(10);
	     	cell.setCellStyle(styleTitles);
	     	cell.setCellValue(createHelper.createRichTextString("Total"));
	    }
	    
		System.out.println("in the create plate sheet method 4....");
	    
	    // put in formula to compute the sum of all the CHECKS
	    row = plateSheet.getRow(5);
		cell = row.createCell(11);
		cell.setCellStyle(styleData);
	    cell.setCellFormula("SUM(C:C)");
	    
	    // put in formula to compute the sum of all the CASH
	    row = plateSheet.getRow(6);
		cell = row.createCell(11);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(D:D)");
		
	    // put in formula to compute the sum of all the EFTS
		row = plateSheet.getRow(7);
		cell = row.createCell(11);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(E:E)");
		
		// put in formula to compute the sum of all categories
		row = plateSheet.getRow(8);
		cell = row.createCell(11);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(L6:L8)");  
    }
  
    private void createDfSheet(HSSFWorkbook wb, CreationHelper createHelper, Sheet dfSheet) {
    	
    		System.out.println("in the create designated fund sheet method....");

    		// setup column widths
    	    dfSheet.setColumnWidth(0, 3200);
    		
    	    // setup worksheet font
    	    HSSFFont font= wb.createFont();
    	    font.setFontHeightInPoints((short)10);
    	    font.setFontName("Arial");
    	    font.setColor(IndexedColors.BLACK.getIndex());
    	    font.setBold(true);
    	    font.setItalic(false);

    	    // setup background colors for sheet
    	    HSSFPalette palette = wb.getCustomPalette();
    	    HSSFColor myColor = palette.findSimilarColor(255, 202, 146);
    	    short palIndex = myColor.getIndex();

    	    // setup the cell style for titles on the page
    	    CellStyle styleTitles = wb.createCellStyle();
    	    styleTitles.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    		styleTitles.setFillForegroundColor(palIndex);
    	    styleTitles.setAlignment(HorizontalAlignment.CENTER);
    	    styleTitles.setVerticalAlignment(VerticalAlignment.CENTER);
    	    styleTitles.setBorderBottom(BorderStyle.THIN);
    	    styleTitles.setFont(font);
    	    
    	    // setup the cell style for currency entries
    	    CellStyle styleData = wb.createCellStyle();
    	    styleData.setAlignment(HorizontalAlignment.RIGHT);
    	    styleData.setDataFormat(wb.createDataFormat().getFormat( BuiltinFormats.getBuiltinFormat( 6 )));
    	    
    	    // create the column titles
    	    int r=0;
    	    Row row = dfSheet.createRow((short)r);
    	    row.setHeightInPoints(20);
    	    
    	    Cell cell = row.createCell(0);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Envelope"));

    	    cell = row.createCell(1);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("First Name"));
    	    
    	    cell = row.createCell(2);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Last Name"));
    	    
    	    cell = row.createCell(3);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Checks"));
    	    
    	    cell = row.createCell(4);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Cash"));

    	    cell = row.createCell(5);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("EFT PP"));
    	    
    	    cell = row.createCell(6);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Fund"));
    	    
    	    cell = row.createCell(7);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Address"));
    	    
      	cell = row.createCell(8);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("City"));

    	    cell = row.createCell(9);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("State"));
    	    
    	    cell = row.createCell(10);
    	    cell.setCellStyle(styleTitles);
    	    cell.setCellValue(createHelper.createRichTextString("Zip"));
	    
	    for(Donation d: offering) {
	    		System.out.println("the category is " + d.getCategory());
	    		if(d.getDesignation().compareToIgnoreCase("Designated")==0) {
	    			
	    			r++;
	    		    row = dfSheet.createRow((short)r);
	    		    row.setHeightInPoints(18);

	    		    row.createCell(0).setCellValue(createHelper.createRichTextString(d.getDonor().getEnvelopeNumber()));
	    		    row.createCell(1).setCellValue(createHelper.createRichTextString(d.getDonor().getFirstName()));
	    		    row.createCell(2).setCellValue(createHelper.createRichTextString(d.getDonor().getLastName()));
	    		    if(d.getCategory().compareToIgnoreCase("check")==0) {
    		    			cell = row.createCell(3);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("cash")==0) {
    		    			cell = row.createCell(4);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("eft")==0) {
    		    			cell = row.createCell(5);
    		    			cell.setCellStyle(styleData);
    		    			cell.setCellValue(d.getAmount());
	    		    }
	    		    row.createCell(6).setCellValue(createHelper.createRichTextString(d.getDescription()));
	    		    row.createCell(7).setCellValue(createHelper.createRichTextString(d.getDonor().getAddress()));
	    		    row.createCell(8).setCellValue(createHelper.createRichTextString(d.getDonor().getCity()));
	    		    row.createCell(9).setCellValue(createHelper.createRichTextString(d.getDonor().getState()));
	    		    row.createCell(10).setCellValue(createHelper.createRichTextString(d.getDonor().getZip()));
	    		}		
	    }
	    
	    int col=12;
	    
	    if(dfSheet.getRow((short)5)==null) {
	    		row = dfSheet.createRow((short)5);
	    		row.setHeightInPoints(18);
	    		cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    else {
    			row = dfSheet.getRow((short)5);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    if(dfSheet.getRow((short)6)==null) {
    			row = dfSheet.createRow((short)6);
    			row.setHeightInPoints(18);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    else {
    			row = dfSheet.getRow((short)6);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    if(dfSheet.getRow((short)7)==null) {
			row = dfSheet.createRow((short)7);
			row.setHeightInPoints(18);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    else {
			row = dfSheet.getRow((short)7);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    if(dfSheet.getRow((short)8)==null) {
			row = dfSheet.createRow((short)8);
			row.setHeightInPoints(18);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("Total"));
	    }
	    else {
			row = dfSheet.getRow((short)8);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("Total"));
	    }

	    row = dfSheet.getRow(5);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
	    cell.setCellFormula("SUM(D:D)");
	    
	    row = dfSheet.getRow(6);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(E:E)");
		
		row = dfSheet.getRow(7);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(F:F)");
		
		row = dfSheet.getRow(8);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(F6:F8)");    	
	    
    }
    
    private void createMiscSheet(HSSFWorkbook wb, CreationHelper createHelper, Sheet miscSheet) {
    	
    		System.out.println("in the create misc. fund sheet method....");

		// setup column widths
	    miscSheet.setColumnWidth(0, 3200);
		
	    // setup worksheet font
	    HSSFFont font= wb.createFont();
	    font.setFontHeightInPoints((short)10);
	    font.setFontName("Arial");
	    font.setColor(IndexedColors.BLACK.getIndex());
	    font.setBold(true);
	    font.setItalic(false);
	    
	    // setup background colors for sheet
	    HSSFPalette palette = wb.getCustomPalette();
	    HSSFColor myColor = palette.findSimilarColor(255, 202, 146);
	    short palIndex = myColor.getIndex();

	    // setup the cell style for titles on the page
	    CellStyle styleTitles = wb.createCellStyle();
	    styleTitles.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		styleTitles.setFillForegroundColor(palIndex);
	    styleTitles.setAlignment(HorizontalAlignment.CENTER);
	    styleTitles.setVerticalAlignment(VerticalAlignment.CENTER);
	    styleTitles.setBorderBottom(BorderStyle.THIN);
	    styleTitles.setFont(font);
	    
	    // setup the cell style for currency entries
	    CellStyle styleData = wb.createCellStyle();
	    styleData.setAlignment(HorizontalAlignment.RIGHT);
	    styleData.setDataFormat(wb.createDataFormat().getFormat( BuiltinFormats.getBuiltinFormat( 6 )));
	    
	 // create the column titles
	    int r=0;
	    Row row = miscSheet.createRow((short)r);
	    row.setHeightInPoints(20);
	    
	    Cell cell = row.createCell(0);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Envelope"));

	    cell = row.createCell(1);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("First Name"));
	    
	    cell = row.createCell(2);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Last Name"));
	    
	    cell = row.createCell(3);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Checks"));
	    
	    cell = row.createCell(4);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Cash"));

	    cell = row.createCell(5);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    
	    cell = row.createCell(6);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Fund"));
	    
	    cell = row.createCell(7);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Address"));
	    
  	cell = row.createCell(8);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("City"));

	    cell = row.createCell(9);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("State"));
	    
	    cell = row.createCell(10);
	    cell.setCellStyle(styleTitles);
	    cell.setCellValue(createHelper.createRichTextString("Zip"));
    	
	    int check=0; 
	    int cash=0;
	    int EFT=0;
    	
	    /*
	    int r=0;
	    Row row = miscSheet.createRow((short)r);
	    row.createCell(0).setCellValue(createHelper.createRichTextString("Envelope"));
	    row.createCell(1).setCellValue(createHelper.createRichTextString("First Name"));
	    row.createCell(2).setCellValue(createHelper.createRichTextString("Last Name"));
	    row.createCell(3).setCellValue(createHelper.createRichTextString("Checks"));
	    row.createCell(4).setCellValue(createHelper.createRichTextString("Cash"));
	    row.createCell(5).setCellValue(createHelper.createRichTextString("EFT PP"));
	    row.createCell(6).setCellValue(createHelper.createRichTextString("Fund"));
	    row.createCell(7).setCellValue(createHelper.createRichTextString("Address"));
	    row.createCell(8).setCellValue(createHelper.createRichTextString("City"));
	    row.createCell(9).setCellValue(createHelper.createRichTextString("State"));
	    row.createCell(10).setCellValue(createHelper.createRichTextString("Zip"));
	    */
	    
	    for(Donation d: offering) {
	    		if(d.getDesignation().compareToIgnoreCase("misc.")==0) {
	    			r++;
	    		    row = miscSheet.createRow((short)r);
	    		    row.createCell(0).setCellValue(createHelper.createRichTextString(d.getDonor().getEnvelopeNumber()));
	    		    row.createCell(1).setCellValue(createHelper.createRichTextString(d.getDonor().getFirstName()));
	    		    row.createCell(2).setCellValue(createHelper.createRichTextString(d.getDonor().getLastName()));
	    		    if(d.getCategory().compareToIgnoreCase("check")==0) {
	    		    		row.createCell(3).setCellValue(d.getAmount());
	    		    		check+=d.getAmount();
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("cash")==0) {
    		    			row.createCell(4).setCellValue(d.getAmount());
    		    			cash+=d.getAmount();
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("eft")==0) {
    		    			row.createCell(5).setCellValue(d.getAmount());
    		    			EFT+=d.getAmount();
	    		    }
	    		    row.createCell(6).setCellValue(createHelper.createRichTextString(d.getDescription()));
	    		    row.createCell(7).setCellValue(createHelper.createRichTextString(d.getDonor().getAddress()));
	    		    row.createCell(8).setCellValue(createHelper.createRichTextString(d.getDonor().getCity()));
	    		    row.createCell(9).setCellValue(createHelper.createRichTextString(d.getDonor().getState()));
	    		    row.createCell(10).setCellValue(createHelper.createRichTextString(d.getDonor().getZip()));
	    		}		
	    }
	    
	    int col=12;
	    
	    /*
	    if(miscSheet.getRow((short)5)==null)
			miscSheet.createRow((short)5).createCell(col).setCellValue(createHelper.createRichTextString("check"));
	    else
			miscSheet.getRow((short)5).createCell(col).setCellValue(createHelper.createRichTextString("check"));

	    if(miscSheet.getRow((short)6)==null)
	    		miscSheet.createRow((short)6).createCell(col).setCellValue(createHelper.createRichTextString("cash"));
	    else
	    		miscSheet.getRow((short)6).createCell(col).setCellValue(createHelper.createRichTextString("cash"));

	    if(miscSheet.getRow((short)7)==null)
    			miscSheet.createRow((short)7).createCell(col).setCellValue(createHelper.createRichTextString("EFT PP"));
	    else
    			miscSheet.getRow((short)7).createCell(col).setCellValue(createHelper.createRichTextString("EFT PP"));
*/
	    //miscSheet.getRow(5).createCell(col+1).setCellValue(check);
	    //miscSheet.getRow(6).createCell(col+1).setCellValue(cash);
	    //miscSheet.getRow(7).createCell(col+1).setCellValue(EFT);
	    
	    
	    if(miscSheet.getRow((short)5)==null) {
	    		row = miscSheet.createRow((short)5);
	    		row.setHeightInPoints(18);
	    		cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    else {
    			row = miscSheet.getRow((short)5);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("check"));
	    }
	    if(miscSheet.getRow((short)6)==null) {
    			row = miscSheet.createRow((short)6);
    			row.setHeightInPoints(18);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    else {
    			row = miscSheet.getRow((short)6);
    			cell = row.createCell(col);
    			cell.setCellStyle(styleTitles);
    			cell.setCellValue(createHelper.createRichTextString("cash"));
	    }
	    if(miscSheet.getRow((short)7)==null) {
			row = miscSheet.createRow((short)7);
			row.setHeightInPoints(18);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    else {
			row = miscSheet.getRow((short)7);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("EFT PP"));
	    }
	    if(miscSheet.getRow((short)8)==null) {
			row = miscSheet.createRow((short)8);
			row.setHeightInPoints(18);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("Total"));
	    }
	    else {
			row = miscSheet.getRow((short)8);
			cell = row.createCell(col);
			cell.setCellStyle(styleTitles);
			cell.setCellValue(createHelper.createRichTextString("Total"));
	    }

	    row = miscSheet.getRow(5);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
	    cell.setCellFormula("SUM(D:D)");
	    
	    row = miscSheet.getRow(6);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(E:E)");
		
		row = miscSheet.getRow(7);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(F:F)");
		
		row = miscSheet.getRow(8);
		cell = row.createCell(col+1);
		cell.setCellStyle(styleData);
		cell.setCellFormula("SUM(N6:N8)");   
	    
    }
  
    private void createDataSheet(HSSFWorkbook wb, CreationHelper createHelper, Sheet dataSheet) {
    	
	    int check=0; 
	    int cash=0;
	    int EFT=0;

	    int r=0;
	    Row row = dataSheet.createRow((short)r);
	    row.createCell(0).setCellValue(createHelper.createRichTextString("Envelope"));
	    row.createCell(1).setCellValue(createHelper.createRichTextString("First Name"));
	    row.createCell(2).setCellValue(createHelper.createRichTextString("Last Name"));
	    row.createCell(3).setCellValue(createHelper.createRichTextString("Checks"));
	    row.createCell(4).setCellValue(createHelper.createRichTextString("Cash"));
	    row.createCell(5).setCellValue(createHelper.createRichTextString("EFT PP"));
	    row.createCell(6).setCellValue(createHelper.createRichTextString("Category"));
	    row.createCell(7).setCellValue(createHelper.createRichTextString("Description"));
	    row.createCell(8).setCellValue(createHelper.createRichTextString("Address"));
	    row.createCell(9).setCellValue(createHelper.createRichTextString("City"));
	    row.createCell(10).setCellValue(createHelper.createRichTextString("State"));
	    row.createCell(11).setCellValue(createHelper.createRichTextString("Zip"));
	    
	    for(Donation d: offering) {
	    			r++;
	    		    row = dataSheet.createRow((short)r);
	    		    row.createCell(0).setCellValue(createHelper.createRichTextString(d.getDonor().getEnvelopeNumber()));
	    		    row.createCell(1).setCellValue(createHelper.createRichTextString(d.getDonor().getFirstName()));
	    		    row.createCell(2).setCellValue(createHelper.createRichTextString(d.getDonor().getLastName()));
	    		    if(d.getCategory().compareToIgnoreCase("check")==0) {
	    		    		row.createCell(3).setCellValue(d.getAmount());
	    		    		check+=d.getAmount();
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("cash")==0) {
    		    			row.createCell(4).setCellValue(d.getAmount());
    		    			cash+=d.getAmount();
	    		    }
	    		    if(d.getCategory().compareToIgnoreCase("eft")==0) {
    		    			row.createCell(5).setCellValue(d.getAmount());
    		    			EFT+=d.getAmount();
	    		    }
	    		    row.createCell(6).setCellValue(createHelper.createRichTextString(d.getDesignation()));
	    		    row.createCell(7).setCellValue(createHelper.createRichTextString(d.getDescription()));
	    		    row.createCell(8).setCellValue(createHelper.createRichTextString(d.getDonor().getAddress()));
	    		    row.createCell(9).setCellValue(createHelper.createRichTextString(d.getDonor().getCity()));
	    		    row.createCell(10).setCellValue(createHelper.createRichTextString(d.getDonor().getState()));
	    		    row.createCell(11).setCellValue(createHelper.createRichTextString(d.getDonor().getZip()));		
	    }
	    
	    int col=13;
	    
	    if(dataSheet.getRow((short)5)==null)
			dataSheet.createRow((short)5).createCell(col).setCellValue(createHelper.createRichTextString("check"));
	    else
			dataSheet.getRow((short)5).createCell(col).setCellValue(createHelper.createRichTextString("check"));

	    if(dataSheet.getRow((short)6)==null)
	    		dataSheet.createRow((short)6).createCell(col).setCellValue(createHelper.createRichTextString("cash"));
	    else
	    		dataSheet.getRow((short)6).createCell(col).setCellValue(createHelper.createRichTextString("cash"));

	    if(dataSheet.getRow((short)7)==null)
    			dataSheet.createRow((short)7).createCell(col).setCellValue(createHelper.createRichTextString("EFT PP"));
	    else
    			dataSheet.getRow((short)7).createCell(col).setCellValue(createHelper.createRichTextString("EFT PP"));

	    dataSheet.getRow(5).createCell(col+1).setCellValue(check);
	    dataSheet.getRow(6).createCell(col+1).setCellValue(cash);
	    dataSheet.getRow(7).createCell(col+1).setCellValue(EFT);
    }
    

}
