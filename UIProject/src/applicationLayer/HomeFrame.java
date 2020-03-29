package applicationLayer;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import businessLayer.EncryptionLogic;
import businessLayer.PredictionDatabaseLogic;
import classes.PredictionClass;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.awt.event.ActionEvent;

public class HomeFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HomeFrame frame = new HomeFrame();
					
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
	public HomeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 381, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblPredictor = new JLabel("PREDICTOR");
		lblPredictor.setFont(new Font("Rockwell", Font.PLAIN, 38));
		lblPredictor.setBounds(72, 11, 220, 45);
		contentPane.add(lblPredictor);
		
		JPanel pnlRecent = new JPanel();
		pnlRecent.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlRecent.setBounds(26, 76, 188, 134);
		contentPane.add(pnlRecent);
		
		JLabel lblMostRecentPrediction = new JLabel("Most Recent Prediction");
		lblMostRecentPrediction.setBounds(23, 11, 135, 18);
		lblMostRecentPrediction.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		JLabel lblDatePerformed = new JLabel("Date Performed:");
		lblDatePerformed.setBounds(10, 42, 86, 16);
		lblDatePerformed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblDate = new JLabel("N/A");
		lblDate.setBounds(120, 40, 58, 16);
		lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblTotalBookings = new JLabel("Total Bookings:");
		lblTotalBookings.setBounds(10, 70, 81, 16);
		lblTotalBookings.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblBookings = new JLabel("N/A");
		lblBookings.setBounds(153, 70, 25, 16);
		lblBookings.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblPredCancellations = new JLabel("Predicted Cancellations:");
		lblPredCancellations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPredCancellations.setBounds(10, 97, 133, 14);
		pnlRecent.setLayout(null);
		pnlRecent.add(lblMostRecentPrediction);
		pnlRecent.add(lblDatePerformed);
		pnlRecent.add(lblDate);
		pnlRecent.add(lblTotalBookings);
		pnlRecent.add(lblBookings);
		pnlRecent.add(lblPredCancellations);
		
		JLabel lblCancellations = new JLabel("N/A");
		lblCancellations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblCancellations.setBounds(153, 97, 25, 14);
		pnlRecent.add(lblCancellations);
		
		JButton btnRunNewPred = new JButton("Run New Prediction");
		btnRunNewPred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				NewPredictionFrame newPredFrame = new NewPredictionFrame();
				newPredFrame.setVisible(true);
				HomeFrame.this.setVisible(false);
				
			}
		});
		btnRunNewPred.setBounds(56, 247, 125, 23);
		contentPane.add(btnRunNewPred);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				SettingsFrame settingsFrame = new SettingsFrame();
				settingsFrame.setVisible(true);
				HomeFrame.this.dispose();
				
			}
		});
		btnSettings.setBounds(250, 76, 104, 23);
		contentPane.add(btnSettings);
		
		JButton btnSignOut = new JButton("Sign Out");
		btnSignOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SignInFrame signIn = new SignInFrame();
				signIn.setVisible(true);
				HomeFrame.this.dispose();
				
			}
		});
		btnSignOut.setBounds(250, 110, 104, 23);
		contentPane.add(btnSignOut);
		
		PredictionDatabaseLogic pdLogic = new PredictionDatabaseLogic();
		
		try {
			
			PredictionClass pred = pdLogic.getLatestPred();
			
			lblDate.setText(pred.getDate());
			lblBookings.setText(Integer.toString(pred.getTotalBookings()));
			lblCancellations.setText(Integer.toString(pred.getCancellations()));
			
			
		} catch(SQLInvalidAuthorizationSpecException sqlException) {
			
			JOptionPane.showMessageDialog(HomeFrame.this,
					"Database Authentication failed!"
					+ "\nNo connection could be made to the database due to incorrect authentication details."
							+ "\n"
					+ "\nYou will now be asked to re-perform setup.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			int choice = showAuthenticationDialog(HomeFrame.this);
			
			if(choice == 0) {
				PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
				HomeFrame.this.dispose();
				propFrame.setVisible(true);
			}
			else {
				System.exit(ABORT);
			}
			
		} catch (IOException e1) {

			JOptionPane.showMessageDialog(HomeFrame.this,
					"Database Authentication file missing!"
					+ "\nFile may have been deleted or changed."
							+ "\n"
					+ "\nYou will now be asked to re-perform setup.",
					"Error",
					JOptionPane.ERROR_MESSAGE);
			
			int choice = showAuthenticationDialog(HomeFrame.this);
			
			if(choice == 0) {
				PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
				HomeFrame.this.dispose();
				propFrame.setVisible(true);
			}
			else {
				System.exit(ABORT);
			}
		}

	}

	private int showAuthenticationDialog(HomeFrame homeFrame) {

		//Custom button text
				Object[] options = {"Continue",
				                    "Later (Exit)"
				                    };
				int choice = JOptionPane.showOptionDialog(homeFrame,
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
