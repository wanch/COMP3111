package hkust.cse.calendar.apptstorage;

import javax.swing.JOptionPane;

import hkust.cse.calendar.UserStorage.UserStorageControllerImpl;
import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.Location;
import hkust.cse.calendar.unit.TimeSpan;
import hkust.cse.calendar.unit.User;

/* This class is for managing the Appt Storage according to different actions */
public class ApptStorageControllerImpl {

	/* Remove the Appt from the storage */
	public final static int REMOVE = 1;

	/* Modify the Appt the storage */
	public final static int MODIFY = 2;

	/* Add a new Appt into the storage */
	public final static int NEW = 3;
	
	/*
	 * Add additional flags which you feel necessary
	 */
	
	/* The Appt storage */
	private ApptStorage mApptStorage;
	private UserStorageControllerImpl mUserInstance = UserStorageControllerImpl.getInstance();
	
	public Location[] getLocationList(){
		return mApptStorage.getLocationList();
	}
	
	public void setLocationList(Location[] locations){
		mApptStorage.setLocationList(locations);
		//System.out.println("In setLocationList" + locations.toString());
	}

	/* Create a new object of ApptStorageControllerImpl from an existing storage of Appt */
	public ApptStorageControllerImpl(ApptStorage storage) {
		mApptStorage = storage;
	}

	/* Retrieve the Appt's in the storage for a specific user within the specific time span */
	public Appt[] RetrieveAppts(User entity, TimeSpan time) {
		if (entity.equals(null))			// Test
			return mApptStorage.RetrieveAppts(time);
		return //mApptStorage.RetrieveAppts(entity, time);
				mApptStorage.RetrieveAppts(time);
	}

	// overload method to retrieve appointment with the given joint appointment id
	public Appt RetrieveAppts(int joinApptID) {
		return mApptStorage.RetrieveAppts(joinApptID);
	}
	
	/* Manage the Appt in the storage
	 * parameters: the Appt involved, the action to take on the Appt */
	public void ManageAppt(Appt appt, int action) {

		if (action == NEW) {				// Save the Appt into the storage if it is new and non-null
			if (appt == null)
				return;
			mApptStorage.SaveAppt(appt);
		} else if (action == MODIFY) {		// Update the Appt in the storage if it is modified and non-null
			if (appt == null)
				return;
			mApptStorage.UpdateAppt(appt);
		} else if (action == REMOVE) {		// Remove the Appt from the storage if it should be removed
			mApptStorage.RemoveAppt(appt);
		} 
	}

	/* Get the defaultUser of mApptStorage */
	public User getDefaultUser() {
		return mApptStorage.getDefaultUser();
	}
	
	public boolean checkOverlap(int id, Appt appt){
		/*for (int i = 0; i < mAppts.size(); i++){
			if (mAppts.containsKey(i)){
				Appt temp = mAppts.get(i);
				if (temp.TimeSpan().Overlap(time)){
					JOptionPane.showMessageDialog(null, appt.getTitle(),
			                "Error!", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
		}*/
		return false;
	}

	// method used to load appointment from xml record into hash map
	public void LoadFromXml(){
		mApptStorage.LoadApptFromXml();
		mUserInstance.LoadUserFromXml();
	}
	
	// method used to load appointment from xml record into hash map
	public void SaveFromXml(){
		mApptStorage.SaveApptFromXml();
		mUserInstance.SaveUserFromXml();
	}

	public Appt[] retrieveUserAppts(User mCurrUser, TimeSpan interval,
			Location locations) {
		// TODO Auto-generated method stub
		
		return null;
	}


}
