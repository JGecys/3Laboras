package paskaitos;

import javax.swing.*;		// swing komponentai
import java.awt.*;			// AWT komponentai ir artimos klasės
import java.awt.event.*;	// įvykių ir adapterių klasės  
import javax.swing.border.*;// rėmeliai, grupės

/**
 * Tai nedidelis grafinės vartotojo sąsajos pavyzdys su pasirinkimo mygtukais (JRadioButton objektai).
 * Pranešimų apie pasirinktojo mygtuko paspaudimą išvedimui panaudotas elementas-žymė JLabel.
 * <p>
 *  IŠSIAIŠKINKITE įvykių apdorojimą (kurį mygtuką paspaudė vartotojas).
 */
public class Mygtukai extends JFrame {

    private final JRadioButton rb1 = new JRadioButton("pirmas", true);
    private final JRadioButton rb2 = new JRadioButton("antras", false);
    private final JRadioButton rb3 = new JRadioButton("trečias", false);
    private final JLabel veiksmas  = new JLabel("Pirmas variantas");

    Mygtukai() {
        Container konteineris = getContentPane();
        konteineris.setLayout(new FlowLayout());
        ButtonGroup grupe = new ButtonGroup(); // grupė

        // Mygtukai dedami į JPanel langą
        JPanel langelis = new JPanel();
        langelis.setBorder(new TitledBorder("Variantai")); // rėmelis 
        // grupuojame (pabandykite tolimesnius veiksmus užkomentuoti)
        grupe.add(rb1);
        grupe.add(rb2);
        grupe.add(rb3);

        // dedame į JPanel langą
        langelis.add(rb1);
        langelis.add(rb2);
        langelis.add(rb3);
        konteineris.add(langelis);

        // registruojame įvykius
        rb1.addActionListener(new PirmoMygtukoKlausytojas());
        ActionListener bendrasMyg23 = new AntrasTrecias();
        rb2.addActionListener(bendrasMyg23);
        rb3.addActionListener(new AntrasTrecias());

        konteineris.add(veiksmas);
        
        setTitle("Trys pasirinkimai");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setVisible(true);      
    }

    /**
	 * Vidinė klasė - pirmojo mygtuko paspaudimo veiksmai
	 */
	class PirmoMygtukoKlausytojas implements ActionListener {
        // vidinė klasė vienam komponentui (pirmam mygtukui)
		@Override
        public void actionPerformed(ActionEvent e) {
            veiksmas.setText("Jus pasirinkote pirmąjį variantą");
        }
    }

    /**
	 * Vidinė klasė - antro ir trečio mygtukų paspaudimo veiksmai
	 */
	class AntrasTrecias implements ActionListener {
        // vidinė klasė bendra 2-am ir 3-am mygtukui
		@Override
        public void actionPerformed(ActionEvent e) {
            // 1-as varijantas: nustatysime mygtukus pagal jų objektų adresus
            Object myg = e.getSource();
            if (myg == rb2) {	       //  lyginame objektų adresus su ==   
                veiksmas.setText("Jus pasirinkote antrąjį variantą");
            } else if (myg == rb3)  {  // lyginame objektų adresus su ==   
                veiksmas.setText("Jus pasirinkote trečiąjį variantą");
            }
            // 2-as varijantas: nustatysime mygtukus pagal jų komandas
//            switch(e.getActionCommand()){
//                case "antras":  
//                    veiksmas.setText("Jus pasirinkote antrąjį variantą");
//                    break;
//                case "trečias":  
//                    veiksmas.setText("Jus pasirinkote trečiąjį variantą");
//                    break;
//            }
        }
    }
    
	/**
	 * Programos paleidimo taškas: sukuriamas JFrame klasės vaiko Mygtukai objektas ir valdymas perduodamas konstruktoriui.
	 * @param args argumentų masyvas.
	 */
	public static void main(String[] args) {
        new Mygtukai();
    }       
}
