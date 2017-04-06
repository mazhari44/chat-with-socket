
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Color;

public class ChatFrame extends JFrame {

	private JPanel contentPane;
	
	public static JTextArea textPane0 = null;
	public static JTextArea textPane1 = null;
	public static JTextArea textPane2 = null;
	public  JLabel me=null;
	public  JLabel he=null;
	
	public static boolean sendFlag = false;
	public static boolean closeFlag=false;

	public ChatFrame() {
		

		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		me = new JLabel("");
		me.setBackground(Color.CYAN);
		me.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		me.setBounds(15, 5, 281, 41);
		contentPane.add(me);

		 he = new JLabel("");
		 he.setBackground(Color.CYAN);
		 he.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 18));
		he.setBounds(332, 5, 281, 41);
		contentPane.add(he);

		textPane0 = new JTextArea();
		textPane0.setBounds(332, 57, 281, 300);
		contentPane.add(textPane0);

		textPane1 = new JTextArea();
		textPane1.setBounds(15, 57, 281, 300);
		contentPane.add(textPane1);

		textPane2 = new JTextArea();
		textPane2.setBounds(15, 378, 439, 44);
		contentPane.add(textPane2);

		JButton btnSend = new JButton("send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendFlag = true;
			}
		});
		btnSend.setBounds(502, 390, 89, 23);
		contentPane.add(btnSend);
		
		JButton button = new JButton("close");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				closeFlag=true;
				dispose();
			}
		});
		button.setBounds(502, 439, 89, 23);
		contentPane.add(button);
	}
}
