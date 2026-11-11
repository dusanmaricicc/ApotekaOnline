package model;

// JavaBean klasa - predstavlja korisnika aplikacije.
// Uloga korisnika moze biti: "administrator", "menadzer" ili "klijent"
public class Korisnik {

	private int id;
	private String ime;
	private String prezime;
	private String email;
	private String username;
	private String password;
	private String uloga;
	private Integer apotekaId; // koristi se samo kada je uloga = menadzer
	private String apotekaNaziv; // pomocno polje za prikaz naziva apoteke u tabelama
	private int poeni; // koristi se samo kada je uloga = klijent

	public Korisnik() {
	}

	// konstruktor koji se koristi prilikom logovanja (samo username i password)
	public Korisnik(String username, String password) {
		this.username = username;
		this.password = password;
	}

	// konstruktor koji se koristi prilikom registracije klijenta
	public Korisnik(String ime, String prezime, String email, String username, String password) {
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.username = username;
		this.password = password;
		this.uloga = "klijent";
		this.poeni = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUloga() {
		return uloga;
	}

	public void setUloga(String uloga) {
		this.uloga = uloga;
	}

	public Integer getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(Integer apotekaId) {
		this.apotekaId = apotekaId;
	}

	public String getApotekaNaziv() {
		return apotekaNaziv;
	}

	public void setApotekaNaziv(String apotekaNaziv) {
		this.apotekaNaziv = apotekaNaziv;
	}

	public int getPoeni() {
		return poeni;
	}

	public void setPoeni(int poeni) {
		this.poeni = poeni;
	}

	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", username=" + username
				+ ", uloga=" + uloga + ", apotekaId=" + apotekaId + ", poeni=" + poeni + "]";
	}
}
