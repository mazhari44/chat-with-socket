import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField userTf;
	private JPasswordField passF;
	private JTextField ipTf;
	private JTextField portTF;
	private JLabel passL;
	private JLabel userL;
	private JButton btnLogin;

	public static boolean loginFlag = false;
	public static boolean loginResultFlag = false;
	public static User user = null;

	public IdleFrame idleFrame = null;

	public User getUser() {
		return user;
	}

	public LoginFrame() {

		user = new User();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		userTf = new JTextField();
		userTf.setBounds(165, 86, 172, 20);
		contentPane.add(userTf);
		userTf.setColumns(10);

		passF = new JPasswordField();
		passF.setBounds(165, 117, 172, 20);
		contentPane.add(passF);

		userL = new JLabel("client username\r\n");
		userL.setFont(new Font("Tahoma", Font.PLAIN, 16));
		userL.setBounds(10, 84, 134, 20);
		contentPane.add(userL);

		passL = new JLabel("client password");
		passL.setFont(new Font("Tahoma", Font.PLAIN, 16));
		passL.setBounds(10, 120, 134, 20);
		contentPane.add(passL);

		btnLogin = new JButton("login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				user.setPassword(String.valueOf(passF.getPassword()));
				user.setUsername(userTf.getText());
				MyClient.address=ipTf.getText();
				MyClient.port=Integer.valueOf(portTF.getText());
				loginFlag = true;
				

			}
		});

		btnLogin.setBounds(314, 198, 89, 23);
		contentPane.add(btnLogin);

		JLabel lblServver = new JLabel("Server IP");
		lblServver.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblServver.setBounds(10, 21, 134, 20);
		contentPane.add(lblServver);

		JLabel lblServerPort = new JLabel("Server Port");
		lblServerPort.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblServerPort.setBounds(10, 52, 134, 14);
		contentPane.add(lblServerPort);

		ipTf = new JTextField();
		ipTf.setColumns(10);
		ipTf.setBounds(165, 23, 172, 20);
		contentPane.add(ipTf);

		portTF = new JTextField();
		portTF.setColumns(10);
		portTF.setBounds(165, 51, 172, 20);
		contentPane.add(portTF);

	}
}
