package model;

// JavaBean klasa - predstavlja jedan lek koji se prodaje u odredjenoj apoteci
public class Lek {

	private int id;
	private String naziv;
	private String opis;
	private double cena;
	private int kolicina;
	private int popust; // popust u procentima, npr. 10 znaci 10%
	private int apotekaId;
	private String apotekaNaziv; // pomocno polje za prikaz u tabelama (naziv apoteke)
	private String apotekaGrad; // pomocno polje za prikaz u tabelama (grad apoteke)

	public Lek() {
	}

	public Lek(String naziv, String opis, double cena, int kolicina, int popust, int apotekaId) {
		this.naziv = naziv;
		this.opis = opis;
		this.cena = cena;
		this.kolicina = kolicina;
		this.popust = popust;
		this.apotekaId = apotekaId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public double getCena() {
		return cena;
	}

	public void setCena(double cena) {
		this.cena = cena;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}

	public int getPopust() {
		return popust;
	}

	public void setPopust(int popust) {
		this.popust = popust;
	}

	public int getApotekaId() {
		return apotekaId;
	}

	public void setApotekaId(int apotekaId) {
		this.apotekaId = apotekaId;
	}

	public String getApotekaNaziv() {
		return apotekaNaziv;
	}

	public void setApotekaNaziv(String apotekaNaziv) {
		this.apotekaNaziv = apotekaNaziv;
	}

	public String getApotekaGrad() {
		return apotekaGrad;
	}

	public void setApotekaGrad(String apotekaGrad) {
		this.apotekaGrad = apotekaGrad;
	}

	// racuna konacnu cenu jednog komada leka kada se primeni popust
	public double cenaSaPopustom() {
		return cena - (cena * popust / 100.0);
	}

	@Override
	public String toString() {
		return "Lek [id=" + id + ", naziv=" + naziv + ", cena=" + cena + ", kolicina=" + kolicina + ", popust="
				+ popust + "]";
	}
}
