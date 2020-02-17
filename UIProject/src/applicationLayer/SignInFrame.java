package applicationLayer;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.net.URL;

public class SignInFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JTextField txtPassword;	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInFrame frame = new SignInFrame();
					
					if(propertyFileExists()) {
						frame.setVisible(true);
					}else {
						
						int choice = showDialog(frame);
						
						if(choice == 0) {
							PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
							propFrame.setVisible(true);
						}
						else {
							System.exit(ABORT);
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignInFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPredictor = new JLabel("PREDICTOR");
		lblPredictor.setFont(new Font("Rockwell", Font.PLAIN, 38));
		lblPredictor.setBounds(81, 11, 220, 45);
		contentPane.add(lblPredictor);
		
		JLabel lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblUser.setBounds(160, 97, 63, 14);
		contentPane.add(lblUser);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		lblPassword.setBounds(162, 161, 58, 14);
		contentPane.add(lblPassword);
		
		txtUsername = new JTextField();
		txtUsername.setBounds(148, 122, 86, 20);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(148, 186, 86, 20);
		contentPane.add(txtPassword);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				HomeFrame homeFrame = new HomeFrame();
				homeFrame.setVisible(true);
				SignInFrame.this.setVisible(false);
				
			}
		});
		btnSignIn.setBounds(147, 235, 89, 23);
		contentPane.add(btnSignIn);
	}
	
	private static boolean propertyFileExists() {
		
		URL main = SignInFrame.class.getResource("SignInFrame.class");
		File javaFile = new File(main.getPath());

		String absolutePath = javaFile.getAbsolutePath();
		String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
		String dbFilePath = javaFileFolderPath+"\\db.properties";

		File dbFile = new File(dbFilePath);
		if(dbFile.exists() && !dbFile.isDirectory()) { 
		    // this is not the first time running the program
			return true;
		}
		else{
		    //this is the first time running the program
			return false;
		}
	}
	
	private static int showDialog(SignInFrame frame) {
		
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
