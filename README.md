# Java web aplikacija za apoteku

Web aplikacija za apoteke napravljena u Javi (Servlets, JSP, JDBC) sa MySQL bazom. Bez dodatnih frameworka: DbKonekcija singleton, DBQueries klase, sesije za prijavu i MD5 heširanje lozinki.

Napravljeno koriscenjem sledecih tehnologija i stilova:
Java Servlet, JSP (scriptlet pristup, bez JSTL/EL), JavaBean klase, JDBC
(PreparedStatement) i MySQL baza podataka. Nema Spring Boot-a, Hibernate-a
ni bilo kog dodatnog frameworka - sve je "rucno" napisano 
(DbKonekcija singleton, DBQueries klase sa statickim
metodama, sesija za logovanje, MD5 hesiranje lozinke kroz MySQL funkciju).

## Uloge u aplikaciji

- **Administrator** - upravlja korisnicima (dodaje, izmenjuje, brise, dodeljuje
  uloge) i apotekama (dodaje, izmenjuje, brise).
- **Menadzer** - vezan je za tacno jednu apoteku. Upravlja lekovima te apoteke
  (prikaz, dodavanje, izmena, brisanje) i pregleda izvestaj o poslovanju
  (broj lekova, vrednost zalihe, ukupan prihod, spisak narudzbina).
- **Klijent** - posetilac koji se registruje, pregleda ponudu lekova iz svih
  apoteka (cene, popusti, kolicine), kupuje lekove i za svaku kupovinu
  dobija poene koje moze iskoristiti za popust pri sledecoj kupovini.
- **Neregistrovani posetilac** - moze da pregleda katalog lekova i spisak
  apoteka bez logovanja, ali da bi kupio lek mora da se registruje/uloguje.

## Struktura projekta

```
Java web aplikacija za apoteku/
├── apoteka.sql                     -> skripta za kreiranje baze i test podataka
├── src/main/java/
│   ├── model/                      -> JavaBean klase (Apoteka, Korisnik, Lek, Narudzbina)
│   ├── database/                   -> DbKonekcija (singleton) i DBQueries* klase (JDBC upiti)
│   └── servleti/                   -> svi servleti (login, registracija, CRUD, kupovina...)
└── src/main/webapp/
    ├── WEB-INF/web.xml             -> mapiranja servleta
    ├── css/style.css
    └── *.jsp                       -> JSP stranice (header/footer + sve view stranice)
```

## Kako pokrenuti projekat

### 1. Baza podataka (MySQL)

Pokrenuti sadrzaj fajla `apoteka.sql` u MySQL-u (npr. kroz MySQL Workbench,
phpMyAdmin ili komandnu liniju). Skripta kreira bazu `apoteka`, sve tabele i
ubacuje test podatke (2 apoteke, admin, 2 menadzera i 1 klijent, kao i
nekoliko lekova).

**Test nalozi (nakon pokretanja skripte):**

| Uloga         | Korisnicko ime | Lozinka       |
|---------------|----------------|---------------|
| administrator | admin          | admin123      |
| menadzer      | menadzer1      | menadzer123   |
| menadzer      | menadzer2      | menadzer123   |
| klijent       | jovana         | jovana123     |

Ako se MySQL server ne nalazi na portu 3306 ili imate drugo korisnicko ime /
lozinku za bazu, izmenite promenljive `url`, `user` i `password` u fajlu
`src/main/java/database/DbKonekcija.java`.

### 2. Uvoz projekta u Eclipse

1. `File -> Import -> Existing Projects into Workspace` i izabrati folder
   `Java web aplikacija za apoteku` (projekat vec ima `.project` i `.classpath`
   fajlove, isto kao projekti sa vezbi, tako da bi trebalo direktno da se
   prepozna kao Dynamic Web Project).
2. Preuzeti MySQL Connector/J (ista verzija koriscena na vezbama -
   `mysql-connector-j-9.6.0.jar`) i postaviti ga u
   `src/main/webapp/WEB-INF/lib/`, a zatim ga dodati i na Build Path
   (desni klik na projekat -> Build Path -> Configure Build Path -> Add JARs).
3. Postaviti Tomcat 9.0 kao server (projekat koristi `javax.servlet` paket i
   Servlet API 4.0, sto odgovara Tomcat-u 9.x - ne Tomcat-u 10+, koji koristi
   `jakarta.servlet`). Projekat je podesen za JDK 17 - ako u Eclipse-u koristis
   drugu verziju JDK-a, izmeni je u Project Properties -> Java Build Path ->
   Libraries i u Project Facets.
4. Pokrenuti projekat na serveru (`Run As -> Run on Server`). Napomena:
   pošto naziv projekta sadrži razmake, context-root u projektu je
   podešen na `ApotekaZaApoteku` (bez razmaka), pa će se aplikacija
   otvarati na adresi npr. `http://localhost:8080/ApotekaZaApoteku/`.

### 3. Testiranje

- Otvoriti pocetnu stranu i pregledati katalog lekova (dostupno svima).
- Registrovati novog klijenta i probati kupovinu leka (poeni se prikazuju
  u meniju nakon logovanja).
- Ulogovati se kao `menadzer1` i probati dodavanje/izmenu/brisanje leka,
  kao i pregled izvestaja o poslovanju.
- Ulogovati se kao `admin` i probati dodavanje korisnika i apoteka.

## Napomena

“Planovi za dalji razvoj”: 
korpa sa više lekova u jednoj narudžbini, 
pretraga i filtriranje, paginacija, detaljnija validacija.
