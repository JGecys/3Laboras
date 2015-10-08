package paskaitos;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

/**
 * Klasės JTextPane demonstracija, išvedant į jos objektą html failą
 *
 * @author Aleksis
 */
/**
 * Klasės JTextPane demonstracija, išvedant į jos objektą html failą.
 */
public class TextPane extends JFrame {

    JTextPane laukas = new JTextPane();
    JButton b = new JButton("  Rodyti html failą  ");
    JScrollPane juosta = new JScrollPane(laukas);

    TextPane() {
	Container k = getContentPane();
	k.setLayout(new BorderLayout());
	k.add(b, BorderLayout.NORTH);
	k.add(juosta, BorderLayout.CENTER);

	b.addActionListener((ActionEvent e) -> {
		String s = null;
		try {
			s = "file:" + "miegas.htm";
		    //s = "http://www.cs.wcupa.edu/rkline/";
			
			// Apie html failo vietą:
			//   NetBeans aplinka: failas "miegas.htm" randasi pagrindiniame projekto kataloge;
			//   Komandinė eilutė: failas "miegas.htm" randasi ten, kur ir paketas textpane
			//		( paleidimas: java.exe textpane.TextPane )
			URL url = new URL(s);
			laukas.setPage(url);
		} catch (Exception ex) {
			System.err.println("Klaida sukuriant URL is failo " + s);
		}
	});

	setTitle("Html failas");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setVisible(true);
	setSize(500, 500);
    }

	/**
	 * Programos paleidimo taškas.
	 * @param args argumentų masyvas.
	 */
	public static void main(String[] args) {
	new TextPane();
    }
}
