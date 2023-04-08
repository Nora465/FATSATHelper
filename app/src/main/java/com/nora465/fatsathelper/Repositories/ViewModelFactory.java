package com.nora465.fatsathelper.Repositories;

//Factory : allows to group the creation of ViewModels
//When you have several ViewModels, it's clearer

import android.content.Context;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.nora465.fatsathelper.Database.FATSATDatabase;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {
	private final    UserDataRepo userDataSource;
	private final SiteDataRepo siteDataSource;
	private final Executor executor;
	private static volatile ViewModelFactory factory;
	
	//Constructor
	public ViewModelFactory(Context context) {
		FATSATDatabase database = FATSATDatabase.getInstance(context);
		this.userDataSource = new UserDataRepo(database.userDAO());
		this.siteDataSource = new SiteDataRepo(database.sitesDAO());
		this.executor = Executors.newSingleThreadExecutor();
	}
	
	public static ViewModelFactory getInstance(Context context) {
		if (factory == null) {
			synchronized (ViewModelFactory.class) {
				if (factory == null) {
					factory = new ViewModelFactory(context);
				}
			}
		}
		return factory;
	}
	
	@Override
	@NotNull
	public <T extends ViewModel> T create(Class<T> modelClass) {
		if (modelClass.isAssignableFrom(MainViewModel.class)) {
			try {
				return modelClass.getConstructor(UserDataRepo.class, SiteDataRepo.class, Executor.class).newInstance(this.userDataSource, this.siteDataSource, this.executor);
			} catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
		//-> If you create other ViewModels, declare them here
		throw new IllegalArgumentException("Unknown ViewModel class");
	}
}
