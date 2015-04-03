package hkust.cse.calendar.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import hkust.cse.calendar.apptstorage.ApptStorageControllerImpl;
import hkust.cse.calendar.unit.Location;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class LocationsDialog extends JFrame {
	private static final long searialVersionUID = 1L;
	
	private ApptStorageControllerImpl _controller;
	
	private DefaultListModel<Location> listModel;
	private JList<Location> list;
	private JTextField locNameText;
	
	public LocationsDialog(ApptStorageControllerImpl controller){
		_controller = controller;
		
		this.setLayout(new BorderLayout());
		this.setLocationByPlatform(true);
		this.setSize(300,200);
		
		listModel = new DefaultListModel<Location>();
		
		list = new JList<Location>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setSelectedIndex(0);
		
		JButton add = new JButton("Add");
		JButton remove = new JButton("Remove");
		locNameText = new JTextField();
		
		//int index = -1;
		
		add.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				String temp = locNameText.getText();
				if (temp.equals("")){
					JOptionPane.showMessageDialog(null, "Please input locations.",
	                        "Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else{
					//System.out.println(locNameText.getText());
					Location[] tempList = _controller.getLocationList();
					Location[] newList;
					if (tempList == null || tempList.length == 0)
						newList = new Location[1];
					else newList = new Location[tempList.length+1];
					for (int i = 0; i < listModel.size(); i++){		// listModel is just the display
						if (listModel.getElementAt(i).getName().equals(temp)){		// Added .getName()
							JOptionPane.showMessageDialog(null, "Location already exists. Please input again.",
			                        "Input Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
					//newList[newList.length].setName(temp);
					//listModel.insertElementAt(new Location(temp), 0);
					listModel.addElement(new Location(temp));       // Output address??
					// Add toString() in Location
					//System.out.println(listModel.elementAt(0));

					if (tempList != null){
						for (int i = 0; i < tempList.length; i++){
							newList[i] = tempList[i];
						}
						newList[tempList.length] = new Location(temp);
					} else newList[0] = new Location(temp);
					_controller.setLocationList(newList);
					locNameText.setText("");
				}
			}
		});
		
		remove.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int index = list.getSelectedIndex();
				if (index != -1){
					listModel.remove(index);
					if (listModel.isEmpty() != true){
						Location[] newList = new Location[listModel.size()];
						for (int i = 0; i < listModel.size(); i++){
							newList[i] = listModel.getElementAt(i);
						}

						_controller.setLocationList(newList);
					}
					JOptionPane.showMessageDialog(null, "Location removed.",
	                        "Successful", JOptionPane.INFORMATION_MESSAGE);
						
				}
			}
			
		});
		
		list.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if (list.getSelectedValue() != null){
					//locNameText.setText(list.getSelectedValue().getName());
					//selectedItem = list.getSelectedValue();
					//index = list.getSelectedIndex();
				}
			}
		});
		
		this.add(list);
		
		JPanel subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(locNameText, BorderLayout.CENTER);
		
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new BorderLayout());
		btnPanel.add(add, BorderLayout.WEST);
		btnPanel.add(remove, BorderLayout.EAST);
		
		subPanel.add(btnPanel, BorderLayout.EAST);
		
		this.add(subPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
}
