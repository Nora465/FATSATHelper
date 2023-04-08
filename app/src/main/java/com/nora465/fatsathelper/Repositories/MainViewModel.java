package com.nora465.fatsathelper.Repositories;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.nora465.fatsathelper.Models.CheckListSite;
import com.nora465.fatsathelper.Models.GenInfoSite;
import com.nora465.fatsathelper.Models.User;

import java.util.List;
import java.util.concurrent.Executor;

public class MainViewModel extends ViewModel {
	//REPOSITORIES
	private final UserDataRepo userDataSource; //Simple copy of DAO User
	private final SiteDataRepo siteDataSource; //Simple copy of DAO Sites
	private final Executor executor; //For execution in background
	@Nullable private LiveData<User> currentUser;
	
	//Constructeur
	public MainViewModel(UserDataRepo userDataSource, SiteDataRepo siteDataSource, Executor executor) {
		this.userDataSource = userDataSource;
		this.siteDataSource = siteDataSource;
		this.executor = executor;
	}
	
	//Initialize the connected user
	public void Init(String userTrig) {
		if (this.currentUser != null) {
			return;
		}
		this.currentUser = this.userDataSource.GetUser(userTrig);
	}
	
	// -------------
	// FOR USER REPO
	// -------------
	public LiveData<User> GetCurrentUser() { return this.currentUser;  }
	public LiveData<User> GetUser(String trigramme) { return this.userDataSource.GetUser(trigramme);  }
	public LiveData<List<User>> GetAll() { return this.userDataSource.GetAll(); }
	public void CreateUser(String firstName, String lastName, String trigramme) {
		executor.execute(() -> this.userDataSource.CreateUser(new User(firstName, lastName, trigramme)));
	}
	
	// -------------
	// FOR SITE REPO
	// -------------
	public LiveData<CheckListSite> GetCheckList(String siteName) { return this.siteDataSource.GetCheckList(siteName); }
	public LiveData<List<String>> GetSitesName() { return this.siteDataSource.GetSitesName(); }
	public LiveData<List<GenInfoSite>> GetSitesGenInfo() { return this.siteDataSource.GetSitesGenInfo(); }
	public void UpsertCheckList(CheckListSite checkListSite) {
		executor.execute(() -> this.siteDataSource.UpsertCheckList(checkListSite));
	}
	public void DeleteSite(String siteName) {
		executor.execute(() -> this.siteDataSource.DeleteSite(siteName));
	}
}
