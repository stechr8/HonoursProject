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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
		pnlRecent.setBounds(26, 76, 188, 144);
		contentPane.add(pnlRecent);
		
		JLabel lblMostRecentPrediction = new JLabel("Most Recent Prediction");
		lblMostRecentPrediction.setBounds(23, 11, 135, 18);
		lblMostRecentPrediction.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		
		JLabel label = new JLabel("");
		label.setBounds(153, 15, 0, 0);
		
		JLabel label_1 = new JLabel("");
		label_1.setBounds(158, 15, 0, 0);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(163, 15, 0, 0);
		
		JLabel label_3 = new JLabel("");
		label_3.setBounds(-10048, -10103, 0, 0);
		
		JLabel lblDatePerformed = new JLabel("Date Performed:");
		lblDatePerformed.setBounds(10, 42, 86, 16);
		lblDatePerformed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel label_4 = new JLabel("");
		label_4.setBounds(99, 37, 0, 0);
		
		JLabel lblDate = new JLabel("01/01/2019");
		lblDate.setBounds(120, 40, 58, 16);
		lblDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel label_5 = new JLabel("");
		label_5.setBounds(167, 37, 0, 0);
		
		JLabel label_6 = new JLabel("");
		label_6.setBounds(-10048, -10103, 0, 0);
		
		JLabel lblTotalBookings = new JLabel("Total Bookings:");
		lblTotalBookings.setBounds(10, 63, 81, 16);
		lblTotalBookings.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel label_7 = new JLabel("");
		label_7.setBounds(117, 58, 0, 0);
		
		JLabel label_8 = new JLabel("");
		label_8.setBounds(122, 58, 0, 0);
		
		JLabel label_9 = new JLabel("");
		label_9.setBounds(127, 58, 0, 0);
		
		JLabel lblBookings = new JLabel("128");
		lblBookings.setBounds(160, 63, 18, 16);
		lblBookings.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblPredCancellations = new JLabel("Predicted Cancellations:");
		lblPredCancellations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPredCancellations.setBounds(10, 84, 133, 14);
		
		JLabel label_10 = new JLabel("");
		label_10.setBounds(143, 78, 0, 0);
		
		JLabel label_11 = new JLabel("");
		label_11.setBounds(148, 78, 0, 0);
		
		JLabel label_12 = new JLabel("");
		label_12.setBounds(153, 78, 0, 0);
		
		JLabel label_13 = new JLabel("");
		label_13.setBounds(-10048, -10103, 0, 0);
		pnlRecent.setLayout(null);
		pnlRecent.add(lblMostRecentPrediction);
		pnlRecent.add(label);
		pnlRecent.add(label_1);
		pnlRecent.add(label_2);
		pnlRecent.add(label_3);
		pnlRecent.add(lblDatePerformed);
		pnlRecent.add(label_4);
		pnlRecent.add(lblDate);
		pnlRecent.add(label_5);
		pnlRecent.add(label_6);
		pnlRecent.add(lblTotalBookings);
		pnlRecent.add(label_7);
		pnlRecent.add(label_8);
		pnlRecent.add(label_9);
		pnlRecent.add(lblBookings);
		pnlRecent.add(lblPredCancellations);
		pnlRecent.add(label_10);
		pnlRecent.add(label_11);
		pnlRecent.add(label_12);
		pnlRecent.add(label_13);
		
		JLabel lblCancellations = new JLabel("32");
		lblCancellations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblCancellations.setBounds(166, 84, 12, 14);
		pnlRecent.add(lblCancellations);
		
		JLabel lblViewPredictedCancellations = new JLabel("<html><u>View Predicted Cancellations</u></html>");
		lblViewPredictedCancellations.setForeground(Color.BLUE);
		lblViewPredictedCancellations.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblViewPredictedCancellations.setBounds(16, 118, 155, 14);
		pnlRecent.add(lblViewPredictedCancellations);
		
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
		btnSettings.setBounds(250, 76, 104, 23);
		contentPane.add(btnSettings);
		
		JButton btnViewHistory = new JButton("View History");
		btnViewHistory.setBounds(250, 110, 104, 23);
		contentPane.add(btnViewHistory);
	}
}
