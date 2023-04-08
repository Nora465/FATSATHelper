package com.nora465.fatsathelper.Repositories;

import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.nora465.fatsathelper.Database.DAO.SitesDAO;
import com.nora465.fatsathelper.Models.CheckListSite;
import com.nora465.fatsathelper.Models.GenInfoSite;

import java.util.List;

public class SiteDataRepo {
	private final SitesDAO sitesDAO;
	//Constructor
	public SiteDataRepo(SitesDAO sitesDAO) {
		this.sitesDAO = sitesDAO;
	}
	
	public LiveData<CheckListSite> GetCheckList(String siteName) { return this.sitesDAO.GetCheckList(siteName); }
	public LiveData<List<String>> GetSitesName() { return this.sitesDAO.GetSitesName(); }
	public LiveData<List<GenInfoSite>> GetSitesGenInfo() { return this.sitesDAO.GetSitesGenInfo(); }
	public void UpsertCheckList(CheckListSite checkList) {
		try {
			this.sitesDAO.UpsertCheckList(checkList);
		} catch (SQLiteConstraintException e) {
			Log.e("SiteDataRepo", "UPSERT Failed (Constraint Error)");
		} catch (SQLiteException e) {
			Log.e("SiteDataRepo", "UPSERT Failed (SQLiteException Generic Exception)");
		}
	}
	public void DeleteSite(String siteName) {
		try {
			this.sitesDAO.DeleteSite(siteName);
		} catch (SQLiteConstraintException e) {
			Log.e("SiteDataRepo", "DELETE Failed (Constraint Error)");
		} catch (SQLiteException e) {
			Log.e("SiteDataRepo", "DELETE Failed (SQLiteException Generic Exception)");
		}
	}
}
