package com.example.jdbcdemo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Ksiazka;
import com.example.jdbcdemo.domain.Autor;

public class KsiazkaManagerTest {
	
	//tworze zmienne globalne uzywane w testach
	KsiazkaManager ksiazkaManager = new KsiazkaManager();
	AutorManager autorManager = new AutorManager(); 
	
	private final static String TYTUL_1 = "Wiedzmin 1";
	private final static String KAT_1 = "Fantasy";
	private final static long AUTORID_1 = 1;
	
	private final static String TYTUL_2 = "Niebo jest wszedzie";
	private final static String KAT_2 = "Obyczajowa";
	private final static long AUTORID_2 = 2;
	
		
	@Test
	public void checkConnection(){ //sprawdam polaczenie do bazy
		assertNotNull(ksiazkaManager.getConnection());
	}
	
	public void przedTestem(){//dodaje autorow do bazy danych, żeby książka działała
		Autor Sapkowski = new Autor("Andrzej Sapkowski", 175, "polskie");
		Autor Nelson = new Autor("Jandy Nelson", 168, "amerykanskie");
		autorManager.addAutor(Sapkowski);
		autorManager.addAutor(Nelson);
	}
	
	@Test
	public void checkAddKsiazka(){ //sprawdza,czy ksiazka zostala dodana - przypisanie x do y; dodanie do tabeli x, 
		przedTestem(); //dodaje autorow 

		Ksiazka ksiazka = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1); //dodaje ksiazke do autora 
		
		ksiazkaManager.clearKsiazkas(); //czyszcze baze danych z ksiazek
		assertEquals(1,ksiazkaManager.addKsiazka(ksiazka)); //sprawdzam,czy metoda wykonala sie poprawnie
		
		List<Ksiazka> ksiazkas = ksiazkaManager.getAllKsiazkas(); //pobiera z bazy danych wszystkie ksiazki i wrzuca je do listy
		Ksiazka ksiazkaRetrieved = ksiazkas.get(0); //pobierz z bazy danych ksiazke spod indeksu 0
		
		assertEquals(TYTUL_1, ksiazkaRetrieved.getTytul());
		assertEquals(KAT_1, ksiazkaRetrieved.getKategoria());
		assertEquals(AUTORID_1, ksiazkaRetrieved.getAutorId());
		
	}
	
	@Test 
	public void checkDeleteKsiazka(){ //sprawdza czy usunięto książkę - usunięcie x z y
		ksiazkaManager.clearKsiazkas();
		
		Ksiazka ks = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		assertEquals(1, ksiazkaManager.addKsiazka(ks));
		
		assertEquals(1, ksiazkaManager.deleteKsiazka(TYTUL_1, AUTORID_1)); //trzeba dokladnie sprecyzowac czy ksiazka o danym tytule i autorze ma byc usunieta
		
		assertEquals(null, ksiazkaManager.getKsiazka(TYTUL_1));//sprawdza czy nie ma ksiazki o tyt1 w bazie
	}
	
	@Test
	public void checkModifyKsiazka(){
		ksiazkaManager.clearKsiazkas();
		Ksiazka jeden = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		ksiazkaManager.addKsiazka(jeden);		
		assertEquals(TYTUL_1, jeden.getTytul());
		ksiazkaManager.modifyKsiazka(TYTUL_2, AUTORID_1);
		Ksiazka jedenmod = ksiazkaManager.getKsiazka(TYTUL_2);
		assertEquals(TYTUL_2, jedenmod.getTytul());
		assertEquals(KAT_1, jedenmod.getKategoria());
		assertEquals(AUTORID_1, jedenmod.getAutorId());
	}
	
	@Test
	public void checkMamKsiazke(){
		Ksiazka wiedzmin = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		Ksiazka niebo = new Ksiazka(TYTUL_2, KAT_2, AUTORID_2);
		ksiazkaManager.clearKsiazkas();
		assertEquals(null, ksiazkaManager.getKsiazka(TYTUL_1));
		ksiazkaManager.addKsiazka(wiedzmin);
		ksiazkaManager.addKsiazka(niebo);		
		assertEquals(TYTUL_1, ksiazkaManager.getKsiazka(TYTUL_1).getTytul());
		assertEquals(KAT_1, ksiazkaManager.getKsiazka(TYTUL_1).getKategoria());
		assertEquals(AUTORID_1, ksiazkaManager.getKsiazka(TYTUL_1).getAutorId());		
		assertEquals(TYTUL_2, ksiazkaManager.getKsiazka(TYTUL_2).getTytul());
		assertEquals(KAT_2, ksiazkaManager.getKsiazka(TYTUL_2).getKategoria());
		assertEquals(AUTORID_2, ksiazkaManager.getKsiazka(TYTUL_2).getAutorId());
	}
	
	@Test
	public void getKsiazkasByIdTest() { //pobieramy wszystkie książki danego autora - pobranie x należących do y
		autorManager.vanishAutors();
		przedTestem();
		Autor a = autorManager.getAutor("Andrzej Sapkowski"); //pobieram z bazy autora 
		
		assertEquals(a.getAutorPer(), "Andrzej Sapkowski"); //sprawdza czy pobrany autor na pewno nazywa sie Sapkowski
		List<Ksiazka> ks = ksiazkaManager.getKsiazkasById(a.getId());// sciaga wszystkie ksiazki autora po "getid",zeby udowodnic w tescie, ze autor nie ma ksiazek,bo ich nie dodalam
		assertEquals(0, ks.size()); //sprawdza czy dlugosc listy jest 0
		Ksiazka jeden = new Ksiazka("Wiedzmin 1", "fantasy", a.getId()); //tworze ksiazke 
		ksiazkaManager.addKsiazka(jeden); //dodaje ksiazke do bazy
		ks = ksiazkaManager.getKsiazkasById(a.getId()); //sciagam ksiazki autora po 'getid'
		assertEquals(1, ks.size());//sprwdzam,czy dl. lsty ks jest 1
		
	}
	

}

