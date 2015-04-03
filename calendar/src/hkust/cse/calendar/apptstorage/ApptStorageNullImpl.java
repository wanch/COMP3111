package hkust.cse.calendar.apptstorage;

import java.sql.Timestamp;
import java.util.HashMap;

import javax.swing.JOptionPane;

import UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

public class ApptStorageNullImpl extends ApptStorage {

	private User defaultUser = null;
	Location[] _locations;
	
	public ApptStorageNullImpl( User user )
	{
		defaultUser = user;
		//mAppts = new HashMap()<int, Appt>;
		mAppts = new HashMap<Integer, Appt>();
		//userController = UserStorageControllerImpl.getInstance();
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
		if (mAppts.isEmpty())
			appt.setID(0);
		else {
			int index = mAppts.size() +1;
			appt.setID(index);
			TimeSpan time = appt.TimeSpan();
			Appt[] overlapEvents = RetrieveAppts(time);
			
			for (int i = 0; i < overlapEvents.length; i++){
				if (overlapEvents[i].TimeSpan().Overlap(time)){
					JOptionPane.showMessageDialog(null, "Time overlap!",
							"Input Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
		mAppts.put(appt.getID(), appt);
	}

	@Override
	public Appt[] RetrieveAppts(TimeSpan d) {
		// TODO Auto-generated method stub
		Appt[] result = new Appt[0];
		int count = 0;
		for (int i = 0; i < mAppts.size(); i++){
			if (mAppts.containsKey(i)){
			Appt temp = mAppts.get(i);
			TimeSpan getTimeSpan = temp.TimeSpan();
			Timestamp sTime = getTimeSpan.StartTime();
			Timestamp eTime = getTimeSpan.EndTime();
			if ((sTime.after(d.StartTime()) && eTime.before(d.EndTime())) || 
					(sTime.equals(d.StartTime()) && eTime.equals(d.EndTime())))
				result[count] = temp;
			count = count +1;
			}
		}
		return result;
	}

	@Override
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		// TODO Auto-generated method stub
		Appt[] result = new Appt[0];
		int count = 0;
		for (int i = 0; i < mAppts.size(); i++){
			if (mAppts.containsKey(i)){
			Appt temp = mAppts.get(i);
			TimeSpan getTimeSpan = temp.TimeSpan();
			Timestamp sTime = getTimeSpan.StartTime();
			Timestamp eTime = getTimeSpan.EndTime();
			if ((sTime.after(time.StartTime()) && eTime.before(time.EndTime())) || 
					(sTime.equals(time.StartTime()) && eTime.equals(time.EndTime()))){
				if (temp.getAllPeople().contains(entity))
					result[count] = temp;
			}
			count = count +1;
			}
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

	}

}
