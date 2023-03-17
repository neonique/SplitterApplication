CREATE TABLE IF NOT EXISTS gruppe_data
(
    gruppenid VARCHAR(36),
    gruppenintid INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gruppenname VARCHAR(300),
    geschlossen BOOLEAN
);
CREATE TABLE IF NOT EXISTS transaktion_data
(
    transaktionid INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    betrag DOUBLE NOT NULL,
    sponsor VARCHAR(300),
    beschreibung VARCHAR(300),
    gruppenintid INTEGER,
    FOREIGN KEY (gruppenintid) REFERENCES gruppe_data(gruppenintid)
);
CREATE TABLE IF NOT EXISTS gruppe_nutzer_data
(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    gruppenintid INTEGER,
    nutzername VARCHAR(300),
    FOREIGN KEY (gruppenintid) REFERENCES gruppe_data(gruppenintid)
);
CREATE TABLE IF NOT EXISTS transaktion_nutzer_data
(
    id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
    nutzername VARCHAR(300),
    transaktionid INTEGER,
    FOREIGN KEY (transaktionid) REFERENCES transaktion_data(transaktionid)
);