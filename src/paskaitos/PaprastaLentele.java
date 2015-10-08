package paskaitos;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

/**
 * Tai nedidelis grafinės vartotojo sąsajos pavyzdys apie javos lentelių (JTable) panaudojimą.
 * Lentelės duomenys pateikiami dvimačiame masyve programos tekste.
 * <p>
 *  IŠSIAIŠKINKITE lentelės sudarymą ir jos rikiavimą pagal pasirinktą stulpelį.
 */
public class PaprastaLentele extends JFrame {
	private	final JPanel paLentele;
	private	final JTable lentele;
	private	final JScrollPane scrollPane;

	public PaprastaLentele() {
		paLentele = new JPanel();
		paLentele.setLayout( new BorderLayout() );
		getContentPane().add(paLentele);

		// Lentelės stulpelių vardai:
		String stulpeliuVardai[] = { "Vardas", "Grupė", "Įvertinimas" };

		// Lentelės duomenys:
		String duomenys[][] = {
			{ "Vaidas", "IF-1", "8.5" },
			{ "Jurgita", "IF-2", "8" },
			{ "Saulė", "IF-1", "9" },
			{ "Benas", "IF-2", "8.8" },
			{ "Saulė", "IF-3", "7" },
			{ "Benas", "IF-1", "10" }
		};

		lentele = new JTable( duomenys, stulpeliuVardai ); // sukuria lentelę
		TableModel modelis = lentele.getModel(); // paima modelį rikiavimui
		lentele.setRowSorter(new TableRowSorter(modelis));
		scrollPane = new JScrollPane( lentele );
		paLentele.add( scrollPane, BorderLayout.CENTER );

	} // Konstruktoriaus PaprastaLentele pabaiga

	/**
	 * Programos paleidimo taškas: sukuriamas JFrame klasės vaiko PaprastaLentele objektas,
	 * 	 nustatomas lango dydis ir vieta ir langas parodomas ekrane (metodas setVisible).
	 * @param args argumentų masyvas.
	 */
	public static void main( String args[] )
	{
		PaprastaLentele langas	= new PaprastaLentele();
		langas.setTitle( "Paprasta lentelė" );
		langas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		langas.setSize( 280, 140 );
		langas.setBackground( Color.gray );
		langas.setVisible( true );
	} // main metodo pabaiga
	
} // Klasės PaprastaLentele pabaiga