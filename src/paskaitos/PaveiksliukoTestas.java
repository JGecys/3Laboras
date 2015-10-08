package paskaitos;

import javax.swing.*;
import java.awt.*;
import java.net.*;

/**
 * Piešinuko (gif tipo failo) išvedimo į ekraną pavyzdys.
 */
class PiestiPaveiksla extends JComponent { // tinka paveldėti ir JPanel
	Image piesinys = null;
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (piesinys != null) {
			g.drawImage(piesinys, 30, 10, this);
		}
	} // Metodo paintComponent pabaiga
	
	/**
	 * Suformuoja piešinuką (Igame klasės objektą) iš failo.
	 * @return true, jei sėkingai.
	 */
	public boolean skaitytiPiesini() {
		try {
			String s = "file:" + "dukesign.gif"; 
							// piešinuko failas projekto kataloge
			URL url = new URL(s);
			Toolkit t = getToolkit();
			piesinys = t.getImage(url);
		} catch(MalformedURLException e) {
			return false; // URL klaida - nežinomas protokolas
		} 
		if (piesinys.getWidth(null) == -1)
			return false; // nenuskaitė piešinuko
		
		//piesinys = getToolkit().getImage(url); // galima ir kartu
		repaint(); // kviečiame paintComponent() metodą
		return true;
	} // Metodo skaitytiPiesini pabaiga
	
} // Klasės PiestiPaveiksla pabaiga

/**
 * Programos paleidimo klasė.
 */
public class PaveiksliukoTestas{
	public static void main(String args[ ]) throws InterruptedException {
		JFrame langas = new JFrame("Piešimas 2 - paveiksliukas");
		PiestiPaveiksla drobe = new PiestiPaveiksla();
		JLabel zyme = new JLabel("     Žemiau padetas piešinukas  ");
		JLabel zyme2 = new JLabel("  Komentarams  ");
		langas.getContentPane().add(zyme, BorderLayout.NORTH);
		langas.getContentPane().add(zyme2, BorderLayout.SOUTH);
		langas.getContentPane().add(drobe, BorderLayout.CENTER);
		langas.setSize(270, 180);
		langas.setVisible(true);
		langas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drobe.skaitytiPiesini();
		Thread.sleep(1000);  // pauzelė piešinuko skaitymui
		if (drobe.skaitytiPiesini() == false)
			zyme2.setText("Piešinukas nenuskaitytas");
	} // Metodo main pabaiga
} // Klasės PaveiksliukoTestas pabaiga
