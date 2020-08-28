package applicationLayer;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Window.Type;

public class WaitDialog extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WaitDialog dialog = new WaitDialog();
					dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public WaitDialog() {
		setUndecorated(true);
		setType(Type.UTILITY);
		setBounds(100, 100, 121, 61);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblPleaseWait = new JLabel("Please Wait...");
		lblPleaseWait.setBounds(26, 23, 68, 14);
		getContentPane().add(lblPleaseWait);

	}
}
