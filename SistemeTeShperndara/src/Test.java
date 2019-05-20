import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;

public class Test extends JFrame {

	private JPanel contentPane;
	private JTextField txt_text;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane txt_area = new JTextPane();
		txt_area.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_area.setBounds(10, 10, 466, 404);
		contentPane.add(txt_area);
		
		JButton btnNewButton = new JButton("Send");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(366, 424, 110, 30);
		contentPane.add(btnNewButton);
		
		txt_text = new JTextField();
		txt_text.setFont(new Font("Tahoma", Font.PLAIN, 12));
		txt_text.setBounds(10, 424, 346, 30);
		contentPane.add(txt_text);
		txt_text.setColumns(10);
	}
}
