CREATE TABLE IF NOT EXISTS gruppe_data
(
    id VARCHAR(36) PRIMARY KEY,
    gruppenname VARCHAR(300),
    geschlossen BOOLEAN
);
CREATE TABLE IF NOT EXISTS transaktion_data
(
    id INT  PRIMARY KEY,
    betrag DOUBLE NOT NULL,
    sponsor VARCHAR(300),
    beschreibung VARCHAR(300),
    FOREIGN KEY (id) REFERENCES gruppe_data(id)
);
CREATE TABLE IF NOT EXISTS gruppe_nutzer_relation
(
    id INT  PRIMARY KEY,
    nutzername VARCHAR(300),
    FOREIGN KEY (id) REFERENCES gruppe_data(id)
);
CREATE TABLE IF NOT EXISTS gruppe_transaktion_realtion
(
    nutzername VARCHAR(300)
    FOREIGN KEY (id) REFERENCES transaktion_data(id)
);

