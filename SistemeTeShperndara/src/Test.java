import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EtchedBorder;
import javax.swing.SwingConstants;

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
		setBounds(100, 100, 750, 500);
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
		
		DefaultTableModel model = new DefaultTableModel(); 
		JTable table = new JTable(model); 
		JScrollPane asd = new JScrollPane(table);

		// Create a couple of columns 
		model.addColumn("Header"); 
		model.addRow(new Object[] {"Shit"});

		asd.setBounds(486, 50, 246, 404);
		contentPane.add(asd);
		
		JLabel lbl_currently_online = new JLabel("New label");
		lbl_currently_online.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_currently_online.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbl_currently_online.setBounds(486, 10, 246, 30);
		contentPane.add(lbl_currently_online);
	}
}
