package applicationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import businessLayer.PythonLinkLogic;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.io.File;
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
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String filename = File.separator+"tmp";
				JFileChooser fc = new JFileChooser(filename);

				// Show open dialog
				fc.showOpenDialog(NewPredictionFrame.this);
				File selFile = fc.getSelectedFile();
				
				lblUploadPath.setText(selFile.getAbsolutePath());
				
				PythonLinkLogic pyLogic = new PythonLinkLogic();
				
				pyLogic.executePythonScript(selFile.getAbsolutePath());
				
			}
		});
		btnBrowse.setBounds(475, 74, 89, 23);
		contentPane.add(btnBrowse);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(61, 122, 452, 267);
		scrollPane.setVisible(false);
		
		JPanel panel = new JPanel();
		panel.setBounds(61, 122, 452, 267);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnRunNew = new JButton("Run New Prediction");
		btnRunNew.setBounds(163, 122, 128, 23);
		btnRunNew.setPreferredSize(new Dimension(128, 23));
		panel.add(btnRunNew);
		
		JLabel lblTableBg = new JLabel("");
		lblTableBg.setBounds(0, 0, 450, 267);
		lblTableBg.setIcon(new ImageIcon("C:\\Users\\stech\\git\\HonoursProject\\UIProject\\res\\scrollPaneBG.png"));
		panel.add(lblTableBg);
		contentPane.add(scrollPane);
		
		tblOutput = new JTable();
		tblOutput.setFocusable(false);
		tblOutput.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		tblOutput.setFillsViewportHeight(true);
		tblOutput.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblOutput.setEnabled(false);
		scrollPane.setViewportView(tblOutput);
		
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
