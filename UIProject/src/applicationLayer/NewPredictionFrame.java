package applicationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.opencsv.CSVWriter;

import businessLayer.CSVReadingLogic;
import businessLayer.PredictionDatabaseLogic;
import businessLayer.PythonLinkLogic;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class NewPredictionFrame extends JFrame {

	private JPanel contentPane;
	private JTable tblOutput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewPredictionFrame frame = new NewPredictionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public NewPredictionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 590, 488);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblPredictor = new JLabel("PREDICTOR");
		lblPredictor.setFont(new Font("Rockwell", Font.PLAIN, 38));
		lblPredictor.setBounds(177, 11, 220, 45);
		contentPane.add(lblPredictor);
		
		JLabel lblUploadcsvFile = new JLabel("Upload '.csv' File:");
		lblUploadcsvFile.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUploadcsvFile.setBounds(34, 74, 103, 20);
		contentPane.add(lblUploadcsvFile);
		
		JPanel pnlUploadPath = new JPanel();
		FlowLayout flowLayout = (FlowLayout) pnlUploadPath.getLayout();
		flowLayout.setVgap(3);
		flowLayout.setAlignment(FlowLayout.LEFT);
		pnlUploadPath.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlUploadPath.setBounds(144, 74, 322, 20);
		contentPane.add(pnlUploadPath);
		
		JLabel lblUploadPath = new JLabel("");
		pnlUploadPath.add(lblUploadPath);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.setBounds(61, 122, 452, 267);
		
		tblOutput = new JTable();
		tblOutput.setVisible(false);
		tblOutput.setFocusable(false);
		tblOutput.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		tblOutput.setFillsViewportHeight(true);
		tblOutput.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tblOutput.setEnabled(false);
		scrollPane.setViewportView(tblOutput);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		panel.setBounds(61, 122, 452, 267);
		contentPane.add(panel);
		panel.setLayout(null);
		
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int noOfRows = tblOutput.getRowCount();
				int noOfCols = tblOutput.getColumnCount();
				
				FileWriter fWriter;
				ArrayList<String> entries = new ArrayList<String>();
				
				int cancellations = 0;
				
				try {
					
					String directory = lblUploadPath.getText().substring(0, lblUploadPath.getText().lastIndexOf(File.separator));
					
					fWriter = new FileWriter(directory + "\\Prediction.csv");
					
					CSVWriter writer = new CSVWriter(fWriter);
				
					for(int i = 0; i < noOfRows; i++) {
						for(int j = 0; j < noOfCols; j++) {
							//get each cell and add to ArrayList
							String entry = tblOutput.getModel().getValueAt(i, j).toString();
							//if entry is of IsCancelled and is less than 50%
							if(j == 0 && Double.parseDouble(entry) < 50) {
								cancellations++;
							}
						    entries.add(entry);
						}
						
						//Convert to string array
				        String[] array = entries.toArray(new String[entries.size()]);
						writer.writeNext(array);
				     
					}
					
				writer.close();
				
				
				PredictionDatabaseLogic predDbLogic = new PredictionDatabaseLogic();
				
				predDbLogic.saveToDB(noOfRows, cancellations);
				
				JOptionPane.showMessageDialog(NewPredictionFrame.this,
						"File Saved to '" + directory + "'",
						"Success!",
						JOptionPane.ERROR_MESSAGE);
				
				} 
				catch(IOException ioException) {
					
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							ioException.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
				catch(SQLInvalidAuthorizationSpecException sqlException) {
					
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							"Database Authentication failed!"
							+ "\nNo connection could be made to the database due to incorrect authentication details."
									+ "\n"
							+ "\nYou will now be asked to re-perform setup.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					
					int choice = showAuthenticationDialog(NewPredictionFrame.this);
					
					if(choice == 0) {
						PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
						NewPredictionFrame.this.dispose();
						propFrame.setVisible(true);
					}
					else {
						System.exit(ABORT);
					}
					
				}
				catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							e.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		btnSave.setBounds(475, 415, 89, 23);
		btnSave.setVisible(false);
		contentPane.add(btnSave);
		
		JButton btnRunNew = new JButton("Run New Prediction");
		btnRunNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				WaitDialog waitDialog = new WaitDialog();
				
				waitDialog.setVisible(true);
				
				try {
					PythonLinkLogic pyLogic = new PythonLinkLogic();
					
					ArrayList<String> predList = pyLogic.executePythonScript(lblUploadPath.getText());
					
					CSVReadingLogic csvLogic = new CSVReadingLogic();
					
					ArrayList<String[]> csvEntries = csvLogic.readCsvForTable(lblUploadPath.getText());
					
		            String[] columnNames = new String[] {
		            		"Chance of Attendance (%)", "LeadTime", "ArrivalDateYear", "ArrivalDateMonth", "ArrivalDateWeekNumber", "ArrivalDateDayOfMonth",
		            		"StaysInWeekendNights", "StaysInWeekNights", "Adults", "Children", "Babies", "Meal", "Country", "MarketSegment", "DistributionChannel",
		            		"IsRepeatedGuest", "PreviousCancellations", "PreviousBookingsNotCanceled", "ReservedRoomType", "AssignedRoomType", "BookingChanges",
		            		"DepositType", "Agent", "Company", "DaysInWaitingList", "CustomerType",	"ADR", "RequiredCarParkingSpaces", "TotalOfSpecialRequests",
		            		"ReservationStatus", "ReservationStatusDate"

		                };
		            
		            Object[][] tableContent = new Object[csvEntries.size()][columnNames.length];

		            for(int i = 0; i < csvEntries.size(); i++) {
		            	for(int k = 0; k < columnNames.length; k++) {
		            			tableContent[i][k] = csvEntries.get(i)[k];
		            		}

		            }
		            
		            for(int i = 0; i < csvEntries.size(); i++) {
		            	tableContent[i][0] = predList.get(i);
		            }

		            tblOutput.setModel(new DefaultTableModel(tableContent, columnNames));
		            
		            waitDialog.setVisible(false);
		            tblOutput.setVisible(true);
		            panel.setVisible(false);
		            panel.setEnabled(false);
		            scrollPane.setVisible(true);
		            btnSave.setVisible(true);
					
				}
				catch(IOException ioException) {
					
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							ioException.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							e.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnRunNew.setBounds(163, 122, 128, 23);
		btnRunNew.setPreferredSize(new Dimension(128, 23));
		btnRunNew.setVisible(false);
		panel.add(btnRunNew);
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					String filename = File.separator+"tmp";
					JFileChooser fc = new JFileChooser(filename);

					// Show open dialog
					fc.showOpenDialog(NewPredictionFrame.this);
					File selFile = fc.getSelectedFile();
					
					if(!selFile.getAbsolutePath().endsWith(".csv")) {
						throw new Exception("Please select a '.csv' file.");
					}
					
					lblUploadPath.setText(selFile.getAbsolutePath());
					
					btnRunNew.setVisible(true);
				}
				catch(Exception ex) {
					
					JOptionPane.showMessageDialog(NewPredictionFrame.this,
							ex.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		btnBrowse.setBounds(475, 74, 89, 23);
		contentPane.add(btnBrowse);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HomeFrame homeFrame = new HomeFrame();
				homeFrame.setVisible(true);
				NewPredictionFrame.this.dispose();
				
			}
		});
		btnBack.setBounds(10, 415, 89, 23);
		contentPane.add(btnBack);
	}
	
private static int showAuthenticationDialog(NewPredictionFrame frame) {
		
		//Custom button text
		Object[] options = {"Continue",
		                    "Later (Exit)"
		                    };
		int choice = JOptionPane.showOptionDialog(frame,
		    "Database authentication details are required.\n"
		    + "\n"
		    + "Continue setup?",
		    "Setup Not Complete",
		    JOptionPane.YES_NO_CANCEL_OPTION,
		    JOptionPane.WARNING_MESSAGE,
		    null,
		    options,
		    options[0]);
		
		return choice;
	}
}
