package applicationLayer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dialog;

import javax.swing.SwingConstants;
import java.awt.Window.Type;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Pattern;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PropertiesSetupFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtUrl;
	private JTextField txtUsername;
	private JTextField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PropertiesSetupFrame frame = new PropertiesSetupFrame();
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
	public PropertiesSetupFrame() {
		setType(Type.UTILITY);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 396, 235);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblUrl = new JLabel("URL:");
		lblUrl.setBounds(71, 37, 23, 14);
		contentPane.add(lblUrl);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(42, 62, 52, 14);
		contentPane.add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(42, 87, 52, 14);
		contentPane.add(lblPassword);

		txtUrl = new JTextField();
		txtUrl.setBounds(171, 34, 184, 20);
		contentPane.add(txtUrl);
		txtUrl.setColumns(10);

		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBounds(104, 59, 101, 20);
		contentPane.add(txtUsername);

		txtPassword = new JTextField();
		txtPassword.setColumns(10);
		txtPassword.setBounds(104, 84, 101, 20);
		contentPane.add(txtPassword);

		JButton btnFinish = new JButton("Finish");
		btnFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				try {

					if(!txtUrl.getText().isEmpty() && !txtUsername.getText().isEmpty() && !txtPassword.getText().isEmpty()) {

						if(!txtUrl.getText().trim().contains(" ")) {

							String regex = "\\w.*/\\w.*";

							String url = txtUrl.getText().trim();

							if(Pattern.matches(regex, url)) {

								writePropertiesFile(url, txtUsername.getText(), txtPassword.getText());

							}
							else{

								throw new Exception("Table not defined in URL: " + url);

							}
						}
						else {

							throw new Exception("URL cannot contain a space");

						}


					}
					else {

						throw new Exception("Please fill out all fields");

					}

				}
				catch(Exception e) {

					JOptionPane.showMessageDialog(PropertiesSetupFrame.this,
							e.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);

				}



			}
		});
		btnFinish.setBounds(145, 136, 89, 23);
		contentPane.add(btnFinish);

		JLabel lblUrlLead = new JLabel(" jdbc:mysql://");
		lblUrlLead.setHorizontalTextPosition(SwingConstants.CENTER);
		lblUrlLead.setOpaque(true);
		lblUrlLead.setForeground(Color.BLACK);
		lblUrlLead.setBackground(Color.LIGHT_GRAY);
		lblUrlLead.setBounds(104, 34, 69, 20);
		contentPane.add(lblUrlLead);




	}

	private void writePropertiesFile(String url, String username, String password) {

		try {

			URL main = SignInFrame.class.getResource("SignInFrame.class");
			File javaFile = new File(main.getPath());

			String absolutePath = javaFile.getAbsolutePath();
			String javaFileFolderPath = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
			String dbFilePath = javaFileFolderPath+"\\db.properties";

			Properties prop = new Properties();

			// set the properties value
			prop.setProperty("db.url", "jdbc:mysql://" + url);
			prop.setProperty("db.user", username);
			prop.setProperty("db.password", password);

			OutputStream output = new FileOutputStream(dbFilePath);

			// save properties to project root folder
			prop.store(output, null);

			System.out.println(prop);

		} catch (IOException io) {
			io.printStackTrace();
		}

	}
}
