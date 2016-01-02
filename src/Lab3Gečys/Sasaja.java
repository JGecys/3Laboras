package Lab3Gečys;

import Lab2Gečys.Telefonas;
import org.omg.CORBA.Environment;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

/**
 * Created by Jurgis on 2015-10-08.
 */
public class Sasaja extends JFrame implements NaujasTelefonasSasaja.OnRegisterListener {

    private final JMenuBar meniuBaras = new JMenuBar();
    private Container sasajosLangoTurinys;
    private final JTextArea taInformacija = new JTextArea(20, 30);
    private final JScrollPane rightPanel = new JScrollPane(taInformacija, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private final JPanel paInfo = new JPanel();            // duomenų ir rezultatų panelis
    private JMenuItem miGamintojas;
    private JMenuItem miKaina;
    private JMenuItem mVisi;
    private JMenuItem mRegistruoti;
    private MyTableModel tableModel;
    private JTable lentelė = new JTable();
    private JLabel titleLabel = new JLabel("Visi Telelfonai", SwingConstants.CENTER);

    //    private AutomobiliuApskaita apskaita = new AutomobiliuApskaita(); // metodų klasės objektas
    private TelefonuDuomenys duomenys = new TelefonuDuomenys();

    /**
     * Klasės konstruktorius - nustatymai ir meniu įdiegimas.
     */
    public Sasaja() {

        // Nustatymai:
        Locale.setDefault(Locale.US); // suvienodiname skaičių formatus
        Font f = new Font("Courier New", Font.PLAIN, 12); // Suvienodinam simbolių pločius (lygiavimui JTextArea elemente)
        taInformacija.setFont(f);
        duomenys.setUpdateListener((tel -> taInformacija.append(tel.toString() + '\n')));

        meniuIdiegimas();
        InitializeTable();

        miGamintojas.setEnabled(false);    // neaktyvi, nes nėra duomenų (failas nenuskaitytas)
        miKaina.setEnabled(false);    // neaktyvi
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Suformuoja meniu, priskiria įvykius ir įdiegia jų veiksmus.
     * Sudėtingesni meniu komandos veiksmai realizuoti atskirais metodais ar atskira klase.
     */
    private void meniuIdiegimas() {
        this.setMinimumSize(new Dimension(800, 500));
        setJMenuBar(meniuBaras);
        JMenu mFailai = new JMenu("Failai");
        meniuBaras.add(mFailai);
        JMenu mAuto = new JMenu("Telefonų apskaita");
        mAuto.setMnemonic('a'); // Alt + a
        meniuBaras.add(mAuto);
        JMenu mPagalba = new JMenu("Pagalba");
        meniuBaras.add(mPagalba);

        //  Grupė  "Failai" :
        JMenuItem miSkaityti = new JMenuItem("Skaityti iš failo...");
        mFailai.add(miSkaityti);
        miSkaityti.addActionListener(this::veiksmaiSkaitantFailą);
        //miSkaityti.addActionListener((e) -> veiksmaiSkaitantFailą(e));  // galima kviesti ir taip
        //miSkaityti.addActionListener((e) -> veiksmaiSkaitantFailą());  // jei metodo antraštė be parametro, kviestume taip
        JMenuItem miBaigti = new JMenuItem("Pabaiga");
        miBaigti.setMnemonic('b'); // Alt + b
        mFailai.add(miBaigti);
        // kadangi išėjimo iš programos metodas trumpas, rašome vietoje
        miBaigti.addActionListener((ActionEvent e) -> System.exit(0));

        mVisi = new JMenuItem("Rodyti visus elementus");
        mVisi.setEnabled(false);
        mRegistruoti = new JMenuItem("Registracija");
        mRegistruoti.setEnabled(false);
        mRegistruoti.addActionListener(e1 -> {
            NaujasTelefonasSasaja naujas = new NaujasTelefonasSasaja();
            naujas.SetOnRegisterListener(this);
        });
        mAuto.add(mVisi);
        mAuto.add(mRegistruoti);
        mVisi.addActionListener(e -> {
            ((MyTableModel) lentelė.getModel()).showAll();
        });

        //	Grupė "Automobiliu apskaita"
        miGamintojas = new JMenuItem("Atranka pagal modelį…");
        mAuto.add(miGamintojas);
        miGamintojas.addActionListener(this::atrankaPagalMarke);

        miKaina = new JMenuItem("Surikiuoja pagal kainą");
        mAuto.add(miKaina);
        miKaina.addActionListener((ActionEvent event) -> {
            duomenys.rikiuotiPagalKaina(); // Klasės AutomobiliuApskaita metodas
            taInformacija.append("\nSurikiuota pagal kaina:\n");
            taInformacija.append(duomenys.toString());
        });
        taInformacija.setEditable(false);

        //    Grupė  "Pagalba" :
        JMenuItem miDokumentacija = new JMenuItem("Paketo Dokumentacija");
        mPagalba.add(miDokumentacija);
        miDokumentacija.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
                InputEvent.ALT_MASK));
        miDokumentacija.addActionListener(new VeiksmaiDokumentacija());
        JMenuItem miGreitojiPagalba = new JMenuItem("Greitoji Pagalba :-)");
        mPagalba.add(miGreitojiPagalba);
        miGreitojiPagalba.addActionListener((e) -> veiksmaiGreitojiPagalba());

        JMenuItem miApie = new JMenuItem("Apie programą ...");
        mPagalba.add(miApie);
        miApie.addActionListener((ActionEvent event) ->
                JOptionPane.showMessageDialog(Sasaja.this,
                        "Demo programa - sąsaja su meniu\nVersija 2.1\n2014 spalis",
                        "Apie...", JOptionPane.INFORMATION_MESSAGE));

        // Sukuriamas JPanel elementas informacijai išvesti ir padedamas į JFrame langą.
        BorderLayout borderLayout = new BorderLayout();
        borderLayout.setVgap(8);
        borderLayout.setHgap(8);
        paInfo.setLayout(borderLayout);
        rightPanel.setPreferredSize(new Dimension(300, 500));
        paInfo.add(rightPanel, BorderLayout.EAST);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // kad veiktų lango uždarymas ("kryžiukas")

    } // Metodo meniuIdiegimas pabaiga

    private void InitializeTable(){
        JPanel pa = new JPanel();
        JTextField editTextField = new JTextField();
        pa.setLayout(new BorderLayout());
        tableModel = new MyTableModel(lentelė, duomenys);
        lentelė.setModel(tableModel);
        JScrollPane juosta = new JScrollPane(lentelė);
        pa.add(titleLabel, BorderLayout.NORTH);
        pa.add(juosta, BorderLayout.CENTER);
        lentelė.setRowSorter(new TableRowSorter(tableModel)); // Rikiavimui pagal stulpelius su pele
        DefaultCellEditor editor = new DefaultCellEditor(editTextField);
        editor.setClickCountToStart(1);
        lentelė.setCellEditor(editor);
        DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
        tableCellRenderer.setHorizontalAlignment(JLabel.RIGHT);
        lentelė.getColumnModel().getColumn(2).setCellRenderer(tableCellRenderer);
        lentelė.getColumnModel().getColumn(3).setCellRenderer(tableCellRenderer);
        lentelė.getColumnModel().getColumn(4).setCellRenderer(tableCellRenderer);

        lentelė.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point p = e.getPoint();
                int row = lentelė.rowAtPoint(p);
                int col = lentelė.columnAtPoint(p);
                if (lentelė.isCellEditable(row, col)) {
                    TableCellEditor editor = lentelė.getCellEditor(row, col);
                    if (editor instanceof DefaultCellEditor) {
                        DefaultCellEditor ce = (DefaultCellEditor) editor;
                        if (e.getClickCount() < ce.getClickCountToStart()) return;
                    }
                    lentelė.editCellAt(row, col);
                }
            }
        });
        paInfo.add(pa, BorderLayout.CENTER);
    }

    /**
     * Metodas yra kviečiamas vykdant meniu punktą "Skaityti iš failo..."
     *
     * @param e klasės ActionEvent objektas. Jis būtinas metodo pagal nuorodą (::) iškvietimui
     *          (kviečiant šį metodą su lambda, jo antraštė gali būti ir be šio parametro).
     */
    public void veiksmaiSkaitantFailą(ActionEvent e) {
        JFileChooser fc = new JFileChooser("./Duomenys/");  // "." tam, kad rodytų projekto katalogą
        fc.setDialogTitle("Atidaryti failą skaitymui");
        fc.setApproveButtonText("Atidaryti");
        int rez = fc.showOpenDialog(Sasaja.this);
        if (rez == JFileChooser.APPROVE_OPTION) {
            // veiksmai, kai pasirenkamas atsakymas “Open"
            taInformacija.setText("");
            if (!paInfo.isShowing()) {
                // Jei JPanel objektas paInfo dar neįdėtas į JFrame
                sasajosLangoTurinys = getContentPane();
//                sasajosLangoTurinys.setLayout(new FlowLayout());
                sasajosLangoTurinys.add(paInfo);
                validate();
            }


            File f1 = fc.getSelectedFile();
            duomenys.fromFile(f1);

            mVisi.setEnabled(true);
            miGamintojas.setEnabled(true);    // aktyvi - duomenys nuskaityti
            miKaina.setEnabled(true);
            mRegistruoti.setEnabled(true);
        } else if (rez == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(Sasaja.this, // kad rodyti sąsajos lango centre (null rodytų ekrano centre)
                    "Skaitymo atsisakyta (paspaustas ESC arba Cancel)",
                    "Skaitymas - rašymas", JOptionPane.INFORMATION_MESSAGE);
        }
    } // metodo veiksmaiSkaitantFailą pabaiga

    /**
     * Atrenka automobilius pagal pasirinktą jų markę. Markė įvedama įvedimo lange (JOptionPane.showInputDialog)
     *
     * @param e klasės ActionEvent objektas.
     */
    public void atrankaPagalMarke(ActionEvent e) {

        String gamintojas = JOptionPane.showInputDialog(Sasaja.this, "Įveskite gamintoją:", // vietoje SasajaSuMeniu.this parašius null, įvedimo langas nebus sąsajos lange
                "", JOptionPane.WARNING_MESSAGE);
        if (gamintojas == null) // Kai pasirinkta Cancel arba Esc
            return;

        if(tableModel.AtrinktiPagalGamintoja(gamintojas)){
            titleLabel.setText("Atrinkti " + gamintojas + " gamintojo telefonai:");
            lentelė.revalidate();
        }else{
            titleLabel.setText("Tokiu telefonu nerasta.");
        }

    } // Metodo atrankaPagalMarke pabaiga

    /**
     * Išorinės programos (C:\Windows\System32\calc.exe) iškvietimo pavyzdys.
     */
    public void veiksmaiGreitojiPagalba() {
        // Galimos programos vardo (exe failo) suformavimo alternatyvos:
        // Pirma:
        //String programa = System.getenv("windir") + File.separatorChar +
        //					 "system32" + File.separatorChar + "calc.exe";

        // Kita:
        String programa = "C:\\WINDOWS\\system32\\calc.exe";
        try {
            Process p = Runtime.getRuntime().exec(programa);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Sasaja.this,
                    "Vykdomasis failas <" + programa + "> nerastas",
                    "Klaida", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void OnRegister(Telefonas t) {
        tableModel.PridetiTelefona(t);
    }

    /**
     * Įvykių apdorojimas atskira klase.
     * Tai gali būti tiek vidinė (kaip šiuo atveju), tiek ir išorinė (ne private) klasė.
     * Tai ypač patogiau tada, kai įvykis turi daug veiksmų (daug kodo), kuriuos norime patalpinti kitame javos faile.
     */

    private class VeiksmaiDokumentacija implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            File f = null;
            try {
                f = new File("dist\\javadoc\\index.html").getAbsoluteFile();
                Desktop.getDesktop().open(f);
                JOptionPane.showMessageDialog(Sasaja.this,
                        "Dokumentacija sėkmingai atidaryta naršyklės lange",
                        "Informacija", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(Sasaja.this,
                        "Dokumentacijos failas <" + f.toString() +
                                "> nerastas(arba dokumentacija dar nesugeneruota)\n"
                        , "Klaida", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(Sasaja.this,
                        "Dokumentacijos failo <" + f.toString() +
                                "> atidaryti nepavyko", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Programos paleidimo taškas: sukuriamas JFrame klasės vaiko (SasajaSuMeniu) objektas,
     * nustatomas lango dydis ir vieta ir langas parodomas ekrane (metodas setVisible).
     *
     * @param args argumentų masyvas.
     */
    public static void main(String[] args) {
        Sasaja langas = new Sasaja();
        langas.setSize(500, 400);
        langas.setLocation(200, 200);
        langas.setTitle("LD3 demo: Vartotojo sąsajos su meniu  p a v y z d y s");
        langas.setVisible(true);
    }

}
