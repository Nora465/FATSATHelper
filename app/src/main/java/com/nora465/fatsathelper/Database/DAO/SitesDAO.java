package com.nora465.fatsathelper.Database.DAO;

import android.database.sqlite.SQLiteException;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Upsert;

import com.nora465.fatsathelper.Models.CheckListSite;
import com.nora465.fatsathelper.Models.GenInfoSite;

import java.util.List;

@Dao
public interface SitesDAO {
	//==== QUERIES
	//Get a checklist by its site name
	@Query("SELECT * FROM check_list WHERE siteName = :siteName")
	LiveData<CheckListSite> GetCheckList(String siteName) throws SQLiteException;
	
	//Get the name of all sites
	@Query("SELECT siteName FROM check_list ORDER BY dateOfCreation") //TODO dans le ORDER BY, on doit pouvoir choisir la façon de trier (pour plus tard)
	LiveData<List<String>> GetSitesName() throws SQLiteException;
	
	//Get the general Infos of all sites (Room will rewrite this query to query only GenInfoSite attributes)
	@RewriteQueriesToDropUnusedColumns
	@Query("SELECT * FROM check_list ORDER BY dateOfCreation")
	LiveData<List<GenInfoSite>> GetSitesGenInfo() throws  SQLiteException;
	
	//==== INSERTS OR UPDATES
	@Upsert //Update or Insert
	void UpsertCheckList(CheckListSite checkList) throws SQLiteException;

	@Query("DELETE FROM check_list WHERE siteName = :siteName")
	void DeleteSite(String siteName) throws SQLiteException;
}