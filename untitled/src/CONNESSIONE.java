import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class CONNESSIONE {
    private Connection con;
    public CONNESSIONE(){

        try{
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            String url = "jdbc:mysql://localhost:3306/Scouts";
            con = DriverManager.getConnection(url,"root", "angelo@2015");
            System.out.println("Connessione OK \n");
            //con.close();
        }
        catch(Exception e) {
            System.out.println("Connessione Fallita \n");
            System.out.println(e);
        }
    }


    public JTable stampaTabbelle(String s) {
        JTable jTable = new JTable();

        try {
            Statement st = con.createStatement();
            String string = "SELECT * FROM " + s;
            ResultSet rs = st.executeQuery(string);

            // Ottieni il numero di colonne dal ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();

            // Crea un vettore per memorizzare i nomi delle colonne
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= numColumns; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Crea un vettore per memorizzare i dati delle righe
            Vector<Vector<Object>> rowData = new Vector<>();
            while (rs.next()) {
                // Crea un vettore per memorizzare i dati di una riga
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= numColumns; i++) {
                    row.add(rs.getObject(i));
                }
                // Aggiungi il vettore della riga al vettore delle righe
                rowData.add(row);
            }

            // Crea un DefaultTableModel utilizzando i dati estratti
            DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);

            // Imposta il DefaultTableModel sulla JTable
            jTable.setModel(tableModel);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jTable;
    }



    public List<String> getNomiAttributi(String nomeTabella) {
        List<String> nomiAttributi = new ArrayList<>();

        try {
            DatabaseMetaData metaData = con.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, nomeTabella, null);

            while (rs.next()) {
                String nomeAttributo = rs.getString("COLUMN_NAME");
                nomiAttributi.add(nomeAttributo);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Errore durante il recupero dei nomi degli attributi", e);
        }

        return nomiAttributi;
    }


    public void inserisci(List<String> list, String tipo){
        try {
            Statement st = con.createStatement();

            // Ottieni i nomi delle colonne della tabella
            ResultSet rs = st.executeQuery("SELECT * FROM " + tipo + " LIMIT 1");
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 1; i <= numColumns; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Costruisci la stringa di inserimento con i nomi delle colonne
            StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + tipo + " (");
            queryBuilder.append(String.join(", ", columnNames));
            queryBuilder.append(") VALUES (");

            // Aggiungi i valori dalla lista alla query
            Iterator<String> iterator = list.iterator(); // Cambia il tipo di iteratore a String
            while (iterator.hasNext()) {
                String value = iterator.next(); // Prendi il valore di tipo String
                queryBuilder.append("'" + value + "'");
                if (iterator.hasNext()) {
                    queryBuilder.append(", ");
                }
            }
            queryBuilder.append(")");

            // Esegui l'inserimento
            int n = st.executeUpdate(queryBuilder.toString());
            System.out.println("Record inserito correttamente: " + n);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(String tipo, String condition) {
        try {
            Statement statement = con.createStatement();
            String query = "DELETE FROM " + tipo + " WHERE " + condition;
            int deletedRows = statement.executeUpdate(query);
            System.out.println("Righe eliminate: " + deletedRows);
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'eliminazione dei dati", e);
        }
    }

    public JTable queryPassate(String query){
        JTable jTable = new JTable();

        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            // Ottieni il numero di colonne dal ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();

            // Crea un vettore per memorizzare i nomi delle colonne
            Vector<String> columnNames = new Vector<>();
            for (int i = 1; i <= numColumns; i++) {
                columnNames.add(metaData.getColumnName(i));
            }

            // Crea un vettore per memorizzare i dati delle righe
            Vector<Vector<Object>> rowData = new Vector<>();
            while (rs.next()) {
                // Crea un vettore per memorizzare i dati di una riga
                Vector<Object> row = new Vector<>();
                for (int i = 1; i <= numColumns; i++) {
                    row.add(rs.getObject(i));
                }
                // Aggiungi il vettore della riga al vettore delle righe
                rowData.add(row);
            }

            // Crea un DefaultTableModel utilizzando i dati estratti
            DefaultTableModel tableModel = new DefaultTableModel(rowData, columnNames);

            // Imposta il DefaultTableModel sulla JTable
            jTable.setModel(tableModel);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return jTable;
    }



    public JTable selezionaQuery(String s){

        if (s.compareToIgnoreCase("NumeroAssociati") == 0)
            return queryPassate("SELECT g.Nome AS Nome_Gruppo, COUNT(DISTINCT r.Codice_Rag) + COUNT(DISTINCT c.Codice_Cap) AS Totale_Associati FROM GRUPPO g LEFT JOIN RAGAZZO r ON g.Codice_Gruppo  = r.CodGruppo LEFT JOIN CAPO c ON g.Codice_Gruppo = c.CodGruppo GROUP BY g.Nome;");

        if(s.compareToIgnoreCase("Le Sedi che Appartengono ai Gruppi ") == 0)
            return queryPassate("SELECT g.Nome AS Nome_Gruppo, s.Codice_Sede, s.Città, s.Via, s.NumeroCV\n" +
                    "FROM GRUPPO g, SEDE s\n" +
                    "WHERE g.Codice_Gruppo = s.CodGruppo;");

        if(s.compareToIgnoreCase("I GRUPPI CHE PARTECIPANO AD UN DETERMINATO EVENTO") == 0)
            return queryPassate("SELECT EVENTI.Nome as Nome_Evento, GRUPPO.Codice_Gruppo, GRUPPO.Nome as Nome_Gruppo FROM GRUPPO, RAGAZZO, PARTECIPA_RAG, EVENTI WHERE EVENTI.Codice_Evento = PARTECIPA_RAG.CodEvento AND PARTECIPA_RAG.CodRagazzo = RAGAZZO.Codice_Rag AND RAGAZZO.CodGruppo = GRUPPO.Codice_Gruppo  UNION SELECT EVENTI.Nome as Nome_Evento, GRUPPO.Codice_Gruppo, GRUPPO.Nome as Nome_Gruppo FROM GRUPPO, CAPO, PARTECIPA_CAP, EVENTI WHERE EVENTI.Codice_Evento = PARTECIPA_CAP.CodEvento AND PARTECIPA_CAP.CodCapo = CAPO.Codice_CAP AND CAPO.CodGruppo = GRUPPO.Codice_Gruppo order by Nome_Evento");

        return null;
    }


    public void updateBranca(String codA, String codB, String tipo){
        try {
            Statement statement = con.createStatement();
            String query;
            if(tipo.compareToIgnoreCase("CAPO")== 0) {
                query = "UPDATE " + tipo + " SET CodBranca_SUP = " + codB + " WHERE Codice_Cap = '" + codA + "'" ;
            }else if (tipo.compareToIgnoreCase("RAGAZZO")== 0){
                query = "UPDATE " + tipo + " SET CodBranca = '" + codB + "' WHERE Codice_Rag = '" + codA + "'" ;
            }else return;
            int resultUpdate = statement.executeUpdate(query);
            System.out.println("Righe eliminate: " + resultUpdate );
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante L'update dei dati", e);
        }
    }

 

    public void chiudiConnessione(){
        try {
            con.close();
            System.out.print("Connessione chiusa");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
