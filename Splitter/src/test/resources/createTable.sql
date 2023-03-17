CREATE TABLE IF NOT EXISTS gruppe_data
(
    gruppenid VARCHAR(36),
    gruppenIntId INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gruppenname VARCHAR(300),
    geschlossen BOOLEAN
);
CREATE TABLE IF NOT EXISTS transaktion_data
(
    transaktionid INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    betrag DOUBLE NOT NULL,
    sponsor VARCHAR(300),
    beschreibung VARCHAR(300),
    gruppenid VARCHAR(36),
    FOREIGN KEY (gruppenid) REFERENCES gruppe_data(gruppenid)
);
CREATE TABLE IF NOT EXISTS gruppe_nutzer_relation
(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gruppenid VARCHAR(36),
    nutzername VARCHAR(300),
    FOREIGN KEY (gruppenid) REFERENCES gruppe_data(gruppenid)
);
CREATE TABLE IF NOT EXISTS nutzer_transaktion_relation
(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nutzername VARCHAR(300),
    transaktionid INTEGER,
    FOREIGN KEY (transaktionid) REFERENCES transaktion_data(transaktionid)
);