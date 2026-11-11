package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Korisnik;

// Klasa koja sadrzi sve upite ka bazi vezane za tabelu korisnik
public class DBQueriesKorisnik {

	// proverava username i password (password se hesira MD5 funkcijom baze,
	// isto kao sto je radjeno na vezbama) i vraca ceo objekat Korisnik ako je
	// login uspesan, odnosno null ako nije
	public static Korisnik proveriKorisnika(Korisnik k) {
		Korisnik pronadjen = null;
		String upit = "SELECT id, ime, prezime, email, username, uloga, apoteka_id, poeni "
				+ "FROM korisnik WHERE username = ? AND password = MD5(?)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, k.getUsername());
			pst.setString(2, k.getPassword());
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				pronadjen = new Korisnik();
				pronadjen.setId(rs.getInt("id"));
				pronadjen.setIme(rs.getString("ime"));
				pronadjen.setPrezime(rs.getString("prezime"));
				pronadjen.setEmail(rs.getString("email"));
				pronadjen.setUsername(rs.getString("username"));
				pronadjen.setUloga(rs.getString("uloga"));
				int apotekaId = rs.getInt("apoteka_id");
				if (!rs.wasNull()) {
					pronadjen.setApotekaId(apotekaId);
				}
				pronadjen.setPoeni(rs.getInt("poeni"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pronadjen;
	}

	// proverava da li vec postoji korisnik sa unetim username-om (koristi se
	// prilikom registracije da ne bi doslo do duplikata)
	public static boolean postojiUsername(String username) {
		String upit = "SELECT id FROM korisnik WHERE username = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	// registracija novog klijenta sa sajta (uloga je uvek "klijent", poeni 0)
	public static void registrujKlijenta(Korisnik k) {
		String upit = "INSERT INTO korisnik (ime, prezime, email, username, password, uloga, apoteka_id, poeni) "
				+ "VALUES (?, ?, ?, ?, MD5(?), 'klijent', NULL, 0)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, k.getIme());
			pst.setString(2, k.getPrezime());
			pst.setString(3, k.getEmail());
			pst.setString(4, k.getUsername());
			pst.setString(5, k.getPassword());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// administrator dodaje korisnika sa bilo kojom ulogom
	public static void dodajKorisnika(Korisnik k) {
		String upit = "INSERT INTO korisnik (ime, prezime, email, username, password, uloga, apoteka_id, poeni) "
				+ "VALUES (?, ?, ?, ?, MD5(?), ?, ?, 0)";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setString(1, k.getIme());
			pst.setString(2, k.getPrezime());
			pst.setString(3, k.getEmail());
			pst.setString(4, k.getUsername());
			pst.setString(5, k.getPassword());
			pst.setString(6, k.getUloga());
			if (k.getApotekaId() != null) {
				pst.setInt(7, k.getApotekaId());
			} else {
				pst.setNull(7, java.sql.Types.INTEGER);
			}
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// administrator izmenjuje osnovne podatke, ulogu i apoteku korisnika
	// (lozinka se menja samo ako je uneta nova)
	public static void izmeniKorisnika(Korisnik k, boolean menjaSeLozinka) {
		String upit;
		if (menjaSeLozinka) {
			upit = "UPDATE korisnik SET ime=?, prezime=?, email=?, username=?, password=MD5(?), "
					+ "uloga=?, apoteka_id=? WHERE id=?";
		} else {
			upit = "UPDATE korisnik SET ime=?, prezime=?, email=?, username=?, "
					+ "uloga=?, apoteka_id=? WHERE id=?";
		}
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			int i = 1;
			pst.setString(i++, k.getIme());
			pst.setString(i++, k.getPrezime());
			pst.setString(i++, k.getEmail());
			pst.setString(i++, k.getUsername());
			if (menjaSeLozinka) {
				pst.setString(i++, k.getPassword());
			}
			pst.setString(i++, k.getUloga());
			if (k.getApotekaId() != null) {
				pst.setInt(i++, k.getApotekaId());
			} else {
				pst.setNull(i++, java.sql.Types.INTEGER);
			}
			pst.setInt(i++, k.getId());
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void obrisiKorisnika(int id) {
		String upit = "DELETE FROM korisnik WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Korisnik> getSviKorisnici() {
		ArrayList<Korisnik> korisnici = new ArrayList<Korisnik>();
		String upit = "SELECT k.id, k.ime, k.prezime, k.email, k.username, k.uloga, k.apoteka_id, k.poeni, "
				+ "a.naziv AS apoteka_naziv "
				+ "FROM korisnik k LEFT JOIN apoteka a ON k.apoteka_id = a.id "
				+ "ORDER BY k.uloga, k.prezime";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				Korisnik k = new Korisnik();
				k.setId(rs.getInt("id"));
				k.setIme(rs.getString("ime"));
				k.setPrezime(rs.getString("prezime"));
				k.setEmail(rs.getString("email"));
				k.setUsername(rs.getString("username"));
				k.setUloga(rs.getString("uloga"));
				int apotekaId = rs.getInt("apoteka_id");
				if (!rs.wasNull()) {
					k.setApotekaId(apotekaId);
				}
				k.setApotekaNaziv(rs.getString("apoteka_naziv"));
				k.setPoeni(rs.getInt("poeni"));
				korisnici.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return korisnici;
	}

	public static Korisnik getKorisnikPoId(int id) {
		Korisnik k = null;
		String upit = "SELECT id, ime, prezime, email, username, uloga, apoteka_id, poeni FROM korisnik WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				k = new Korisnik();
				k.setId(rs.getInt("id"));
				k.setIme(rs.getString("ime"));
				k.setPrezime(rs.getString("prezime"));
				k.setEmail(rs.getString("email"));
				k.setUsername(rs.getString("username"));
				k.setUloga(rs.getString("uloga"));
				int apotekaId = rs.getInt("apoteka_id");
				if (!rs.wasNull()) {
					k.setApotekaId(apotekaId);
				}
				k.setPoeni(rs.getInt("poeni"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return k;
	}

	// azurira broj poena klijenta (koristi se prilikom kupovine leka)
	public static void azurirajPoene(int korisnikId, int noviBrojPoena) {
		String upit = "UPDATE korisnik SET poeni = ? WHERE id = ?";
		try {
			DbKonekcija konekcija = DbKonekcija.getInstance();
			PreparedStatement pst = konekcija.getDb().prepareStatement(upit);
			pst.setInt(1, noviBrojPoena);
			pst.setInt(2, korisnikId);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
