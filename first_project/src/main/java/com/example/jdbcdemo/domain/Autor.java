package com.example.jdbcdemo.domain;

public class Autor {
	private long id;
	
	private String AutorPer; /*personalia autora*/
	private int Wzrost;
	private String Obywatelstwo;
	
	public Autor(){}
	public Autor(String pers, int wzrost, String obyw){
		this.AutorPer = pers;
		this.Wzrost = wzrost;
		this.Obywatelstwo = obyw;
	}
	
	public long getId(){
		return this.id;
	}
	public String getAutorPer(){
		return this.AutorPer;
	}
	
	public int getWzrost(){
		return this.Wzrost;
	}
	
	public String getObywatelstwo(){
		return this.Obywatelstwo;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setAutorPers(String pers){
		this.AutorPer = pers;
	}

	public void setWzrost(int wzrost){
		this.Wzrost = wzrost;
	}

	public void setObywatelstwo(String obyw){
		this.Obywatelstwo = obyw;
	}
}
