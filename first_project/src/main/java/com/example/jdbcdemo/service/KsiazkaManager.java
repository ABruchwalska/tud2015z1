package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Ksiazka;

public class KsiazkaManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";

	private String createTableKsiazka = "CREATE TABLE Ksiazka(id int GENERATED BY DEFAULT AS IDENTITY, tytul varchar(200), kat varchar(50), autorId integer)";

	private PreparedStatement addKsiazkaStmt;
	private PreparedStatement deleteAllKsiazkasStmt;
	private PreparedStatement getAllKsiazkasStmt;
	private PreparedStatement getKsiazkaStmt;
	private PreparedStatement modifyKsiazkaStmt;
	private PreparedStatement deleteKsiazkaStmt;
	private PreparedStatement getKsiazkasByIdStmt;

	private Statement statement;

	public KsiazkaManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("Ksiazka".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists){
				statement.executeUpdate(createTableKsiazka);
			}
			addKsiazkaStmt = connection
					.prepareStatement("INSERT INTO Ksiazka (tytul, kat, autorId) VALUES (?, ?, ?)");
			deleteAllKsiazkasStmt = connection
					.prepareStatement("DELETE FROM Ksiazka");
			getAllKsiazkasStmt = connection
					.prepareStatement("SELECT id, tytul, kat, autorId FROM Ksiazka");
			getKsiazkaStmt = connection
					.prepareStatement("SELECT id, tytul, kat, autorId FROM Ksiazka WHERE tytul=?");
			modifyKsiazkaStmt = connection
					.prepareStatement("UPDATE Ksiazka SET autorId=? WHERE tytul=? AND autorId=?");
			deleteKsiazkaStmt = connection
					.prepareStatement("DELETE FROM Ksiazka WHERE tytul=? AND autorId=?");
			getKsiazkasByIdStmt = connection
					.prepareStatement("SELECT id, tytul, kat, autorId FROM Ksiazka WHERE autorId=?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	void clearKsiazkas() {
		try {
			deleteAllKsiazkasStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addKsiazka(Ksiazka ksiazka) {
		int count = 0;
		try {
			addKsiazkaStmt.setString(1, ksiazka.getTytul());
			addKsiazkaStmt.setString(2, ksiazka.getKategoria());
			addKsiazkaStmt.setLong(3, ksiazka.getAutorId());
			count = addKsiazkaStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int modifyKsiazka(String tytul, String kat1, long nowyAutorId) {
		int count = 0;
		try {
			modifyKsiazkaStmt.setLong(1, nowyAutorId);
			modifyKsiazkaStmt.setString(2, tytul);
			modifyKsiazkaStmt.setString(3, kat1);
			count = modifyKsiazkaStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteKsiazka(String tytul, long autorId) {
		int count = 0;
		try {
			deleteKsiazkaStmt.setString(1, tytul);
			deleteKsiazkaStmt.setLong(2, autorId);
			count = deleteKsiazkaStmt.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public Ksiazka getKsiazka(String tytul) {
		Ksiazka ks = new Ksiazka();
		try {
			getKsiazkaStmt.setString(1, tytul);
			ResultSet rs = getKsiazkaStmt.executeQuery();
			if(!rs.next()){
				return null;
			}
			ks.setId(rs.getInt("id"));
			ks.setTytul(rs.getString("tytul"));
			ks.setKategoria(rs.getString("kat"));
			ks.setAutorId(rs.getInt("autorId"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ks;
	}
	
	public List<Ksiazka> getAllKsiazkas() {
		List<Ksiazka> ksiazkas = new ArrayList<Ksiazka>();

		try {
			ResultSet rs = getAllKsiazkasStmt.executeQuery();

			while (rs.next()) {
				Ksiazka ks = new Ksiazka();
				ks.setId(rs.getInt("id"));
				ks.setTytul(rs.getString("tytul"));
				ks.setKategoria(rs.getString("kat"));
				ks.setAutorId(rs.getInt("autorId"));
				ksiazkas.add(ks);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ksiazkas;
	}
	
	public List<Ksiazka> getKsiazkasById(long id){
		List<Ksiazka> ksiazkas = new ArrayList<Ksiazka>();

		try {
			getKsiazkasByIdStmt.setLong(1, id);
			ResultSet rs = getKsiazkasByIdStmt.executeQuery();

			while (rs.next()) {
				Ksiazka ks = new Ksiazka();
				ks.setId(rs.getInt("id"));
				ks.setTytul(rs.getString("tytul"));
				ks.setKategoria(rs.getString("kat"));
				ks.setAutorId(rs.getInt("autorId"));
				ksiazkas.add(ks);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ksiazkas;
	}

}