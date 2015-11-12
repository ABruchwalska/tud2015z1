package com.example.jdbcdemo.service;
import static org.junit.Assert.*;


import org.junit.Test;

import com.example.jdbcdemo.domain.Autor;
import com.example.jdbcdemo.service.AutorManager;

public class AutorManagerTest {
	//tworze zmienne globalne uzywane w testach
	AutorManager autorManager = new AutorManager(); 
	private final static String AUTORPER_1 = "Andrzej Sapkowski";
	private final static int WZROST_1 = 175;
	private final static String OBYWATELSTWO_1 = "polskie";
	
	private final static String AUTORPER_2 = "Wislawa Szymborska";
	private final static int WZROST_2 = 155;
	private final static String OBYWATELSTWO_2 = "polskie";
	
	@Test
	public void getConnectionTest(){ //Sprawdza połączenie z bazą
		assertNotNull(autorManager.getConnection());
	}
	
	@Test
	public void addAutorTest(){ // Tworzenie nowego autora ; dodanie do tabeli y
		Autor autor = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		
		autorManager.vanishAutors(); // czyści bazę, żeby uniknąć konfliktu z innymi testami. np. duplikat dodwania osoby itp.
		assertEquals(1,autorManager.addAutor(autor)); //jeżeli doda do bazy,to wypluwa 1.
		
		Autor autorRetrieved = autorManager.getAutor(AUTORPER_1); //pobierz z bazy autora spod "AUTORPER_1"
		
		assertEquals(AUTORPER_1, autorRetrieved.getAutorPer()); //bierze wartosci,porównuje i wypluwa w metodzie personalia danego autora
		assertEquals(WZROST_1, autorRetrieved.getWzrost()); // j.w. tylko dla wzrostu
		assertEquals(OBYWATELSTWO_1, autorRetrieved.getObywatelstwo()); //j.w. dla obyw.
	}
	
	@Test
	public void modifyAutorTest(){ // sprawdza,czy f-kcja modyfikuj autora działa poprawnie
		autorManager.vanishAutors(); // usuwam, żeby nie było konfliktu z innymi testami
		
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1); //tworzy instancje klasy S. na podst. podanych danych
		autorManager.addAutor(Sapkowski); 
		
		assertEquals(AUTORPER_1, Sapkowski.getAutorPer());
		autorManager.modifyAutor(AUTORPER_1, OBYWATELSTWO_2); //odpalamy metode modify... i zmieniamy AUTORPER1 obywatelstwo na OBYW_2
		Autor Sapkowski2 = autorManager.getAutor(AUTORPER_1);//pobiera autora,ktory został zmodyfikowany
		assertEquals(AUTORPER_1, Sapkowski2.getAutorPer()); //sprawdza jak wyzej,czy dane sie zgadzaja
		assertEquals(WZROST_1, Sapkowski2.getWzrost());
		assertEquals(OBYWATELSTWO_2, Sapkowski2.getObywatelstwo());
	}
	
	@Test
	public void deleteAutorTest(){ //sprawdza, czy zostal usuniety
		autorManager.vanishAutors();
		
		Autor a = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);//do zmiennej 'a' ktora jest typu autor tworze nowego autora ktory 'zawiera rzeczy z nawiasow'
		autorManager.addAutor(a); //dodajemy do bazy autora 'a'
		
		autorManager.deleteAutor(AUTORPER_1); //usuwamy autora o personaliach AUTORPER_1
		
		assertEquals(null, autorManager.getAutor(AUTORPER_1)); //sprawdza czy poprawnie usunieto autorper1 z linijki wyzej
	}
		
	@Test
	public void getAutorTest(){ //sprawdza, czy metoda 'getautor' dziala
		autorManager.vanishAutors(); //czyscmy autorow
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		Autor Szymborska = new Autor(AUTORPER_2, WZROST_2, OBYWATELSTWO_2);
		assertEquals(null, autorManager.getAutor(AUTORPER_1)); //sprawdza czy w bazie jest autor o personliach1, jezeli go nie ma,to daje null
		autorManager.addAutor(Sapkowski); //dodajemy "Sapkowskiego" do bazy
		autorManager.addAutor(Szymborska);// j.w. dla "Szymbkorskiej"
		
		assertEquals(AUTORPER_1, autorManager.getAutor(AUTORPER_1).getAutorPer());// pobiera z bazy autora i sprawdza,czy dane sa te same
		assertEquals(WZROST_1, autorManager.getAutor(AUTORPER_1).getWzrost());
		assertEquals(OBYWATELSTWO_1, autorManager.getAutor(AUTORPER_1).getObywatelstwo());
		
		assertEquals(AUTORPER_2, autorManager.getAutor(AUTORPER_2).getAutorPer());
		assertEquals(WZROST_2, autorManager.getAutor(AUTORPER_2).getWzrost());
		assertEquals(OBYWATELSTWO_2, autorManager.getAutor(AUTORPER_2).getObywatelstwo());

	}
	
	@Test
	public void getAllAutorsTest(){ //sprawdza czy dziala getautor; zwraca liste autorow - pobranie wszystkich y
		autorManager.vanishAutors();
		assertEquals(0, autorManager.getAllAutors().size());// sprawdza, czy długość listy jest 0
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		Autor Szymborska = new Autor(AUTORPER_2, WZROST_2, OBYWATELSTWO_2);
		autorManager.addAutor(Sapkowski);//dodaje autorow do bazy
		autorManager.addAutor(Szymborska);
		assertEquals(2, autorManager.getAllAutors().size());
		assertEquals(AUTORPER_1, autorManager.getAllAutors().get(0).getAutorPer());// sprawdza czy pod indeksem'0' jest autor o pers.1
		assertEquals(AUTORPER_2, autorManager.getAllAutors().get(1).getAutorPer());
	}
	
}
