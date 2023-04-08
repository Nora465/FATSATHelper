package com.nora465.fatsathelper.Database;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.nora465.fatsathelper.Database.DAO.SitesDAO;
import com.nora465.fatsathelper.Database.DAO.UserDAO;
import com.nora465.fatsathelper.Models.CheckListSite;
import com.nora465.fatsathelper.Models.User;
import com.nora465.fatsathelper.Utils.SQLTypesConverter;

import java.util.concurrent.Executors;

@Database(
		entities = {User.class, CheckListSite.class},
		version = 1, //Increment if the structure of the DB changes
		autoMigrations = {
			@AutoMigration(from= 1, to= 2)
		}
)
@TypeConverters({SQLTypesConverter.class}) //Date/Long conversions for DB
public abstract class FATSATDatabase extends RoomDatabase {
	//Singleton (design pattern, its objective is to restrict the instantiation of a class to a single object)
	private static volatile FATSATDatabase INSTANCE;
	
	//DAO
	public abstract UserDAO userDAO();
	public abstract SitesDAO sitesDAO();
	
	//Instance (returns the database)
	//Create and return a new database if it does not exist
	//return the reference to the database if it already exists
	public static FATSATDatabase getInstance(Context context) {
		//TO DELETE DATABASE (if something change, and the DB content isn't important)
		//context.deleteDatabase("FATSATDatabase.db");
		if (INSTANCE == null) {
			synchronized (FATSATDatabase.class) {
				if (INSTANCE == null) {
					INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
						FATSATDatabase.class, "FATSATDatabase.db")
					.addCallback(prepopulateDatabase())
					.build();
				}
			}
		}
		return INSTANCE;
	}
	
	private static Callback prepopulateDatabase() {
		return new Callback() {
			@Override
			public void onCreate(@NonNull SupportSQLiteDatabase db) {
				super.onCreate(db);
				Executors.newSingleThreadExecutor().execute(() -> {
					try {
						INSTANCE.userDAO().CreateUser(new User("Patrick", "Dab", "PDA"));
						INSTANCE.userDAO().CreateUser(new User("ioioP", "ioioD", "ioio"));
					} catch (SQLiteConstraintException e) {
						Log.e("FATSATDatabase", "INSERT Failed (Constraint Error) : Trigramme probably already exist ?");
					}
				});
			}
		};
	}
}