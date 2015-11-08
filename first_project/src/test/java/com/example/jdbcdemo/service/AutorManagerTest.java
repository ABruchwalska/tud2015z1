package com.example.jdbcdemo.service;
import static org.junit.Assert.*;


import org.junit.Test;

import com.example.jdbcdemo.domain.Autor;

public class AutorManagerTest {

	AutorManager autorManager = new AutorManager(); //czemu tu mam blad? xdd
	Autor autor = new Autor();
	private final static String AUTORPER_1 = "Andrzej Sapkowski";
	private final static int WZROST_1 = 175;
	private final static String OBYWATELSTWO_1 = "polskie";
	
	private final static String AUTORPER_2 = "Wislawa Szymborska";
	private final static int WZROST_2 = 155;
	private final static String OBYWATELSTWO_2 = "polskie";
	
	@Test
	public void getConnectionTest(){
		assertNotNull(autorManager.getConnection());
	}
	
	@Test
	public void addAutorTest(){
		Autor autor = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		
		autorManager.vanishAutors();
		assertEquals(1,autorManager.addAutor(autor));
		
		Autor autorRetrieved = autorManager.getAutor(AUTORPER_1);
		
		assertEquals(AUTORPER_1, autorRetrieved.getAutorPer());
		assertEquals(WZROST_1, autorRetrieved.getWzrost());
		assertEquals(OBYWATELSTWO_1, autorRetrieved.getObywatelstwo());
	}
	
	@Test
	public void modifyAutorTest(){ //NIE ROZUMIEM TEGO TESTU...
		autorManager.vanishAutors();
		
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		autorManager.addAutor(Sapkowski);
		
		assertEquals(AUTORPER_1, Sapkowski.getAutorPer());
		autorManager.modifyAutor(AUTORPER_1, OBYWATELSTWO_2);
		Autor Sapkowski2 = autorManager.getAutor(AUTORPER_1);
		assertEquals(AUTORPER_1, Sapkowski2.getAutorPer());
		assertEquals(WZROST_1, Sapkowski2.getWzrost());
		assertEquals(OBYWATELSTWO_2, Sapkowski2.getObywatelstwo());
	}
	
	@Test
	public void deleteAutorTest(){
		autorManager.vanishAutors();
		
		Autor a = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		assertEquals(1, autorManager.addAutor(a));
		
		assertEquals(1, autorManager.deleteAutor(AUTORPER_1));
		
		assertEquals(null, autorManager.getAutor(AUTORPER_1));
	}
		
	@Test
	public void getAutorrTest(){
		autorManager.vanishAutors();
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		Autor Sapkowski2 = new Autor(AUTORPER_2, WZROST_2, OBYWATELSTWO_2);
		assertEquals(null, autorManager.getAutor(AUTORPER_1));
		autorManager.addAutor(Sapkowski);
		autorManager.addAutor(Sapkowski2);
		
		assertEquals(AUTORPER_1, autorManager.getAutor(AUTORPER_1).getAutorPer());
		assertEquals(WZROST_1, autorManager.getAutor(AUTORPER_1).getWzrost());
		assertEquals(OBYWATELSTWO_1, autorManager.getAutor(AUTORPER_1).getObywatelstwo());
		
		assertEquals(AUTORPER_2, autorManager.getAutor(AUTORPER_2).getAutorPer());
		assertEquals(WZROST_2, autorManager.getAutor(AUTORPER_2).getWzrost());
		assertEquals(OBYWATELSTWO_2, autorManager.getAutor(AUTORPER_2).getObywatelstwo());

	}
	
	@Test
	public void getAllAutorsTest(){
		autorManager.vanishAutors();
		assertEquals(0, autorManager.getAllAutors().size());
		Autor Sapkowski = new Autor(AUTORPER_1, WZROST_1, OBYWATELSTWO_1);
		Autor Sapkowski2 = new Autor(AUTORPER_2, WZROST_2, OBYWATELSTWO_2);
		autorManager.addAutor(Sapkowski);
		autorManager.addAutor(Sapkowski2);
		assertEquals(2, autorManager.getAllAutors().size());
		assertEquals(AUTORPER_1, autorManager.getAllAutors().get(0).getAutorPer());
		assertEquals(AUTORPER_2, autorManager.getAllAutors().get(1).getAutorPer());
		autorManager.vanishAutors();
	}
	
}
