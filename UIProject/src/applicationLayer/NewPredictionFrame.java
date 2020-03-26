package applicationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.List;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLayer.CSVReadingLogic;
import businessLayer.PythonLinkLogic;

import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
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
		
		JButton btnRunNew = new JButton("Run New Prediction");
		btnRunNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					PythonLinkLogic pyLogic = new PythonLinkLogic();
					
					/*ArrayList<String> predList = pyLogic.executePythonScript(lblUploadPath.getText());
					
					if(predList == null) {
						throw new Exception("There has been an error reading in the data."
								+ "\nPlease review your data and ensure only the 'Agent' and 'Company' fields\n"
								+ "contain NULL values.");
					}*/
					
					CSVReadingLogic csvLogic = new CSVReadingLogic();
					
					ArrayList<String[]> csvEntries = csvLogic.readCsvForTable(lblUploadPath.getText());
					
		            String[] columnNames = new String[] {
		            		"IsCanceled", "LeadTime", "ArrivalDateYear", "ArrivalDateMonth", "ArrivalDateWeekNumber", "ArrivalDateDayOfMonth",
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

		            tblOutput.setModel(new DefaultTableModel(tableContent, columnNames));
		            tblOutput.setVisible(true);
		            panel.setVisible(false);
		            panel.setEnabled(false);
		            scrollPane.setVisible(true);
					
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
		
		JLabel lblTableBg = new JLabel("");
		lblTableBg.setBounds(0, 0, 450, 267);
		lblTableBg.setIcon(new ImageIcon("C:\\Users\\stech\\git\\HonoursProject\\UIProject\\res\\scrollPaneBG.png"));
		panel.add(lblTableBg);
		
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
}
