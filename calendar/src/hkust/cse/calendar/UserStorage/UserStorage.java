package hkust.cse.calendar.UserStorage;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.User;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class UserStorage {
	public HashMap<String, User> mUsers;		//a hashmap to save every thing to it, write to memory by the memory based storage implementation	
	//public User defaultUser;	//a user object, now is single user mode without login
	//public int mAssignedApptID;	//a global appointment ID for each appointment record
	private String filepath = "D:/userstorage.txt";
	
	protected static UserStorage instance = null;
	
	public UserStorage getInstance() {
		if(instance == null) {
			instance = new UserStorage();
		}
		return instance;
	}

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
	
	public void LoadUserFromXml() {
		// TODO Auto-generated method stub
		try {
			FileInputStream fis = new FileInputStream(filepath);
			ObjectInputStream is = new ObjectInputStream(fis);
			Object o;
			ArrayList<User> p = new ArrayList<User>();
			try {
				while ((o = is.readObject()) != null){
					User tempUser = (User)o;
					p.add(tempUser);
					String tempID = tempUser.ID();
					System.out.println("tempID: " + tempID);
					mUsers.put(tempID, tempUser);
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

	public void SaveUserFromXml() {
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
			for (int i = 0; i < mUsers.size(); i++){
				os.writeObject(mUsers.get(i));
			}
			os.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
