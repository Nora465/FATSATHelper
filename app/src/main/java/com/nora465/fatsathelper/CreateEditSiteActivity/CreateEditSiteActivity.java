package com.nora465.fatsathelper.CreateEditSiteActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.nora465.fatsathelper.Models.CheckListSite;
import com.nora465.fatsathelper.R;
import com.nora465.fatsathelper.Repositories.MainViewModel;
import com.nora465.fatsathelper.Repositories.ViewModelFactory;

public class CreateEditSiteActivity extends AppCompatActivity {
	private MainViewModel mainViewModel;
	private static final String USER_TRIG = "PDA"; //TODO faire en sorte de faire venir cette info de l'activité main (et que ça soit stocké)
	private CheckListSite checkListSite;
	private ArrayAdapter<CharSequence> spinnerAdaptor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_entry_activity);
		
		//Init ViewModel
		this.mainViewModel = new ViewModelProvider(this, ViewModelFactory.getInstance(this)).get(MainViewModel.class);
		this.mainViewModel.Init(USER_TRIG);
		
		//Get data from MainActivity
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			TextView createBtn = findViewById(R.id.CreateEditBtn);
			if (extras.getBoolean("Edit") && extras.getString("SiteName") != null) {
				//Edit Mode

				//Disable all views until we get the data from DB
				findViewById(R.id.TextSiteName).setEnabled(false);
				findViewById(R.id.siteTypeSelector).setEnabled(false);
				findViewById(R.id.textSiteType).setEnabled(false);
				
				//Load Checklist from DB
				mainViewModel.GetCheckList(extras.getString("SiteName"))
					//TODO on peut peut etre utiliser GenInfo, mais vérifier si on peut Upsert avec uniquement l'ID ou les GenInfo
					.observe(this, this::EditCheckListViews);
				
				createBtn.setText("Modifier le site");
			} else {
				//Create Mode
				this.checkListSite = new CheckListSite("", "");
				createBtn.setText("Créer le site");
			}
		}
		InitViews(); //Init Views on Activity
	}
	
	//event Click : Button "Create or Edit"
	public void CreateSiteButtonClicked() {
		EditText textSiteName = findViewById(R.id.TextSiteName);
		Spinner spinner = findViewById(R.id.siteTypeSelector);
		
		this.checkListSite.siteName = textSiteName.getText().toString();
		this.checkListSite.siteType = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();
		
		this.mainViewModel.UpsertCheckList(this.checkListSite);
		finish(); //Close Activity
	}
	
	//==============
	//===== UI =====
	//==============
	//Initialize "symbols" (=Views) of the activity
	private void InitViews() {
		ConfigureTypeSelector();
		
		findViewById(R.id.CreateEditBtn).setOnClickListener(view -> CreateSiteButtonClicked());
	}
	
	//Configurer the spinner (Combobox, to select the "type")
	private void ConfigureTypeSelector() {
		this.spinnerAdaptor = ArrayAdapter.createFromResource(this, R.array.TypeSiteArray, android.R.layout.simple_spinner_item);
		this.spinnerAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		Spinner spinner = findViewById(R.id.siteTypeSelector);
		spinner.setAdapter(this.spinnerAdaptor);
	}
	
	//Edit Views Text with DB data
	private void EditCheckListViews(CheckListSite checkList) {
		if (checkList != null) {
			this.checkListSite = checkList;
			
			//Enable and fill Views
			EditText textSiteName = findViewById(R.id.TextSiteName);
			Spinner typeSelector = findViewById(R.id.siteTypeSelector);
			EditText siteType = findViewById(R.id.textSiteType);
			
			textSiteName.setEnabled(true);
			typeSelector.setEnabled(true);
			siteType.setEnabled(true);
			
			textSiteName.setText(this.checkListSite.siteName);
			typeSelector.setSelection(this.spinnerAdaptor.getPosition(checkList.siteType));
		}
	}
}