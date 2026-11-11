package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Lek;

// Klasa koja sadrzi sve upite ka bazi vezane za tabelu lek
public class DBQueriesLek {

	private static Lek mapujRed(ResultSet rs) throws SQLException {
		Lek l = new Lek();
		l.setId(rs.getInt("id"));
		l.setNaziv(rs.getString("naziv"));
		l.setOpis(rs.getString("opis"));
		l.setCena(rs.getDouble("cena"));
		l.setKolicina(rs.getInt("kolicina"));
		l.setPopust(rs.getInt("popust"));
		l.setApotekaId(rs.getInt("apoteka_id"));
		l.setApotekaNaziv(rs.getString("apoteka_naziv"));
		l.setApotekaGrad(rs.getString("apoteka_grad"));
		return l;
	}

	// svi lekovi iz svih apoteka - koristi se za javni katalog koji vide
	// posetioci sajta
	public static ArrayList<Lek> getSviLekovi() {
		ArrayList<Lek> lekovi = new ArrayList<Lek>();
		String upit = "SELECT l.id, l.naziv, l.opis, l.cena, l.kolicina, l.popust, l.apoteka_id, "
				+ "a.naziv AS apoteka_naziv, a.grad AS apoteka_grad "
				+ "FROM lek l JOIN apoteka a ON l.apoteka_id = a.id "
				+ "ORDER BY l.naziv";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				lekovi.add(mapujRed(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lekovi;
	}

	// lekovi samo jedne apoteke - koristi ih menadzer te apoteke
	public static ArrayList<Lek> getLekoviZaApoteku(int apotekaId) {
		ArrayList<Lek> lekovi = new ArrayList<Lek>();
		String upit = "SELECT l.id, l.naziv, l.opis, l.cena, l.kolicina, l.popust, l.apoteka_id, "
				+ "a.naziv AS apoteka_naziv, a.grad AS apoteka_grad "
				+ "FROM lek l JOIN apoteka a ON l.apoteka_id = a.id "
				+ "WHERE l.apoteka_id = ? ORDER BY l.naziv";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, apotekaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				lekovi.add(mapujRed(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lekovi;
	}

	public static Lek getLekPoId(int id) {
		Lek l = null;
		String upit = "SELECT l.id, l.naziv, l.opis, l.cena, l.kolicina, l.popust, l.apoteka_id, "
				+ "a.naziv AS apoteka_naziv, a.grad AS apoteka_grad "
				+ "FROM lek l JOIN apoteka a ON l.apoteka_id = a.id WHERE l.id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				l = mapujRed(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l;
	}

	public static void dodajLek(Lek l) {
		String upit = "INSERT INTO lek (naziv, opis, cena, kolicina, popust, apoteka_id) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, l.getNaziv());
			pst.setString(2, l.getOpis());
			pst.setDouble(3, l.getCena());
			pst.setInt(4, l.getKolicina());
			pst.setInt(5, l.getPopust());
			pst.setInt(6, l.getApotekaId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void izmeniLek(Lek l) {
		String upit = "UPDATE lek SET naziv=?, opis=?, cena=?, kolicina=?, popust=? WHERE id=? AND apoteka_id=?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, l.getNaziv());
			pst.setString(2, l.getOpis());
			pst.setDouble(3, l.getCena());
			pst.setInt(4, l.getKolicina());
			pst.setInt(5, l.getPopust());
			pst.setInt(6, l.getId());
			pst.setInt(7, l.getApotekaId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// menadzer moze da obrise samo lek koji pripada njegovoj apoteci
	public static void obrisiLek(int id, int apotekaId) {
		String upit = "DELETE FROM lek WHERE id = ? AND apoteka_id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			pst.setInt(2, apotekaId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// umanjuje raspolozivu kolicinu leka nakon kupovine
	public static void umanjiKolicinu(int lekId, int kupljenaKolicina) {
		String upit = "UPDATE lek SET kolicina = kolicina - ? WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, kupljenaKolicina);
			pst.setInt(2, lekId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// vraca kolicinu leka na stanje - koristi se kada klijent otkaze narudzbinu
	public static void povecajKolicinu(int lekId, int vracenaKolicina) {
		String upit = "UPDATE lek SET kolicina = kolicina + ? WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, vracenaKolicina);
			pst.setInt(2, lekId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
