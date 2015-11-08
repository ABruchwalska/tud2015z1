package com.example.jdbcdemo.service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Ksiazka;
import com.example.jdbcdemo.domain.Autor;

public class KsiazkaManagerTest {
	
	
	KsiazkaManager ksiazkaManager = new KsiazkaManager();
	AutorManager autorManager = new AutorManager(); 
	
	private final static String TYTUL_1 = "Wiedzmin 1";
	private final static String KAT_1 = "Fantasy";
	private final static long AUTORID_1 = 1;
	
	private final static String TYTUL_2 = "Niebo jest wszedzie";
	private final static String KAT_2 = "Obyczajowa";
	private final static long AUTORID_2 = 2;
	
	Autor Sapkowski;
	Autor Nelson;
	
	@Test
	public void checkConnection(){
		assertNotNull(ksiazkaManager.getConnection());
	}
	/***
	 * Simply because object Ksiazka depends on Autor
	 * we need to provide Autors in the actual database
	 * before we check how the tests work
	 */
	public void przedTestem(){
		Sapkowski = new Autor("Andrzej Sapkowski", 175, "polskie");
		Nelson = new Autor("Jandy Nelson", 168, "amerykanskie");
		autorManager.addAutor(Sapkowski);
		autorManager.addAutor(Nelson);
	}
	
	@Test
	public void checkAddKsiazka(){
		przedTestem();

		Ksiazka ksiazka = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		
		ksiazkaManager.clearKsiazkas();
		assertEquals(1,ksiazkaManager.addKsiazka(ksiazka));
		
		List<Ksiazka> ksiazkas = ksiazkaManager.getAllKsiazkas();
		Ksiazka ksiazkaRetrieved = ksiazkas.get(0);
		
		assertEquals(TYTUL_1, ksiazkaRetrieved.getTytul());
		assertEquals(KAT_1, ksiazkaRetrieved.getKategoria());
		assertEquals(AUTORID_1, ksiazkaRetrieved.getAutorId());
		
	}
	
	@Test 
	public void checkDeleteKsiazka(){
		ksiazkaManager.clearKsiazkas();
		
		Ksiazka ks = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		assertEquals(1, ksiazkaManager.addKsiazka(ks));
		
		assertEquals(1, ksiazkaManager.deleteKsiazka(TYTUL_1, AUTORID_1));
		
		assertEquals(null, ksiazkaManager.getKsiazka(TYTUL_1));
	}
	
	@Test
	public void checkModifyKsiazka(){
		ksiazkaManager.clearKsiazkas();
		Ksiazka jeden = new Ksiazka(TYTUL_1, KAT_1, AUTORID_1);
		ksiazkaManager.addKsiazka(jeden);		
		assertEquals(TYTUL_1, jeden.getTytul());
		ksiazkaManager.modifyKsiazka(TYTUL_2, AUTORID_1);
		Ksiazka dwa = ksiazkaManager.getKsiazka(TYTUL_2);
		assertEquals(TYTUL_2, dwa.getTytul());
		assertEquals(KAT_1, dwa.getKategoria());
		assertEquals(AUTORID_1, dwa.getAutorId());
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
	public void getKsiazkasByIdTest() {
		autorManager.vanishAutors();
		przedTestem();
		Autor a = autorManager.getAutor("Andrzej Sapkowski"); /*Powinno byc "Andrzej Sapkowski" ??? */
		
		assertEquals(a.getAutorPer(), "Andrzej Sapkowski"); /* j.w.*/
		List<Ksiazka> ks = ksiazkaManager.getKsiazkasById(a.getId());
		assertEquals(0, ks.size()); /*czym jest to 'size'?? */
		Ksiazka jeden = new Ksiazka("Wiedzmin 1", "fantasy", a.getId());
		ksiazkaManager.addKsiazka(jeden);
		ks = ksiazkaManager.getKsiazkasById(a.getId());
		assertEquals(1, ks.size());
		
	}

}
