package hkust.cse.calendar.apptstorage;

import java.awt.Frame;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import hkust.cse.calendar.UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;
	Location[] _locations;
	private String filepath = "D:/apptstorage.txt";
	
	public ApptStorageNullImpl( User user )
	{
		defaultUser = user;
		//mAppts = new HashMap()<int, Appt>;
		mAppts = new HashMap<Integer, Appt>();
		//userController = UserStorageControllerImpl.getInstance();
		mAssignedApptID = 0;
	}
	
	// ADD
	@Override
	public Location[] getLocationList(){
		return _locations;
	}
	// ADD
	@Override
	public void setLocationList(Location[] locations){
		_locations = locations;
	}
	
	@Override
	public void SaveAppt(Appt appt) {
		// TODO Auto-generated method stub
		if (mAppts.isEmpty()){
			appt.setID(mAssignedApptID);
			System.out.println("Empty! title = " + appt.getTitle());
		}else {

			TimeSpan time = appt.TimeSpan();
			System.out.println("SaveAppt size=" + mAppts.size());
			Appt[] overlapEvents = RetrieveAppts(time);
			System.out.println("RetrieveAppts overlapEvents");
			
			/*for (int i = 0; i < overlapEvents.length; i++){
				if (overlapEvents[i].TimeSpan().Overlap(time)){
					JOptionPane.showMessageDialog(null, "Time overlap!",
							"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
			for (int i = 0; i < mAppts.size(); i++){
				if (mAppts.containsKey(i)){
					Appt temp = mAppts.get(i);
					if (temp.TimeSpan().Overlap(time)){
						JOptionPane.showMessageDialog(null, appt.getTitle(),
				                "Error!", JOptionPane.INFORMATION_MESSAGE);
						return;
					}
				}
			}*/
			
			if (overlapEvents != null){
	        //if (overlapEvents.length > 0){
	        	//JOptionPane.showMessageDialog(null, "Time overlap!",
						//"Input Error", JOptionPane.INFORMATION_MESSAGE);
	        	JOptionPane optionPane = new JOptionPane();
	        	JDialog dialog = optionPane.createDialog("Input Error");
	        	optionPane.setMessage("Time overlap!");
	        	optionPane.setMessageType(JOptionPane.ERROR_MESSAGE);
	        	dialog.setAlwaysOnTop(true);
	        	dialog.setVisible(true);
	        	//JOptionPane.showMessageDialog(dialog, dialog, null, index, null)
	        	return;
	        }
		}
		//int index = mAppts.size();
		appt.setID(mAssignedApptID);
    	JOptionPane.showMessageDialog(null, appt.getTitle(),
                "Success", JOptionPane.INFORMATION_MESSAGE);
		mAppts.put(appt.getID(), appt);
		System.out.println("appt id = " + appt.getID()+ " title = " + appt.getTitle());
		mAssignedApptID = mAssignedApptID +1;
	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Auto-generated method stub
		Appt[] crash = new Appt[mAppts.size()];		// Whole size
		int count = 0;
		System.out.println("----RetrieveAppts-----");
		System.out.println("mAppts.size() " + mAppts.size());
		for (int i = 0; i < mAppts.size(); i++){
			if (mAppts.containsKey(i)){
				Appt temp = mAppts.get(i);
				
				TimeSpan getTimeSpan = temp.TimeSpan();
				Timestamp sTime = getTimeSpan.StartTime();
				Timestamp eTime = getTimeSpan.EndTime();
				System.out.println("id = " + i + "title = " + temp.getTitle());
				System.out.println("sTime = " + sTime.toString() + " eTime = " + eTime.toString());
				System.out.println("d.sTime = " + d.StartTime().toString() + " d.eTime = " + d.EndTime().toString());
				if ((sTime.after(d.StartTime()) && eTime.before(d.EndTime())) || 
						(sTime.equals(d.StartTime()) && eTime.equals(d.EndTime()))){
					crash[count] = temp;
					count = count +1;
				}
			}
		}
		if (count > 0){
			Appt[] result = new Appt[count];
			for (int i = 0; i < crash.length; i++)
				result[i] = crash[i];
			return result;
		}
		//return result;
		return null;
	}

	@Override
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		// TODO Auto-generated method stub
		Appt[] crash = new Appt[0];
		int count = 0;
		for (int i = 0; i < mAppts.size(); i++){
			if (mAppts.containsKey(i)){
				Appt temp = mAppts.get(i);
				TimeSpan getTimeSpan = temp.TimeSpan();
				Timestamp sTime = getTimeSpan.StartTime();
				Timestamp eTime = getTimeSpan.EndTime();
				if ((sTime.after(time.StartTime()) && eTime.before(time.EndTime())) || 
						(sTime.equals(time.StartTime()) && eTime.equals(time.EndTime()))){
					if (temp.getAllPeople().contains(entity)){
						crash[count] = temp;
						count = count +1;
					}
				}
			}
		}
		if (count > 0){
			Appt[] result = new Appt[count];
			for (int i = 0; i < crash.length; i++)
				result[i] = crash[i];
			return result;
		}
		return null;
	}

	@Override
	public Appt RetrieveAppts(int joinApptID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void UpdateAppt(Appt appt) {
		// TODO Auto-generated method stub
		mAppts.put(appt.getID(), appt);
	}

	@Override
	public void RemoveAppt(Appt appt) {
		// TODO Auto-generated method stub
		mAppts.remove(appt.getID());
	}

	@Override
	public User getDefaultUser() {
		// TODO Auto-generated method stub
		return defaultUser;
	}

	@Override
	public void LoadApptFromXml() {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = new FileInputStream(filepath);
			ObjectInputStream is = new ObjectInputStream(fis);
			Object o;
			ArrayList<Appt> p = new ArrayList<Appt>();
			try {
				while ((o = is.readObject()) != null){
					Appt tempAppt = (Appt)o;
					p.add(tempAppt);
					int tempID = tempAppt.getID();
					System.out.println("tempID: " + tempID);
					mAppts.put(tempID, tempAppt);
				}
			}
			catch (EOFException exc)
			{
				is.close();
			}
			//setPosts(p);
			is.close();
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("Wait! There is something wrong.");
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}

	}

	@Override
	public void SaveApptFromXml() {
		// TODO Auto-generated method stub
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(filepath);
		} catch (IOException e){
			System.out.println("Wait! There is something wrong. I cannot find the file...");
		}
		try {
			ObjectOutputStream os = new ObjectOutputStream(fs);
			//os.writeObject(this);
			for (int i = 0; i < mAppts.size(); i++){
				os.writeObject(mAppts.get(i));
			}
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
