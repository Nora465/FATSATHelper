package com.nora465.fatsathelper.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(
	tableName = "check_list",
	indices = { @Index(value = "siteName", unique = true) }
)
public class CheckListSite {
	@PrimaryKey(autoGenerate = true)
	public long id;
	
	//GenInfo
	@NonNull public String siteName; //Name of site TODO ajouter un tag unique ici ?
	public Date dateOfCreation; //Date of the creation of this site here
	@NonNull public String siteType; //Type of the site (Wind(EOL)/Solar(PV))
	public String coordsGPS; //Coords PDL
	public String localFolder; //For pictures
	public boolean FATok; //All FAT Data are validated
	public boolean SATok; //All SAT Data are validated
	
	//CheckList
	public String picsAPI;
	public String picsSNArm;
	public String picsArm;
	public String picsSIMSMS;
	public boolean cablETIC1;
	//...
	
	
	public CheckListSite(@NonNull String siteName, @NonNull String siteType) {
		this.siteName = siteName;
		this.siteType = siteType;
	}
}
