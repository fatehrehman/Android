package com.example.employeedirectory;

import java.util.ArrayList;

import android.content.Context;

public class Employee {

	private int _id;
	private String name;
	private String profession;
	private String experience;
	private EmployeeDB empdb;

	public Employee(int id, String name, String profession, String experience) {
		this._id = id;
		this.name = name;
		this.profession = profession;
		this.experience = experience;
	}

	public Employee(String name, String profession, String experience) {
		this.name = name;
		this.profession = profession;
		this.experience = experience;
	}

	public String getId() {
		return String.valueOf(this._id);
	}

	public String getName() {
		return this.name;
	}

	public String getProfession() {
		return this.profession;
	}

	public String getExperience() {
		return this.experience;
	}

	public String getProfileImage(Context cont) {
		empdb = new EmployeeDB(cont);
		return empdb.getProfileImage(this.getId(), "1");
	}

	public ArrayList<String> getImages(Context cont) {
		empdb = new EmployeeDB(cont);
		return empdb.getImages(this.getId(), "0");
	}
}