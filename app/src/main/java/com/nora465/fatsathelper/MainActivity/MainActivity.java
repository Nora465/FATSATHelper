package com.nora465.fatsathelper.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nora465.fatsathelper.CreateEditSiteActivity.CreateEditSiteActivity;
import com.nora465.fatsathelper.Models.GenInfoSite;
import com.nora465.fatsathelper.Models.User;
import com.nora465.fatsathelper.R;
import com.nora465.fatsathelper.Repositories.MainViewModel;
import com.nora465.fatsathelper.Repositories.ViewModelFactory;

import java.util.List;

//Tutorial RecyclerView : https://www.youtube.com/watch?v=DlaSiftrWeA
//Tutorial RecyclerView OnClick : https://www.youtube.com/watch?v=-gs1hllisG4
//Voir explication sur RecyclerView : https://www.grokkingandroid.com/first-glance-androids-recyclerview/
public class MainActivity extends AppCompatActivity implements MainAdapter.ClickListener {
	//Data
	private MainViewModel mainViewModel;
	private static final String USER_TRIG = "PDA";
	private MainAdapter mainAdapter;
	RecyclerView recyclerView;
	
	//TODO je pourrai utiliser "view binding" pour récupérer une variable qui contient tous mes Views
	@Override //From FragmentActivity
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		//Configuration du ViewModel
		this.mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainViewModel.class);
		this.mainViewModel.Init(USER_TRIG);
		
		InitViews();
	}
	
	@Override //From MainAdapter.SelectListener (Interface)
	public void onItemClicked(@NonNull String siteNameClicked) { //Onclick tutorial
		Toast.makeText(this, siteNameClicked, Toast.LENGTH_SHORT).show();
		//TODO open editChecklistActivity
	}
	
	@Override //From MainAdapter.SelectListener (Interface)
	public void onEditClicked(@NonNull String siteNameClicked) {
		this.OpenEditSiteActivity(true, siteNameClicked);
	}
	
	@Override //From MainAdapter.SelectListener (Interface)
	public void onDeleteClicked(@NonNull String siteNameClicked) {
		//TODO add a confirmation popup
		//this.mainViewModel.DeleteSite(siteNameClicked);
		Log.d("MainActivity", "Clicked delete button, but it has been deactivated");
	}

	//==============
	//===== UI =====
	//==============
	//Initialize "symbols" (=Views) of the activity
	private void InitViews() {
		//--- RecyclerView
		this.recyclerView = findViewById(R.id.mainRecyclerView);
		//LayoutManager : Permet de positionner correctement l'ensemble des données de la liste.
		//TODO j'utilise un GridLayoutManager, vérifier la différence
		this.recyclerView.setLayoutManager(new LinearLayoutManager(this));
		this.mainAdapter = new MainAdapter(this);
		this.recyclerView.setAdapter(this.mainAdapter);
		
		//--- Get Data (Current User and list of sites) From ViewModel
		this.mainViewModel.GetSitesGenInfo().observe(this, this::UpdateSiteList);
		this.mainViewModel.GetCurrentUser().observe(this, this::UpdateUserView);
		
		//--- Click Listeners
		findViewById(R.id.mainAddButton).setOnClickListener(view -> this.OpenEditSiteActivity(false));
		
	}
	
	//Update User View
	private void UpdateUserView(User user) {
		if (user != null) {
			TextView userText = findViewById(R.id.textUserTrig);
			userText.setText("User : " + user.trigramme);
		}
	}
	
	//Update list of sites
	private void UpdateSiteList(List<GenInfoSite> sitesList) {
		if (sitesList != null) this.mainAdapter.updateSites(sitesList);
	}
	
	//Open CreateEditSite Activity
	private void OpenEditSiteActivity(@NonNull Boolean editMode, String... siteName) {
		Intent intent = new Intent(MainActivity.this, CreateEditSiteActivity.class);
		
		//Send datas to, and start the Activity
		intent.putExtra("Edit", editMode); //Create Mode
		if (editMode) intent.putExtra("SiteName", siteName[0]);
		startActivity(intent);
	}
}