package com.nora465.fatsathelper.MainActivity;
//Explications : https://openclassrooms.com/fr/courses/4568576-recuperez-et-affichez-des-donnees-distantes/4893781-implementez-votre-premiere-recyclerview#/id/r-4909499

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nora465.fatsathelper.R;

//Allows to visually represent an element of the data list in the RecyclerView (One Line)
public class MainViewHolder extends RecyclerView.ViewHolder {
	public TextView siteName;
	public CardView cardView; //to detect clicks
	public ImageButton editButton;
	public ImageButton deleteButton;
	public ImageView typeImg;
	
	public MainViewHolder(@NonNull View itemView) {
		super(itemView);
		
		//Here, must declare all views that the RecyclerView will modify or access
		this.siteName = itemView.findViewById(R.id.SglLineSiteName);
		this.cardView = itemView.findViewById(R.id.SglLineContainer);
		this.editButton = itemView.findViewById(R.id.SglLineEditBtn);
		this.deleteButton = itemView.findViewById(R.id.SglLineDeleteBtn);
		this.typeImg = itemView.findViewById(R.id.SglLineTypeImg);
	}
}
