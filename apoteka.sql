-- Skripta za kreiranje baze podataka za Java Web aplikaciju za apoteku


CREATE DATABASE IF NOT EXISTS apoteka;
USE apoteka;

-- Tabela apoteka (poslovnih jedinica)
CREATE TABLE apoteka (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naziv VARCHAR(100) NOT NULL,
    adresa VARCHAR(150) NOT NULL,
    grad VARCHAR(50) NOT NULL
);

-- Tabela korisnika (administrator, menadzer, klijent)
CREATE TABLE korisnik (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ime VARCHAR(50) NOT NULL,
    prezime VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    uloga VARCHAR(20) NOT NULL, -- 'administrator', 'menadzer' ili 'klijent'
    apoteka_id INT NULL, -- samo za menadzera - u kojoj apoteci radi
    poeni INT NOT NULL DEFAULT 0, -- samo za klijenta - sakupljeni poeni
    FOREIGN KEY (apoteka_id) REFERENCES apoteka(id)
);

-- Tabela lekova
CREATE TABLE lek (
    id INT AUTO_INCREMENT PRIMARY KEY,
    naziv VARCHAR(100) NOT NULL,
    opis VARCHAR(255),
    cena DECIMAL(10,2) NOT NULL,
    kolicina INT NOT NULL,
    popust INT NOT NULL DEFAULT 0, -- popust u procentima (0-100)
    apoteka_id INT NOT NULL,
    FOREIGN KEY (apoteka_id) REFERENCES apoteka(id)
);

-- Tabela narudzbina (jedna kupovina klijenta)
CREATE TABLE narudzbina (
    id INT AUTO_INCREMENT PRIMARY KEY,
    korisnik_id INT NOT NULL,
    apoteka_id INT NOT NULL,
    lek_id INT NOT NULL,
    kolicina INT NOT NULL,
    cena_po_komadu DECIMAL(10,2) NOT NULL,
    ukupna_cena DECIMAL(10,2) NOT NULL,
    iskorisceni_poeni INT NOT NULL DEFAULT 0,
    dobijeni_poeni INT NOT NULL DEFAULT 0,
    datum DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (korisnik_id) REFERENCES korisnik(id),
    FOREIGN KEY (apoteka_id) REFERENCES apoteka(id),
    FOREIGN KEY (lek_id) REFERENCES lek(id)
);

-- Pocetni podaci

INSERT INTO apoteka (naziv, adresa, grad) VALUES
('Apoteka Zdravlje', 'Majora Ilica', 'Ivanjica'),
('Apoteka Benu', 'Milojice Nikolica', 'Ivanjica');

-- lozinke su hesirane MD5 funkcijom baze prilikom ubacivanja (isto kao u vezbama)
INSERT INTO korisnik (ime, prezime, email, username, password, uloga, apoteka_id, poeni) VALUES
('Dusan', 'Maricic', 'admin@apoteka.rs', 'admin', MD5('admin123'), 'administrator', NULL, 0),
('Sinisa', 'Borovic', 'sinisa.menadzer@apoteka.rs', 'menadzer1', MD5('menadzer123'), 'menadzer', 1, 0),
('Bogdan', 'Curcic', 'bogdan.menadzer@apoteka.rs', 'menadzer2', MD5('menadzer123'), 'menadzer', 2, 0),
('Lena', 'Rajkovic', 'lena@gmail.com', 'klijent', MD5('klijent123'), 'klijent', NULL, 0);

INSERT INTO lek (naziv, opis, cena, kolicina, popust, apoteka_id) VALUES
('Brufen 400mg', 'Lek protiv bolova i upale, 20 tableta', 350.00, 50, 10, 1),
('Aspirin protect', 'Zastita srca, 30 tableta', 480.00, 40, 0, 1),
('Vitamin C 1000mg', 'Imunitet, 20 sumecih tableta', 620.00, 60, 15, 1),
('Andol', 'Lek protiv prehlade, 10 kesica', 540.00, 35, 5, 2),
('Nurofen 200mg', 'Lek protiv bolova i temperature, 24 tablete', 410.00, 45, 0, 2),
('Magnezijum + B6', 'Dodatak ishrani, 60 tableta', 730.00, 25, 20, 2);
