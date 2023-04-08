package com.nora465.fatsathelper.Database.DAO;

import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.nora465.fatsathelper.Models.User;

import java.util.List;

@Dao
public interface UserDAO {
	@Query("SELECT * FROM Users ORDER BY id")
	LiveData<List<User>> GetAll() throws SQLiteException;
	
	@Query("SELECT * FROM Users WHERE trigramme = :trigramme")
	LiveData<User> GetUser(String trigramme) throws SQLiteException;
	
	@Insert()
	Long CreateUser(User user) throws SQLiteException;
}