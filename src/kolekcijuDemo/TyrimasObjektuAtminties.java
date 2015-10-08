/** @author Eimutis Karčiauskas, KTU IF Programų inžinerijos katedra, 2014 10 24
 *
 * Tai yra demonstracinė atminties tyrimo klasė, kurioje eksperimentiškai
 * bandome nustatyti sukuriamų objektų užimamos atminties kiekį.
 * Dėl automatiškai veikiančio šiukškių surinkimo ir kitų procesų
 * gauti rezultatai gali turėti nedideles paklaidas.
   *  IŠSIAIŠKINKITE metodo memDifference sudarymą, jo paskirtį;
   *  IŠBANDYKITE skirtingų objektų kūrimą ir jų atminties tyrimą
   *  PASIRINKITE kitų objektų klasę ir sudarykite analogiškus tyrimo metodus
   *  PALYGINKITE gautus rezultatus su teoriniais objektų užimamos atminties
   *                įvertinimais, raskite neatitikimus
   ****************************************************************************/

package kolekcijuDemo;

import java.util.ArrayList;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JTextArea;

/**
 * Klasė skirta atminties, skiriamos obkeltų saugojimui, tyrimui.
 * Tyrimo rezultatai yra kaupiami objekte StringBuilder.
 * <p>
 *  IŠSIAIŠKINKITE skirtingų objektų atminties poreikį.
 */
public class TyrimasObjektuAtminties {
    int memUsed;  // čia nurodomas prieš kreipinį buvęs atminties kiekis
    StringBuilder sb = new StringBuilder(1200);  // skirta rezultatams kaupti
    
//******************************************************************************    
// metodas paskaičiuoja užimtos atminties kiekio pasikeitimą, 
// įvykusį tarp tarp dviejų kreipinių į patį metodą memDifference
// Rezultatai yra kaupiami objekte StringBuilder sb
    void memDifference(String rem) {
        System.gc(); // 
        System.gc();
        System.gc();
        long memTotal = Runtime.getRuntime().totalMemory();
        System.gc();
        long memFree = Runtime.getRuntime().freeMemory();
        int memUsed1 = (int) (memTotal - memFree);
        sb.append(String.format(rem + " užima atminties = %,6d\t\n",
                (memUsed1 - memUsed)));
        memUsed = memUsed1;
    }
//******************************************************************************    
// metode nuosekliai sukuriami įvairūs duomenų struktūrų objektai
// ir panaudojant metodą memDifference fiksuojamas atmintiess skirtumas 
    void atmintiesSunaudojimoTyrimas() {
        memDifference("Pradinis kodas");
        // pradžioje užkraunamos tiriamos klasės
        byte[] b = new byte[2];
        int[] k = new int[2];
        Integer[] j = new Integer[2];
        String[] s = new String[2];
        LinkedList<Integer> x = new LinkedList<Integer>();
        LinkedList<String> y = new LinkedList<String>();
        y.add(new String("abc6789"));

        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(11);
        memDifference("Klasių užkrovimas");
        memDifference("Klasių užkrovimas");
        memDifference("Klasių užkrovimas");
        // tyrimas prasideda nuo čia
        byte[] b1 = new byte[1000];                     // byte
        memDifference("byte[] b1=new byte[1000];");
        
        int[] k1 = new int[1000];                       // int
        memDifference("int[] k1=new int[1000];");   
        
        Integer[] j1 = new Integer[1000];               // Integer
        memDifference("Integer[] j1= new Integer[1000];");
        
        for (int i = 0; i < 1000; i++) {
            j1[i] = new Integer(i);
        }
        memDifference("for (int i=0;i<1000;) j1[i]= new Integer(i);");
        

        String[] s1 = new String[1000];                // String
        memDifference("String[] j1= new String[1000];");
        String[] s2 = new String[1000];                // String
        memDifference("String[] j1= new String[1000];");
        String[] s3 = new String[1000];                // String
        memDifference("String[] j1= new String[1000];");

        for (int i = 0; i < 1000; i++) {
            s1[i] = new String("abc");
        }
        memDifference("for (int i=0;i<1000;) j1[i]= new String(abc);");
        for (int i = 0; i < 1000; i++) {
            s2[i] = new String("abc" + i);
        }
        memDifference("for (int i=0;i<1000;) j2[i]= new String(abc+i);");
        for (int i = 0; i < 1000; i++) {
            s3[i] = new String("abc" + i + 7000);
        }
        memDifference("for (int i=0;i<1000;) j3[i]= new String(i+7000);");

        LinkedList<Integer> r = new LinkedList<Integer>();
        for (int i = 0; i < 1000; i++) {
            r.add(null);
        }
        memDifference("new LinkedList<Integer>(1000*null);");
        for (int i = 0; i < 1000; i++) {
            r.set(i, new Integer(i));
        }
        memDifference("new LinkedList<Integer>(1000*Integer);");
        
        ArrayList<Integer> a1 = new ArrayList<Integer>(1000);
        memDifference("ArrayList<Integer> a1= new ArrayList<Integer>(1000);");
        ArrayList<String> a2 = new ArrayList<String>(1000);
        memDifference("ArrayList<String> a2= new ArrayList<String>(1000);");
    }
//******************************************************************************    
// metode main sukuriamas JFrame langas, kuriame pateikiama tyrimo informacija
// padarius tiriamų objektų pakeitimus, skirtinguose languose galime stebėti
// kaip keičiasi užimamos atminties kiekis
    public static void main(String[] args) {
        TyrimasObjektuAtminties at = new TyrimasObjektuAtminties();
        at.atmintiesSunaudojimoTyrimas();
        
        JFrame fr = new JFrame();
        fr.add(new JTextArea(at.sb.toString()));
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.pack();
    }       
}
