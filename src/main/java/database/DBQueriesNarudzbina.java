package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Narudzbina;

// Klasa koja sadrzi sve upite ka bazi vezane za tabelu narudzbina
public class DBQueriesNarudzbina {

	// kreira novu narudzbinu (jedna kupovina jednog leka)
	public static void kreirajNarudzbinu(Narudzbina n) {
		String upit = "INSERT INTO narudzbina (korisnik_id, apoteka_id, lek_id, kolicina, cena_po_komadu, "
				+ "ukupna_cena, iskorisceni_poeni, dobijeni_poeni) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, n.getKorisnikId());
			pst.setInt(2, n.getApotekaId());
			pst.setInt(3, n.getLekId());
			pst.setInt(4, n.getKolicina());
			pst.setDouble(5, n.getCenaPoKomadu());
			pst.setDouble(6, n.getUkupnaCena());
			pst.setInt(7, n.getIskorisceniPoeni());
			pst.setInt(8, n.getDobijeniPoeni());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// sve narudzbine jednog klijenta (istorija kupovina)
	public static ArrayList<Narudzbina> getNarudzbineZaKorisnika(int korisnikId) {
		ArrayList<Narudzbina> narudzbine = new ArrayList<Narudzbina>();
		String upit = "SELECT n.id, n.korisnik_id, n.apoteka_id, n.lek_id, n.kolicina, n.cena_po_komadu, "
				+ "n.ukupna_cena, n.iskorisceni_poeni, n.dobijeni_poeni, n.datum, "
				+ "l.naziv AS lek_naziv, a.naziv AS apoteka_naziv, "
				+ "k.ime AS korisnik_ime, k.prezime AS korisnik_prezime "
				+ "FROM narudzbina n "
				+ "JOIN lek l ON n.lek_id = l.id "
				+ "JOIN apoteka a ON n.apoteka_id = a.id "
				+ "JOIN korisnik k ON n.korisnik_id = k.id "
				+ "WHERE n.korisnik_id = ? ORDER BY n.datum DESC";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, korisnikId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				narudzbine.add(mapujRed(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return narudzbine;
	}

	// sve narudzbine jedne apoteke (koristi menadzer za izvestaj o poslovanju -
	// ukljucuje i ime i prezime klijenta koji je naruzio lek)
	public static ArrayList<Narudzbina> getNarudzbineZaApoteku(int apotekaId) {
		ArrayList<Narudzbina> narudzbine = new ArrayList<Narudzbina>();
		String upit = "SELECT n.id, n.korisnik_id, n.apoteka_id, n.lek_id, n.kolicina, n.cena_po_komadu, "
				+ "n.ukupna_cena, n.iskorisceni_poeni, n.dobijeni_poeni, n.datum, "
				+ "l.naziv AS lek_naziv, a.naziv AS apoteka_naziv, "
				+ "k.ime AS korisnik_ime, k.prezime AS korisnik_prezime "
				+ "FROM narudzbina n "
				+ "JOIN lek l ON n.lek_id = l.id "
				+ "JOIN apoteka a ON n.apoteka_id = a.id "
				+ "JOIN korisnik k ON n.korisnik_id = k.id "
				+ "WHERE n.apoteka_id = ? ORDER BY n.datum DESC";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, apotekaId);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				narudzbine.add(mapujRed(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return narudzbine;
	}

	// ukupan prihod jedne apoteke (zbir ukupnih cena svih njenih narudzbina) -
	// koristi se u izvestaju menadzera
	public static double getUkupanPrihodZaApoteku(int apotekaId) {
		double prihod = 0;
		String upit = "SELECT SUM(ukupna_cena) AS prihod FROM narudzbina WHERE apoteka_id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, apotekaId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				prihod = rs.getDouble("prihod");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prihod;
	}

	// jedna narudzbina po id-u (koristi se pri otkazivanju - da se proveri
	// kome pripada i koliko treba vratiti na stanje/poene)
	public static Narudzbina getNarudzbinaPoId(int id) {
		Narudzbina n = null;
		String upit = "SELECT n.id, n.korisnik_id, n.apoteka_id, n.lek_id, n.kolicina, n.cena_po_komadu, "
				+ "n.ukupna_cena, n.iskorisceni_poeni, n.dobijeni_poeni, n.datum, "
				+ "l.naziv AS lek_naziv, a.naziv AS apoteka_naziv, "
				+ "k.ime AS korisnik_ime, k.prezime AS korisnik_prezime "
				+ "FROM narudzbina n "
				+ "JOIN lek l ON n.lek_id = l.id "
				+ "JOIN apoteka a ON n.apoteka_id = a.id "
				+ "JOIN korisnik k ON n.korisnik_id = k.id "
				+ "WHERE n.id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				n = mapujRed(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n;
	}

	// otkazivanje (brisanje) narudzbine - koristi se kada klijent otkaze kupovinu
	public static void obrisiNarudzbinu(int id) {
		String upit = "DELETE FROM narudzbina WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static Narudzbina mapujRed(ResultSet rs) throws SQLException {
		Narudzbina n = new Narudzbina();
		n.setId(rs.getInt("id"));
		n.setKorisnikId(rs.getInt("korisnik_id"));
		n.setApotekaId(rs.getInt("apoteka_id"));
		n.setLekId(rs.getInt("lek_id"));
		n.setKolicina(rs.getInt("kolicina"));
		n.setCenaPoKomadu(rs.getDouble("cena_po_komadu"));
		n.setUkupnaCena(rs.getDouble("ukupna_cena"));
		n.setIskorisceniPoeni(rs.getInt("iskorisceni_poeni"));
		n.setDobijeniPoeni(rs.getInt("dobijeni_poeni"));
		n.setDatum(rs.getString("datum"));
		n.setLekNaziv(rs.getString("lek_naziv"));
		n.setApotekaNaziv(rs.getString("apoteka_naziv"));
		n.setKorisnikImePrezime(rs.getString("korisnik_ime") + " " + rs.getString("korisnik_prezime"));
		return n;
	}
}
