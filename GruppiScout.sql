   DROP TABLE GRUPPO,EVENTI,RAGAZZO,CAPO,BRANCA,PARTECIPA_RAG,PARTECIPA_CAP;
   DROP DATABASE Scouts;
   
   CREATE DATABASE Scouts;
    USE Scouts;
    
    create table GRUPPO
    (
    Codice_Gruppo varchar(20) primary key,
    Nome varchar(20) not null
    );
    
    
    create table SEDE
    (
    Codice_Sede varchar(20) primary key,
    Città varchar(20) not null,
    Via varchar(20) not null,
    NumeroCV numeric(3) not null,
    CodGruppo varchar(20),
    FOREIGN Key (CodGruppo) references GRUPPO (Codice_Gruppo)
    );
    
    create table BRANCA
    (
    Codice_Branca varchar(20) primary key,
    Nome varchar(50) not null,
    Tipo varchar(25) not null
    );
    
    create table RAGAZZO
    (
    Codice_Rag varchar(20) primary key,
    Nome varchar(20) not null,
    Cognome varchar(20) not null,
    Data_Nascita date not null,
    CodGruppo varchar(20) not null,
    CodBranca varchar(20) not null,
    FOREIGN Key (CodGruppo) references GRUPPO (Codice_Gruppo),
	FOREIGN Key (CodBranca) references BRANCA (Codice_Branca)
    );
    
    create table SPECIALITA
    (
    Codice_Specialita INT AUTO_INCREMENT PRIMARY KEY,
    CodRagazzo varchar(20),
    Nome varchar(20),
    -- primary key(CodRagazzo),
    FOREIGN Key (CodRagazzo) references RAGAZZO (Codice_Rag)
    );
    
    
	create table CAPO
    (
    Codice_Cap varchar(20) primary key,
    Nome varchar(20) not null,
    Cognome varchar(20) not null,
    Data_Nascita date not null,
    CodGruppo varchar(20) not null,
    CodBranca_APP varchar(20) not null,
    CodBranca_SUP varchar(20) default null,
    FOREIGN Key (CodGruppo) references GRUPPO (Codice_Gruppo),
	FOREIGN Key (CodBranca_APP) references BRANCA (Codice_Branca),
    FOREIGN Key (CodBranca_SUP) references BRANCA (Codice_Branca)
    );


    create table TIPO_FORMAZIONE
    (
    Codice_Formazione INT AUTO_INCREMENT PRIMARY KEY,
    CodCapo varchar(20),
    Tipo varchar(20),
    FOREIGN Key (CodCapo) references CAPO (Codice_Cap)
    );    
    
    
    create table EVENTI
    (
    Codice_Evento varchar(20) primary key,
    Nome varchar(40) not null,
    Tipo varchar(20) not null,
    Descrizione varchar(200)
    );
    
    
    create table PARTECIPA_RAG
    (
    CodEvento varchar(20),
	CodRagazzo varchar(20),
    Data_Partecipazione date,
    primary key(CodEvento, CodRagazzo),
    FOREIGN Key (CodEvento) references EVENTI (Codice_Evento),
    FOREIGN Key (CodRagazzo) references RAGAZZO (Codice_Rag)
    );
    
    
    create table PARTECIPA_CAP
    (
    CodEvento varchar(20),
	CodCapo varchar(20),
    Data_Partecipazione date,
    primary key(CodEvento, CodCapo),
    FOREIGN Key (CodEvento) references EVENTI (Codice_Evento),
    FOREIGN Key (CodCapo) references CAPO (Codice_Cap)
    );
    
    
    -- Inserimento dati nella tabella GRUPPO
INSERT INTO GRUPPO (Codice_Gruppo, Nome) VALUES 
('G1', 'Napoli 1'),
('G2', 'Roma 1'),
('G3', 'Milano 1'),
('G4', 'Torino 1'),
('G5', 'Firenze 1');

-- Inserimento dati nella tabella SEDE
INSERT INTO SEDE (Codice_Sede, Città, Via, NumeroCV, CodGruppo) VALUES 
('S1', 'Roma', 'Via Roma', 10, 'G2'),
('S2', 'Milano', 'Via Appia', 15, 'G3'),
('S3', 'Napoli', 'Via Toledo', 20, 'G1'),
('S4', 'Torino', 'Via Aldo Moro', 12, 'G4'),
('S5', 'Firenze', 'Via Roma', 18, 'G5');

-- Inserimento dati nella tabella BRANCA
INSERT INTO BRANCA (Codice_Branca, Nome, Tipo) VALUES 
('B1', 'Branca Popolo Libero', 'L/C'),
('B2', 'Branca Antares Andromeda', 'E/G'),
('B3', 'Branca Orizzonti', 'R/S'),
('B4', 'Comunità Capi', 'Capi'),
('B5', 'Branca Aquile Randagie', 'R/S'),
('B6', 'Branca Vulcani Sputanti', 'E/G');


-- Inserimento dati nella tabella RAGAZZO
INSERT INTO RAGAZZO (Codice_Rag, Nome, Cognome, Data_Nascita, CodGruppo, CodBranca) VALUES 
('R1', 'Mario', 'Rossi', '2005-05-15', 'G1', 'B1'),
('R2', 'Luca', 'Bianchi', '2006-08-20', 'G2', 'B2'),
('R3', 'Giovanni', 'Verdi', '2007-10-10', 'G3', 'B3'),
('R4', 'Giulia', 'Ricci', '2004-04-25', 'G1', 'B1'),
('R5', 'Simone', 'Ferraro', '2003-09-15', 'G2', 'B2'),
('R6', 'Laura', 'Esposito', '2005-06-10', 'G3', 'B3');

-- Inserimento dati nella tabella SPECIALITA
INSERT INTO SPECIALITA (CodRagazzo, Nome) VALUES 
('R1', 'Primo Soccorso'),
('R1', 'Campeggio'),
('R2', 'Primo Soccorso'),
('R2', 'Escursionismo'),
('R3', 'Arte');

-- Inserimento dati nella tabella CAPO
INSERT INTO CAPO (Codice_Cap, Nome, Cognome, Data_Nascita, CodGruppo, CodBranca_APP, CodBranca_SUP) VALUES 
('C1', 'Anna', 'Ferrari', '1980-03-25', 'G1', 'B4', 'B2'),
('C2', 'Laura', 'Rizzo', '1975-12-12', 'G2', 'B4', 'B3'),
('C3', 'Marco', 'De Luca', '1982-08-30', 'G3', 'B4', NULL),
('C4', 'Roberto', 'Romano', '1978-01-12', 'G1', 'B4', NULL),
('C5', 'Elena', 'Marini', '1983-05-30', 'G2', 'B4', NULL),
('C6', 'Francesco', 'Conti', '1975-11-08', 'G3', 'B4', NULL);

-- Inserimento dati nella tabella TIPO_FORMAZIONE
INSERT INTO TIPO_FORMAZIONE (CodCapo, Tipo) VALUES 
('C1', 'CFM E/G'),
('C2', 'CFM R/S'),
('C3', 'CFT');

-- Inserimento dati nella tabella EVENTI
INSERT INTO EVENTI (Codice_Evento, Nome, Tipo, Descrizione) VALUES 
('E1', 'Campo estivo', 'Campo', 'Campo estivo di 1 settimana'),
('E2', 'Escursione in montagna', 'Escursione', 'Escursione di 2 giorni sulle montagne'),
('E3', 'Gita culturale', 'Gita', 'Visita ai musei della città');

-- Inserimento dati nella tabella PARTECIPA_RAG
INSERT INTO PARTECIPA_RAG (CodEvento, CodRagazzo, Data_Partecipazione) VALUES 
('E1', 'R1', '2023-07-20'),
('E1', 'R2', '2023-07-20'),
('E2', 'R3', '2023-08-10');


-- Inserimento dati nella tabella PARTECIPA_CAP
INSERT INTO PARTECIPA_CAP (CodEvento, CodCapo, Data_Partecipazione) VALUES 
('E3', 'C1', '2023-07-20'),
('E2', 'C2', '2023-08-09');

    
    
-- I GRUPPI CHE PARTECIPANO AD UN DETERMINATO EVENTO
SELECT EVENTI.Nome as Nome_Evento, GRUPPO.Codice_Gruppo, GRUPPO.Nome as Nome_Gruppo
FROM GRUPPO, RAGAZZO, PARTECIPA_RAG, EVENTI
WHERE EVENTI.Codice_Evento = PARTECIPA_RAG.CodEvento AND PARTECIPA_RAG.CodRagazzo = RAGAZZO.Codice_Rag AND RAGAZZO.CodGruppo = GRUPPO.Codice_Gruppo

UNION

SELECT EVENTI.Nome as Nome_Evento, GRUPPO.Codice_Gruppo, GRUPPO.Nome as Nome_Gruppo
FROM GRUPPO, CAPO, PARTECIPA_CAP, EVENTI
WHERE EVENTI.Codice_Evento = PARTECIPA_CAP.CodEvento AND PARTECIPA_CAP.CodCapo = CAPO.Codice_CAP AND CAPO.CodGruppo = GRUPPO.Codice_Gruppo
order by Nome_Evento;



-- I CAPI CHE SONO ASSEGNATI AD UNA BRANCA
SELECT CAPO.Nome, CAPO.Cognome, b1.Nome as Branca_Appartenenza, b2.Nome as Branca_Assegnato, b2.Tipo as Tipo_Assegnato
FROM CAPO, BRANCA b1, BRANCA b2
WHERE CAPO.CodBranca_APP = b1.Codice_Branca AND CAPO.CodBranca_SUP = b2.Codice_Branca;



-- IL NUMERO DI ASSOCIATI PER OGNI BRANCA
SELECT b.Codice_Branca, b.Nome AS Nome_Branca, COUNT(DISTINCT r.Codice_Rag) + COUNT(DISTINCT c.Codice_Cap) AS Totale_Associati
FROM BRANCA b
LEFT JOIN RAGAZZO r ON b.Codice_Branca = r.CodBranca
LEFT JOIN CAPO c ON b.Codice_Branca = c.CodBranca_APP OR b.Codice_Branca = c.CodBranca_SUP
GROUP BY b.Codice_Branca , b.Nome;



-- IL NUMERO DI SPECIALITA' DI OGNI RAGAZZO
SELECT r.Codice_Rag, r.Nome, r.Cognome, COUNT(DISTINCT s.CodRagazzo, s.Nome) AS Numero_SPECIALITA
FROM RAGAZZO r
LEFT JOIN SPECIALITA s ON r.Codice_Rag = s.CodRagazzo 
GROUP BY r.Codice_Rag, r.Nome, r.Cognome;



-- IL NUMERO DI FORMAZIONI DI OGNI CAPO
SELECT c.Codice_Cap, c.Nome, c.Cognome, COUNT(DISTINCT f.CodCapo, f.Tipo) AS Numero_Formazioni
FROM CAPO c
LEFT JOIN TIPO_FORMAZIONE f ON c.Codice_Cap = f.CodCapo
GROUP BY c.Codice_Cap, c.Nome, c.Cognome;


-- LE SEDI CHE APPARTENGONO AI GRUPPI
SELECT g.Nome AS Nome_Gruppo, s.Codice_Sede, s.Città, s.Via, s.NumeroCV
FROM GRUPPO g, SEDE s
WHERE g.Codice_Gruppo = s.CodGruppo;  

-- OPERAZIONE NUM ASSOCIATI
SELECT g.Nome AS Nome_Gruppo, COUNT(DISTINCT r.Codice_Rag) + COUNT(DISTINCT c.Codice_Cap) AS Totale_Associati
FROM GRUPPO g
LEFT JOIN RAGAZZO r ON g.Codice_Gruppo  = r.CodGruppo
LEFT JOIN CAPO c ON g.Codice_Gruppo = c.CodGruppo
GROUP BY g.Nome;


/*SELECT r.Nome, r.Cognome, g.Nome as Nome_Gruppo
FROM RAGAZZO r, GRUPPO g
WHERE r.CodGruppo = g.Codice_Gruppo
ORDER BY g.Codice_Gruppo*/
    