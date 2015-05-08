package hkust.cse.calendar.gui;

import hkust.cse.calendar.UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.User;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
	private JScrollPane listSP;
	
	private DefaultListModel<User> othersListModel;
	private JList<User> othersList;
	private JScrollPane othersListSP;

	private ArrayList<User> invitedUser;
	private ArrayList<User> canSelectUser;
	
	private JLabel Llabel;
	private JLabel Rlabel;
	private JButton addButton;
	private JButton removeButton;
	
	private Appt NewAppt;

	public InviteJointApptJDialog(CalGrid cal, AppScheduler appsch, Appt newAppt){
		parent = cal;
		lock = appsch;
		NewAppt = newAppt;
		
		invitedUser = new ArrayList<User>();
		canSelectUser = new ArrayList<User>();
		
		Llabel = new JLabel("Invite users: ");
		listModel = new DefaultListModel<User>();
		list = new JList<User>(listModel);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setSelectedIndex(0);
		listSP = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		Rlabel = new JLabel("Other users: ");
		othersListModel = new DefaultListModel<User>();
		othersList = new JList<User>();
		othersList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		othersList.setSelectedIndex(0);
		othersListSP = new JScrollPane(othersList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		addButton = new JButton("<<");
		removeButton = new JButton(">>");
		//JButton remove = new JButton("Remove");
		
		JPanel left = new JPanel(new BorderLayout());
		left.add(Llabel, BorderLayout.NORTH);
		left.add(list, BorderLayout.CENTER);
		
		JPanel center = new JPanel(new GridLayout());
		left.add(addButton);
		left.add(removeButton);
		
		JPanel right = new JPanel(new BorderLayout());
		left.add(Rlabel, BorderLayout.NORTH);
		left.add(othersList, BorderLayout.CENTER);
		
		//this.getContentPane().add(Llabel, BorderLayout.NORTH);
		//this.getContentPane().add(list, BorderLayout.CENTER);
		//this.getContentPane().add(addButton, BorderLayout.SOUTH);
		
		this.getContentPane().add(left, BorderLayout.WEST);
		this.getContentPane().add(center, BorderLayout.CENTER);
		this.getContentPane().add(right, BorderLayout.EAST);
		
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
		if (e.getSource() == addButton){
			List<User> selected = list.getSelectedValuesList();
			if (selected == null){
				JOptionPane.showMessageDialog(this, "Please select users!",
						"Input Error", JOptionPane.ERROR_MESSAGE);
			}else{
				for (int i = 0; i < selected.size(); i++){
					listModel.remove(i); /// how?
				}
			}
		}
	}

}
