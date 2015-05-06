package hkust.cse.calendar.gui;

import hkust.cse.calendar.UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;

public class InviteJointApptJDialog extends JDialog implements ActionListener {
	
	private CalGrid parent;
	private UserStorageControllerImpl usercontroller;
	private AppScheduler lock;
	
	//private ArrayList<User> allUser;
	//private LinkedList<User> allParticipants;
	private LinkedList<String> allParticipants;
	
	private DefaultListModel<User> listModel;
	private JList<User> list;

	private ArrayList<User> invitedUser;
	private ArrayList<User> canSelectUser;;
	
	private JLabel label;
	private JButton confirm;
	
	private Appt NewAppt;

	public InviteJointApptJDialog(CalGrid cal, AppScheduler appsch, Appt newAppt){
		parent = cal;
		lock = appsch;
		NewAppt = newAppt;
		
		invitedUser = new ArrayList<User>();
		canSelectUser = new ArrayList<User>();
		
		label = new JLabel("Invite users: ");
		
		listModel = new DefaultListModel<User>();
		
		list = new JList<User>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		
		confirm = new JButton("Confirm");
		//JButton remove = new JButton("Remove");
		
		this.getContentPane().add(label, BorderLayout.NORTH);
		this.getContentPane().add(list, BorderLayout.CENTER);
		this.getContentPane().add(confirm, BorderLayout.SOUTH);
		this.setTitle("Invitation Dialog");
		this.setSize(new Dimension(300, 200));
		this.setResizable(false);
		this.setVisible(true);
		
		loadAvailableUsers();
	}
	
	public void loadAvailableUsers(){
		allParticipants = NewAppt.getAllPeople();
		UserStorageControllerImpl users = UserStorageControllerImpl.getInstance();
		User[] allUsers = users.retrieveAllUser();
		if (allUsers != null){
			for (int i = 0; i < users.getSize(); i++){
				if (allUsers[i] != parent.mCurrUser){
					if (allParticipants.contains(allUsers[i])){
						invitedUser.add(allUsers[i]);
						continue;
					}else {
						canSelectUser.add(allUsers[i]);
						listModel.addElement(allUsers[i]);
					}
				}
			}
			list.setModel(listModel);
			list.setSelectedIndex(0);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == confirm){
			List<User> selected = list.getSelectedValuesList();
			if (selected == null){
				JOptionPane.showMessageDialog(this, "Please select users!",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				for (int i = 0; i < selected.size(); i++){
					list.remove(selected.get(i)); /// how?
				}
			}
		}
	}

}
