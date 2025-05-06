import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Grafic extends JFrame {
    private JPanel panelPrincipal;
    private JPanel panel2;
    private JPanel panel3;
    private JComboBox jComboBox;
    private JButton visualizzaButton;
    private JButton aggiungiButton;
    private JButton delte;
    private JButton update;
    private JComboBox jComboBox2;
    private JButton esegui;
    private CONNESSIONE connessione;

    public Grafic(){
        connessione = new CONNESSIONE();

        jComboBox = new JComboBox();
        jComboBox.addItem("");
        jComboBox.addItem("GRUPPO");
        jComboBox.addItem("EVENTI");
        jComboBox.addItem("RAGAZZO");
        jComboBox.addItem("CAPO");
        jComboBox.addItem("BRANCA");
        jComboBox.addItem("SEDE");
        jComboBox.addItem("PARTECIPA_RAG");
        jComboBox.addItem("PARTECIPA_CAP");


        visualizzaButton = new JButton("Visuaalizza");
        visualizzaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(jComboBox.getSelectedItem() == "")
                    return;


                JPanel jPanelrun = new JPanel(new FlowLayout());
                jPanelrun.add(connessione.stampaTabbelle(String.valueOf( jComboBox.getSelectedItem())).getTableHeader());
                jPanelrun.add(connessione.stampaTabbelle(String.valueOf( jComboBox.getSelectedItem())));
                JFrame jFrameRun = new JFrame();
                jFrameRun.setTitle(String.valueOf( jComboBox.getSelectedItem()));
                jFrameRun.add(jPanelrun);
                jFrameRun.setVisible(true);
                jFrameRun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameRun.setSize(400,300);

                //connessione.chiudiConnessione();

            }
        });



        aggiungiButton = new JButton("Aggiungi");
        aggiungiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBox.getSelectedItem() == "")
                    return;



                JPanel jPanelrun2 = new JPanel(new BorderLayout());
                JPanel jPanelrun = new JPanel(new FlowLayout());

                JButton aggiungi = new JButton("Aggiungi");

                List<String> stringList = connessione.getNomiAttributi(String.valueOf( jComboBox.getSelectedItem()));
                List<JTextField> jTextFieldList = new ArrayList<>();
                for (String s : stringList) {
                    JTextField jTextField = new JTextField(s);
                    jTextField.setColumns(10);
                    jPanelrun.add(jTextField);
                    jTextFieldList.add(jTextField);
                }



                jPanelrun2.add(jPanelrun, BorderLayout.CENTER);
                jPanelrun2.add(aggiungi, BorderLayout.SOUTH);

                aggiungi.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        List<String> stringList1 = new ArrayList<>();
                        for(int i = 0; i < stringList.size(); i++){
                            stringList1.add(jTextFieldList.get(i).getText());
                        }

                        connessione.inserisci(stringList1, String.valueOf( jComboBox.getSelectedItem()));
                    }
                });

                JFrame jFrameRun = new JFrame();
                jFrameRun.setTitle("Aggiungi " + String.valueOf(jComboBox.getSelectedItem()));
                jFrameRun.add(jPanelrun2);
                jFrameRun.setVisible(true);
                jFrameRun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameRun.setSize(600,300);

            }
        });




        delte = new JButton("Delete");
        delte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBox.getSelectedItem() == "")
                    return;


                JPanel jPanelrun2 = new JPanel(new FlowLayout());
                JTextField jTextField = new JTextField("Condizione");
                jTextField.setColumns(25);
                JButton cancella = new JButton("Cancella");


                jPanelrun2.add(jTextField);
                jPanelrun2.add(cancella);


                cancella.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        connessione.delete(String.valueOf(jComboBox.getSelectedItem()), jTextField.getText());

                    }
                });


                JFrame jFrameRun = new JFrame();
                jFrameRun.setTitle("Delete " + String.valueOf(jComboBox.getSelectedItem()));
                jFrameRun.add(jPanelrun2);
                jFrameRun.setVisible(true);
                jFrameRun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameRun.setSize(600,300);



            }
        });


        update = new JButton("UpdateBranca");
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBox.getSelectedItem() != "CAPO" && jComboBox.getSelectedItem() != "RAGAZZO")
                    return;


                JTextField jTextFieldB = new JTextField("Codice Nuova Branca");
                JTextField jTextFieldA = new JTextField("Codice Associato");
                JButton jButtonrun = new JButton("UPDATE");
                jButtonrun.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        connessione.updateBranca(jTextFieldA.getText(), jTextFieldB.getText(), String.valueOf(jComboBox.getSelectedItem()));
                    }
                });


                JPanel jPanelrun = new JPanel(new FlowLayout());
                jTextFieldA.setColumns(10);
                jTextFieldB.setColumns(10);
                jPanelrun.add(jTextFieldA);
                jPanelrun.add(jTextFieldB);
                jPanelrun.add(jButtonrun);


                JFrame jFrameRun = new JFrame();
                jFrameRun.setTitle("Update " + String.valueOf(jComboBox.getSelectedItem()));
                jFrameRun.add(jPanelrun);
                jFrameRun.setVisible(true);
                jFrameRun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameRun.setSize(400,300);
            }
        });




        panel2 = new JPanel();
        panel2.setLayout(new FlowLayout());
        panel2.add(visualizzaButton);
        panel2.add(jComboBox);
        panel2.add(aggiungiButton);
        panel2.add(delte);
        panel2.add(update);




        jComboBox2 = new JComboBox();
        jComboBox2.addItem("");
        jComboBox2.addItem("NumeroAssociati");
        jComboBox2.addItem("Le Sedi che Appartengono ai Gruppi ");
        jComboBox2.addItem("I GRUPPI CHE PARTECIPANO AD UN DETERMINATO EVENTO");



        esegui = new JButton("Esegui");
        esegui.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jComboBox2.getSelectedItem() == "")
                    return;


                JPanel jPanelrun = new JPanel(new FlowLayout());
                jPanelrun.add(connessione.selezionaQuery(String.valueOf(jComboBox2.getSelectedItem())).getTableHeader());
                jPanelrun.add(connessione.selezionaQuery(String.valueOf(jComboBox2.getSelectedItem())));
                JFrame jFrameRun = new JFrame();
                jFrameRun.setTitle(String.valueOf(jComboBox2.getSelectedItem()));
                jFrameRun.add(jPanelrun);
                jFrameRun.setVisible(true);
                jFrameRun.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                jFrameRun.setSize(400,300);
            }
        });


        panel3 = new JPanel();
        panel3.setLayout(new FlowLayout());
        panel3.add(jComboBox2);
        panel3.add(esegui);




        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(panel2, BorderLayout.NORTH);
        panelPrincipal.add(panel3, BorderLayout.SOUTH);

        getContentPane().add(panelPrincipal);

    }


    public static void main(String[] args) {
        Grafic g = new Grafic();
        g.setVisible(true);
        g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        g.setSize(700,400);

    }




}
