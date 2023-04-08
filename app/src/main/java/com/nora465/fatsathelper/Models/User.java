package com.nora465.fatsathelper.Models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
	tableName = "Users",
	indices = { @Index(value = "trigramme", unique = true) }
)
public class User {
	@PrimaryKey(autoGenerate = true)
	public long id;
	public String firstName;
	public String lastName;
	@NonNull public String trigramme;
	//TODO Ajouter Icone ? (à ajouter plus tard si besoin)
	
	//Constructor
	public User(String firstName, String lastName, String trigramme) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.trigramme = trigramme;
	}
}
