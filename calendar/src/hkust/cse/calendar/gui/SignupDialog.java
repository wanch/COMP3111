package hkust.cse.calendar.gui;

import hkust.cse.calendar.UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.User;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class SignupDialog  extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel userNameL;
	private JLabel passwordL;
	private JTextField userName;
	private JPasswordField password;
	private JButton submitButton;
	private JButton closeButton;
	private JButton clearButton;
	
	private UserStorageControllerImpl controller;
	
	public SignupDialog(UserStorageControllerImpl UserStorageControllerImpl){
		
		controller = UserStorageControllerImpl;
		
		setSize(400, 200);
		//this.setLocation(x, y);
		
		setTitle("Sign Up");
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		Container contentPane;
		contentPane = getContentPane();
		
		JPanel top = new JPanel();
		top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
		
		JPanel messPanel = new JPanel();
		messPanel.add(new JLabel("Please input your user name and password to sign up."));
		top.add(messPanel);
		
		JPanel namePanel = new JPanel();		
		userNameL = new JLabel("User Name:");
		namePanel.add(userNameL);
		userName = new JTextField(15);
		namePanel.add(userName);
		top.add(namePanel);
		
		JPanel pwPanel = new JPanel();
		passwordL = new JLabel("Password:  ");
		pwPanel.add(passwordL);
		password = new JPasswordField(15);
		pwPanel.add(password);
		top.add(pwPanel);

		
		contentPane.add("North", top);
		
		JPanel butPanel = new JPanel();
		butPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//JPanel signupPanel = new JPanel();
		//signupPanel.add(new JLabel("If you don't have an account, please sign up:"));
		submitButton = new JButton("Submit");
		submitButton.addActionListener(this);
		//signupPanel.add(submitButton);
		butPanel.add(submitButton);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		butPanel.add(clearButton);
				
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		butPanel.add(closeButton);

		contentPane.add("South", butPanel);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton){
			createUser();
		}else if (e.getSource() == clearButton){
			userName.setText("");
			password.setText("");
		}else if (e.getSource() == closeButton){
			dispose();
		}
	}
	
	public void createUser(){
		String name = userName.getText();
		char[] pw = password.getPassword();
		String pass = new String(pw);
		if (name.equals("")){
			JOptionPane.showMessageDialog(this,
			"Please input user name.", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return;
		}else if (pass.equals("")){
			JOptionPane.showMessageDialog(this,
			"Please input password.", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return;
		}
		if (controller.cheakUserExist(name) == true){
			JOptionPane.showMessageDialog(this,
			"User name already exist.", "Input Error",
			JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {

			User newUser = new User(name, pass);
			controller.addNewUser(newUser);
			JOptionPane.showMessageDialog(this, "User account areated.");
		}
	}
}
