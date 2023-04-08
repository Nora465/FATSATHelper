package com.nora465.fatsathelper.Models;

import java.util.Date;

public class GenInfoSite {
	//GenInfo (gen_info)
	public long id;
	public String siteName; //Name of site (UNIQUE)
	public Date dateOfCreation; //Date of the creation of this site here
	public String siteType; //Type of the site (Wind(EOL)/Solar(PV))
	public String coordsGPS; //Coords PDL
	public String localFolder; //For pictures
	public boolean FATok; //All FAT Data are validated
	public boolean SATok; //All SAT Data are validated
}
