CREATE TABLE IF NOT EXISTS gruppe_data
(
    gruppenid VARCHAR(36),
    gruppenintid SERIAL PRIMARY KEY,
    gruppenname VARCHAR(300),
    geschlossen BOOLEAN
    );
CREATE TABLE IF NOT EXISTS transaktion_data
(
    transaktionid SERIAL PRIMARY KEY,
    betrag NUMERIC(8,2) NOT NULL,
    sponsor VARCHAR(300),
    beschreibung VARCHAR(300),
    gruppenintid INTEGER,
    FOREIGN KEY (gruppenintid) REFERENCES gruppe_data(gruppenintid)
    );
CREATE TABLE IF NOT EXISTS gruppe_nutzer_relation
(
    id SERIAL PRIMARY KEY,
    gruppenintid INTEGER,
    nutzername VARCHAR(300),
    FOREIGN KEY (gruppenintid) REFERENCES gruppe_data(gruppenintid)
    );
CREATE TABLE IF NOT EXISTS nutzer_transaktion_relation
(
    id SERIAL PRIMARY KEY,
    nutzername VARCHAR(300),
    transaktionid INTEGER,
    FOREIGN KEY (transaktionid) REFERENCES transaktion_data(transaktionid)
    );