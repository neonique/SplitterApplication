CREATE TABLE IF NOT EXISTS gruppe_data
(
    gruppenid VARCHAR(36) PRIMARY KEY,
    gruppenname VARCHAR(300),
    geschlossen BOOLEAN
);
CREATE TABLE IF NOT EXISTS transaktion_data
(
    transaktionid INT PRIMARY KEY,
    betrag DOUBLE NOT NULL,
    sponsor VARCHAR(300),
    beschreibung VARCHAR(300),
    gruppenid VARCHAR(36),
    FOREIGN KEY (gruppenid) REFERENCES gruppe_data(gruppenid)
);
CREATE TABLE IF NOT EXISTS gruppe_nutzer_relation
(
    gruppenid VARCHAR(36)
    nutzername VARCHAR(300),
    FOREIGN KEY (gruppenid) REFERENCES gruppe_data(gruppenid)
);
CREATE TABLE IF NOT EXISTS gruppe_transaktion_realtion
(
    nutzername VARCHAR(300)
    id INT
    FOREIGN KEY (id) REFERENCES transaktion_data(transaktionid)
);

