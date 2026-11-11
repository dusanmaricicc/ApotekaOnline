package model;

// JavaBean klasa - predstavlja jednu kupovinu (narudzbinu) jednog leka od strane klijenta
public class Narudzbina {

	private int id;
	private int korisnikId;
	private int apotekaId;
	private int lekId;
	private String lekNaziv; // pomocno polje za prikaz u tabelama
	private String apotekaNaziv; // pomocno polje za prikaz u tabelama
	private String korisnikImePrezime; // pomocno polje - ime i prezime kupca (koristi menadzer u izvestaju)
	private int kolicina;
	private double cenaPoKomadu;
	private double ukupnaCena;
	private int iskorisceniPoeni;
	private int dobijeniPoeni;
	private String datum;

	public Narudzbina() {
	}

	public Narudzbina(int korisnikId, int apotekaId, int lekId, int kolicina, double cenaPoKomadu,
			double ukupnaCena, int iskorisceniPoeni, int dobijeniPoeni) {
		this.korisnikId = korisnikId;
		this.apotekaId = apotekaId;
		this.lekId = lekId;
		this.kolicina = kolicina;
		this.cenaPoKomadu = cenaPoKomadu;
		this.ukupnaCena = ukupnaCena;
		this.iskorisceniPoeni = iskorisceniPoeni;
		this.dobijeniPoeni = dobijeniPoeni;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getKorisnikId() {
		return korisnikId;
	}

	public void setKorisnikId(int korisnikId) {
		this.korisnikId = korisnikId;
	}

	public int getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(int apotekaId) {
		this.apotekaId = apotekaId;
	}

	public int getLekId() {
		return lekId;
	}

	public void setLekId(int lekId) {
		this.lekId = lekId;
	}

	public String getLekNaziv() {
		return lekNaziv;
	}

	public void setLekNaziv(String lekNaziv) {
		this.lekNaziv = lekNaziv;
	}

	public String getApotekaNaziv() {
		return apotekaNaziv;
	}

	public void setApotekaNaziv(String apotekaNaziv) {
		this.apotekaNaziv = apotekaNaziv;
	}

	public String getKorisnikImePrezime() {
		return korisnikImePrezime;
	}

	public void setKorisnikImePrezime(String korisnikImePrezime) {
		this.korisnikImePrezime = korisnikImePrezime;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public double getCenaPoKomadu() {
		return cenaPoKomadu;
	}

	public void setCenaPoKomadu(double cenaPoKomadu) {
		this.cenaPoKomadu = cenaPoKomadu;
	}

	public double getUkupnaCena() {
		return ukupnaCena;
	}

	public void setUkupnaCena(double ukupnaCena) {
		this.ukupnaCena = ukupnaCena;
	}

	public int getIskorisceniPoeni() {
		return iskorisceniPoeni;
	}

	public void setIskorisceniPoeni(int iskorisceniPoeni) {
		this.iskorisceniPoeni = iskorisceniPoeni;
	}

	public int getDobijeniPoeni() {
		return dobijeniPoeni;
	}

	public void setDobijeniPoeni(int dobijeniPoeni) {
		this.dobijeniPoeni = dobijeniPoeni;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}
}
