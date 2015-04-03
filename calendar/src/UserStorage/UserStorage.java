package UserStorage;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.User;

import java.util.HashMap;

public class UserStorage {
	public HashMap<String, User> mUsers;		//a hashmap to save every thing to it, write to memory by the memory based storage implementation	
	//public User defaultUser;	//a user object, now is single user mode without login
	//public int mAssignedApptID;	//a global appointment ID for each appointment record

	public UserStorage() {	//default constructor
		mUsers = new HashMap<String, User>();
	}
	
	public int getSize(){
		return mUsers.size();
	}
	
	public boolean cheakUserExist(String userName){
		if (mUsers.containsKey(userName))
			return true;
		else return false;
	}
	
	public void addNewUser(User user){
		if (cheakUserExist(user.ID()) == false)
			mUsers.put(user.ID(), user);
	}
	
	public User getUser(String userName) {
		if (cheakUserExist(userName))
			return mUsers.get(userName);
		else return null;
	}
	
	public User retrieveUser(String id, String password) {
		if(cheakUserExist(id) == false) {
			return null;
		}
		else if(getUser(id).Password().equals(password)) {
			return getUser(id);
		}
		else return null;
	}
}
