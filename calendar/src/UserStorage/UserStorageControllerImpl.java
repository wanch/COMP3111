package UserStorage;

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
}
