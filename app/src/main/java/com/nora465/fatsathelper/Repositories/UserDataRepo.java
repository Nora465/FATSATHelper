package com.nora465.fatsathelper.Repositories;

import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.nora465.fatsathelper.Database.DAO.UserDAO;
import com.nora465.fatsathelper.Models.User;

import java.util.List;

//C'est une recopie du DAO User
public class UserDataRepo {
	private final UserDAO userDAO;
	//Constuctor
	public UserDataRepo(UserDAO userDao) {
		this.userDAO = userDao;
	}
	
	public LiveData<List<User>> GetAll() { return this.userDAO.GetAll(); }
	public LiveData<User> GetUser(String userTrig) { return this.userDAO.GetUser(userTrig); }
	public Long CreateUser(User user) {
		try {
			return userDAO.CreateUser(user);
		} catch (SQLiteConstraintException e) {
			Log.e("UserDataRepo", "INSERT Failed (Constraint Error) : Trigramme probably already exist ?");
			return -1L; //-1 (Long type)
		}
	}
}
