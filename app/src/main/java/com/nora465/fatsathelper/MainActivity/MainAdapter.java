package com.nora465.fatsathelper.MainActivity;
//explication sur RecyclerView.Adapter : https://openclassrooms.com/fr/courses/4568576-recuperez-et-affichez-des-donnees-distantes/4893781-implementez-votre-premiere-recyclerview#/id/r-4909503

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nora465.fatsathelper.Models.GenInfoSite;
import com.nora465.fatsathelper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//Allows to bind a ViewHolder to a list of data
public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {
	private List<GenInfoSite> sitesList;
	
	//Interface : allows to "transfer" data between places (here, between MainAdapter and MainActivity)
	public interface ClickListener {
		void onItemClicked(String siteNameClicked);
		void onEditClicked(String siteNameClicked);
		void onDeleteClicked(String siteNameClicked);
	}
	private final ClickListener listener;
	
	//Constructor
	public MainAdapter(ClickListener listener) {
		this.sitesList = new ArrayList<>();
		this.listener = listener;
	}
	
	@NonNull
	@Override //From RecyclerView.Adapter
	//Creation of ViewHolder and inflating of layout XML
	//this is triggered at start of mainActivity, to load first lines of RecyclerView
	public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_single_line, parent, false));
	}
	
	@Override //From RecyclerView.Adapter
	//Update the appearance of visible lines
	//This is triggered when a new line is displayed
	public void onBindViewHolder(@NonNull MainViewHolder holder, int position) {
		GenInfoSite currentGenInfo = sitesList.get(position);
		
		holder.siteName.setText(currentGenInfo.siteName);
		if (Objects.equals(currentGenInfo.siteType, "EOL")) {
			holder.typeImg.setImageResource(R.drawable.ic_wind_turbine);
		} else if (Objects.equals(currentGenInfo.siteType, "PV")) {
			holder.typeImg.setImageResource(R.drawable.ic_solar_panel);
		}
		
		//From View.OnClickListener
		holder.cardView.setOnClickListener(view ->
			this.listener.onItemClicked(sitesList.get(holder.getAbsoluteAdapterPosition()).siteName));
		
		holder.editButton.setOnClickListener(view ->
			this.listener.onEditClicked(sitesList.get(holder.getAbsoluteAdapterPosition()).siteName));
		
		holder.deleteButton.setOnClickListener(view ->
			this.listener.onDeleteClicked(sitesList.get(holder.getAbsoluteAdapterPosition()).siteName));

		//Support for long click, if needed
		/*holder.cardView.setOnLongClickListener(view -> {
			this.listener.onItemLongClicked(sitesList.get(holder.getAbsoluteAdapterPosition()).siteName);
			return false;
		});*/
	}
	
	@Override //From RecyclerView.Adapter
	//Return the number of items that can be selected
	public int getItemCount() {
		return this.sitesList.size();
	}
	
	//Update list of sites
	public void updateSites(List<GenInfoSite> ListInfoSite) {
		this.sitesList = ListInfoSite;
		this.notifyDataSetChanged();
	}
}
