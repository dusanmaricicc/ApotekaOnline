package model;

// JavaBean klasa - predstavlja jednu apoteku (poslovnu jedinicu)
public class Apoteka {

	private int id;
	private String naziv;
	private String adresa;
	private String grad;

	public Apoteka() {
	}

	public Apoteka(String naziv, String adresa, String grad) {
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
	}

	public Apoteka(int id, String naziv, String adresa, String grad) {
		this.id = id;
		this.naziv = naziv;
		this.adresa = adresa;
		this.grad = grad;
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

	public String getAdresa() {
		return adresa;
	}

	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}

	public String getGrad() {
		return grad;
	}

	public void setGrad(String grad) {
		this.grad = grad;
	}

	@Override
	public String toString() {
		return "Apoteka [id=" + id + ", naziv=" + naziv + ", adresa=" + adresa + ", grad=" + grad + "]";
	}
}
