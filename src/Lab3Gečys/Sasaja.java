package Lab3Gečys;

import Lab2Gečys.Telefonas;
import sasajaSuMeniu.AutomobiliuApskaita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Jurgis on 2015-10-08.
 */
public class Sasaja extends JFrame {

    private final JMenuBar meniuBaras = new JMenuBar();
    private Container sasajosLangoTurinys;
    private final JTextArea taInformacija = new JTextArea(20, 50);
    private final JScrollPane zona = new JScrollPane(taInformacija);
    private final JLabel laAntraste = new JLabel("Rezultatai");
    private final JPanel paInfo = new JPanel();            // duomenų ir rezultatų panelis
    private JMenuItem miMarkė;
    private JMenuItem miKaina;

    private AutomobiliuApskaita apskaita = new AutomobiliuApskaita(); // metodų klasės objektas
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

        miMarkė.setEnabled(false);    // neaktyvi, nes nėra duomenų (failas nenuskaitytas)
        miKaina.setEnabled(false);    // neaktyvi
    }

    /**
     * Suformuoja meniu, priskiria įvykius ir įdiegia jų veiksmus.
     * Sudėtingesni meniu komandos veiksmai realizuoti atskirais metodais ar atskira klase.
     */
    private void meniuIdiegimas() {
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

        //	Grupė "Automobiliu apskaita"
        miMarkė = new JMenuItem("Atranka pagal modelį…");
        mAuto.add(miMarkė);
        miMarkė.addActionListener(this::atrankaPagalMarke);

        miKaina = new JMenuItem("Surikiuoja pagal kainą");
        mAuto.add(miKaina);
        miKaina.addActionListener((ActionEvent event) -> {
            apskaita.rikiuojaPagalKainą(); // Klasės AutomobiliuApskaita metodas
            taInformacija.append("\n       SURIKIUOTA (pradinis sąrašas):\n");
            taInformacija.append(apskaita.toString());
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
        paInfo.setLayout(new BorderLayout());
        paInfo.add(laAntraste, BorderLayout.NORTH);
        paInfo.add(zona, BorderLayout.CENTER);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // kad veiktų lango uždarymas ("kryžiukas")

    } // Metodo meniuIdiegimas pabaiga

    /**
     * Metodas yra kviečiamas vykdant meniu punktą "Skaityti iš failo..."
     *
     * @param e klasės ActionEvent objektas. Jis būtinas metodo pagal nuorodą (::) iškvietimui
     *          (kviečiant šį metodą su lambda, jo antraštė gali būti ir be šio parametro).
     */
    public void veiksmaiSkaitantFailą(ActionEvent e) {
        JFileChooser fc = new JFileChooser(".");  // "." tam, kad rodytų projekto katalogą
        fc.setDialogTitle("Atidaryti failą skaitymui");
        fc.setApproveButtonText("Atidaryti");
        int rez = fc.showOpenDialog(Sasaja.this);
        if (rez == JFileChooser.APPROVE_OPTION) {
            // veiksmai, kai pasirenkamas atsakymas “Open"
            taInformacija.setText("");
            if (!paInfo.isShowing()) {
                // Jei JPanel objektas paInfo dar neįdėtas į JFrame
                sasajosLangoTurinys = getContentPane();
                sasajosLangoTurinys.setLayout(new FlowLayout());
                sasajosLangoTurinys.add(paInfo);
                validate();
            }


            File f1 = fc.getSelectedFile();
            duomenys.fromFile(f1);
            SugrupuotiTelefonai sugrupuoti = new SugrupuotiTelefonai();
            sugrupuoti.fromList(duomenys, SugrupuotiTelefonai.Tipas.GAMINTOJAS);
            System.out.println(sugrupuoti);


            miMarkė.setEnabled(true);    // aktyvi - duomenys nuskaityti
            miKaina.setEnabled(true);
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
        String markė = JOptionPane.showInputDialog(Sasaja.this, "Įveskite markę", // vietoje SasajaSuMeniu.this parašius null, įvedimo langas nebus sąsajos lange
                "Tekstas", JOptionPane.WARNING_MESSAGE);
        if (markė == null) // Kai pasirinkta Cancel arba Esc
            return;

        // PIRMAS variantas: Atrinktieji įrašai išvedami į tą patį JTextArea elementą:
        taInformacija.append("\n  Atrinkti " + markė + " markės automobiliai :\n");
        apskaita.atrinktiPagalMarkę(markė, taInformacija);

        // ANTRAS variantas: Atrinktieji įrašai išvedami į atskitą JFrame objektą lentele JTable:
        JFrame fr = new JFrame();
        JPanel pa = new JPanel();
        pa.setLayout(new BorderLayout());
        JTable lentelė = new JTable();
        String stulpeliuVardai[] = {"Modelis", "Metai", "Rida", "Kaina"};
        DefaultTableModel lentelėsModelis = (DefaultTableModel) lentelė.getModel();
        lentelė.setModel(lentelėsModelis);
        JScrollPane juosta = new JScrollPane(lentelė);
        pa.add(new JLabel("Atrinkti " + markė + " markės automobiliai:", SwingConstants.CENTER), BorderLayout.NORTH);
        pa.add(juosta, BorderLayout.CENTER);
        lentelė.setRowSorter(new TableRowSorter(lentelėsModelis)); // Rikiavimui pagal stulpelius su pele

        // Stulpelių vardai ir informacija:
        lentelėsModelis.setColumnIdentifiers(stulpeliuVardai);
        boolean b = apskaita.atrinktiPagalMarkęLentele(lentelėsModelis, markė);
        if (b) {
            // Jei įrašų atrinkta
            fr.add(pa);
            fr.setSize(300, 350);
            // Dėsim JTable lentelę į sąsajos lango centrą (kitaip padės į ekrano centrą):
            Dimension dydis = Sasaja.this.getSize();    // sąsajos lango gydis
            Point vieta = Sasaja.this.getLocation();    // sąsajos lango vieta (kairys-viršutinis kampas)
            fr.setLocation((vieta.x + dydis.width / 2) - fr.getSize().width / 2,
                    (vieta.y + dydis.height / 2) - fr.getSize().height / 2);
            fr.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(Sasaja.this,
                    "<" + markė + "> markės automobilių nerasta.", "Atranka", JOptionPane.WARNING_MESSAGE);

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
