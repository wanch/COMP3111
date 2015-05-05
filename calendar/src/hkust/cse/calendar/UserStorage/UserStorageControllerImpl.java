package hkust.cse.calendar.UserStorage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import hkust.cse.calendar.unit.Appt;
import hkust.cse.calendar.unit.User;


public class UserStorageControllerImpl {
	private static UserStorageControllerImpl instance;
	private UserStorage mUserStorage;
	
	public UserStorageControllerImpl(UserStorage storage) {
		mUserStorage = storage;
	}
	
    public static UserStorageControllerImpl getInstance() {
        if (instance == null ) {
            synchronized (UserStorageControllerImpl.class) {
                if (instance == null) {
                    instance = new UserStorageControllerImpl(new UserStorage().getInstance());
                }
            }
        }
 
        return instance;
    }
	
	public int getSize(){
		return mUserStorage.getSize();
	}
	
	public boolean cheakUserExist(String userName){
		return mUserStorage.cheakUserExist(userName);
	}
	
	public void addNewUser(User user){
		if (cheakUserExist(user.ID()) == false)
			mUserStorage.addNewUser(user);
	}
	
	public User retrieveUser(String userId, String password) {
		return mUserStorage.retrieveUser(userId, password);
	}
	
	public void LoadUserFromXml() {
		mUserStorage.LoadUserFromXml();
	}
	
	public void SaveUserFromXml() {
		mUserStorage.SaveUserFromXml();
	}
	
	

}
