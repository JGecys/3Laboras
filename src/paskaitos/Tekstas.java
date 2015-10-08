package paskaitos;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * Tai nedidelis grafinės vartotojo sąsajos pavyzdys apie teksto išvedimą i JTextField elementą.
 * Aiškinamasisi tekstas (laukų pavadinimai) išvedami į JTextField elementus.
 * <p>
 *  IŠSIAIŠKINKITE informacijos įvedimą/išvedimą į JTextField elementus ir įvykių apdorojimą.
 */
public class Tekstas extends JFrame {

    private final JLabel z1 = new JLabel("Įvedimo laukas:");
    private final JLabel z2 = new JLabel("Skaičius kvadratu:");
    private final JTextField duom = new JTextField(15);
    private final JTextField rez =  new JTextField(15);

    public Tekstas() {
        Container turinys = getContentPane();
        turinys.setLayout(new GridLayout(2, 2));
        turinys.add(z1);    // lauko žymė	
        turinys.add(duom);	// įvedimo laukas
        turinys.add(z2);
        turinys.add(rez);	// rezultatų laukas

        duom.addActionListener(new EnterPaspaudimas());
    }

    /**
	 * Vidinė klasė Enter klavišo paspaudimo veiksmui
	 */
    class EnterPaspaudimas implements ActionListener {
		@Override
        public void actionPerformed(ActionEvent ae) {
            try {
                String s = duom.getText();
                double x = Double.parseDouble(s);
                rez.setText(Double.toString(x * x));
            } catch (NumberFormatException e) {
                rez.setText("Tai nebuvo skaičius");
            }
        }
    }

	/**
	 * Programos paleidimo taškas: sukuriamas JFrame klasės vaiko PaprastaLentele objektas,
	 * 	 nustatomas lango dydis (metodas pack) ir langas parodomas ekrane (metodas setVisible).
	 * @param args argumentų masyvas.
	 */
    public static void main(String[] args) {
        Tekstas frame = new Tekstas();
        frame.setTitle("Įvedimo laukai");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();  // arba setSize() metodas
    }
}
