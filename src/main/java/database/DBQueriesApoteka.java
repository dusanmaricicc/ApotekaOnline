package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Apoteka;

// Klasa koja sadrzi sve upite ka bazi vezane za tabelu apoteka
public class DBQueriesApoteka {

	public static ArrayList<Apoteka> getSveApoteke() {
		ArrayList<Apoteka> apoteke = new ArrayList<Apoteka>();
		String upit = "SELECT id, naziv, adresa, grad FROM apoteka ORDER BY naziv";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Apoteka a = new Apoteka(rs.getInt("id"), rs.getString("naziv"), rs.getString("adresa"),
						rs.getString("grad"));
				apoteke.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return apoteke;
	}

	public static Apoteka getApotekaPoId(int id) {
		Apoteka a = null;
		String upit = "SELECT id, naziv, adresa, grad FROM apoteka WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				a = new Apoteka(rs.getInt("id"), rs.getString("naziv"), rs.getString("adresa"), rs.getString("grad"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return a;
	}

	public static void dodajApoteku(Apoteka a) {
		String upit = "INSERT INTO apoteka (naziv, adresa, grad) VALUES (?, ?, ?)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, a.getNaziv());
			pst.setString(2, a.getAdresa());
			pst.setString(3, a.getGrad());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void izmeniApoteku(Apoteka a) {
		String upit = "UPDATE apoteka SET naziv = ?, adresa = ?, grad = ? WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, a.getNaziv());
			pst.setString(2, a.getAdresa());
			pst.setString(3, a.getGrad());
			pst.setInt(4, a.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void obrisiApoteku(int id) {
		String upit = "DELETE FROM apoteka WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
