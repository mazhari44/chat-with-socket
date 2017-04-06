

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JScrollPane;
import javax.swing.JTable;


public class IdleFrame extends JFrame {

	private JPanel contentPane;
	public static JTable table;
	public static User user=null;
	
	
	public static boolean listFlag=false;
	public static boolean listResultFlag=false;
	public static boolean connectFlag=false;
	
			
	public ChatFrame chatFrame=null;
	

	/**
	 * Launch the application.
	 */


	/**
	 * Create the frame.
	 */
	public IdleFrame() {
		
		user=new User();

	
//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnShowOnlineIdle = new JButton("show online idle host");
		btnShowOnlineIdle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listFlag=true;
			}
		});
		btnShowOnlineIdle.setBounds(216, 37, 208, 23);
		contentPane.add(btnShowOnlineIdle);
		
		JButton button = new JButton("connect to this host...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				int row=table.getSelectedRow();
				String username=table.getValueAt(row,0).toString();
				user.setUsername(username);
				connectFlag=true;
				
			    chatFrame = new ChatFrame();
			    chatFrame.me.setText("me");
			    chatFrame.he.setText(username);
				chatFrame.setVisible(true);
			}
		});
		button.setBounds(216, 96, 208, 23);
		contentPane.add(button);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 37, 200, 200);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
	}
}
