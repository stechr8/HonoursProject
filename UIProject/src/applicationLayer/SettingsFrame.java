package applicationLayer;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SettingsFrame frame = new SettingsFrame();
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
	public SettingsFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JButton btnDbAuth = new JButton("Database Authentication");
		btnDbAuth.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Custom button text
				Object[] options = {"Continue",
				                    "Cancel"
				                    };
				int choice = JOptionPane.showOptionDialog(SettingsFrame.this,
				    "Changing database authentication details affects the ability to connect to the database."
				    + "\nYou will be required to sign back in after changes take effect.\n"
				    + "Continue?",
				    "Database Authentication Details",
				    JOptionPane.YES_NO_CANCEL_OPTION,
				    JOptionPane.WARNING_MESSAGE,
				    null,
				    options,
				    options[0]);
				
				if(choice == 0) {
					PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
					SettingsFrame.this.dispose();
					propFrame.setVisible(true);
				}
				
			}
		});
		btnDbAuth.setBounds(141, 29, 151, 23);
		contentPane.add(btnDbAuth);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				HomeFrame homeFrame = new HomeFrame();
				homeFrame.setVisible(true);
				SettingsFrame.this.dispose();
			}
		});
		btnBack.setBounds(10, 227, 89, 23);
		contentPane.add(btnBack);
	}
}
