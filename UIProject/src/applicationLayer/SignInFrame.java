package applicationLayer;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.security.crypto.bcrypt.BCrypt;

import businessLayer.EncryptionLogic;
import businessLayer.PropertiesLogic;
import businessLayer.UserAuthenticationLogic;
import dataLayer.UserAuthDatabaseCommands;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.sql.SQLInvalidAuthorizationSpecException;
import java.util.EmptyStackException;
import java.util.regex.Pattern;
import javax.swing.JPasswordField;

public class SignInFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInFrame frame = new SignInFrame();
					
					if(propertiesFileExists()) {

						EncryptionLogic encLogic = new EncryptionLogic();

						try {
							encLogic.createKeystore();
						}
						catch(FileAlreadyExistsException e) {
							System.out.println("Keystore exists");
						}

						frame.setVisible(true);
					}
					else {
						
						int choice = showAuthenticationDialog(frame);
						
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
		setLocationRelativeTo(null);
		
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
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					validateInput();
					
					UserAuthenticationLogic userAuthLogic = new UserAuthenticationLogic();
					
					String password = new String(txtPassword.getPassword());

					if(userAuthLogic.signInUser(txtUsername.getText(), password)) {
						
						HomeFrame homeFrame = new HomeFrame();
						homeFrame.setVisible(true);
						SignInFrame.this.setVisible(false);
						
					}
					else {
						throw new Exception("Incorrect details entered");
					}
				}catch(IOException ioException) {
					
					JOptionPane.showMessageDialog(SignInFrame.this,
							"Database Authentication file missing!"
							+ "\nFile may have been deleted or changed."
									+ "\n"
							+ "\nYou will now be asked to re-perform setup.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					
					int choice = showAuthenticationDialog(SignInFrame.this);
					
					if(choice == 0) {
						PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
						SignInFrame.this.dispose();
						propFrame.setVisible(true);
					}
					else {
						System.exit(ABORT);
					}
					
					
					
				} catch(SQLInvalidAuthorizationSpecException sqlException) {
					
					JOptionPane.showMessageDialog(SignInFrame.this,
							"Database Authentication failed!"
							+ "\nNo connection could be made to the database due to incorrect authentication details."
									+ "\n"
							+ "\nYou will now be asked to re-perform setup.",
							"Error",
							JOptionPane.ERROR_MESSAGE);
					
					int choice = showAuthenticationDialog(SignInFrame.this);
					
					if(choice == 0) {
						PropertiesSetupFrame propFrame = new PropertiesSetupFrame();
						SignInFrame.this.dispose();
						propFrame.setVisible(true);
					}
					else {
						System.exit(ABORT);
					}
					
				}
				catch (Exception generalException) {
					
					JOptionPane.showMessageDialog(SignInFrame.this,
							generalException.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
					
				}
				
			}
		});
		btnSignIn.setBounds(147, 235, 89, 23);
		contentPane.add(btnSignIn);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(148, 186, 86, 20);
		contentPane.add(txtPassword);
	}
	
	private static boolean propertiesFileExists() {
		
		PropertiesLogic pLogic = new PropertiesLogic();
		
		String dbFilePath = pLogic.getPropertiesFilePath();

		File dbFile = new File(dbFilePath);
		if(dbFile.exists() && !dbFile.isDirectory()) { 
		    // signifies first time running the program
			return true;
		}
		else{
		    //not first time running the program
			return false;
		}
	}
	
	private static int showAuthenticationDialog(SignInFrame frame) {
		
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
	
	private void validateInput() throws Exception{
		
		if(!txtUsername.getText().isEmpty() && txtPassword.getPassword().length != 0) {
			
			String regex = "\\w+";
			
			if(!Pattern.matches(regex, txtUsername.getText())) {
				
				throw new Exception("Invalid Username");
				
			}
			
		}
		else {
			
			throw new Exception("Please fill out all fields");
			
		}
		
	}
}
