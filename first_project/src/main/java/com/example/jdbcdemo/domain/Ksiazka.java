package com.example.jdbcdemo.domain;

public class Ksiazka {
	
	private long id;
	
	private String Tytul;
	private String Kategoria;
	private long AutorId;
	
	public Ksiazka() {
	}
	
	public Ksiazka(String tytul, String kat, long autorId) {
		super();
		this.Tytul = tytul;
		this.Kategoria = kat;
		this.AutorId = autorId;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTytul() {
		return Tytul;
	}
	
	public void setTytul(String tytul) {
		this.Tytul = tytul;
	}

	public String getKategoria() {
		return Kategoria;
	}
	
	public void setKategoria(String kat) {
		this.Kategoria = kat;
	}
	
	public long getAutorId() {
		return AutorId;
	}
	
	public void setAutorId(int autorId) {
		this.AutorId = autorId;
	}
}
