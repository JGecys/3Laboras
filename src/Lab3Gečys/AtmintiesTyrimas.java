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

package Lab3Gečys;

import Lab2Gečys.TelefonuGeneratorius;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import javax.swing.*;

/**
 * Klasė skirta atminties, skiriamos obkeltų saugojimui, tyrimui.
 * Tyrimo rezultatai yra kaupiami objekte StringBuilder.
 * <p>
 *  IŠSIAIŠKINKITE skirtingų objektų atminties poreikį.
 */
public class AtmintiesTyrimas {
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
        SugrupuotiTelefonai uzkrovimas = new SugrupuotiTelefonai(HashMap.class);
        TelefonuGeneratorius gen = new TelefonuGeneratorius();

        memDifference("Klasių užkrovimas");
        memDifference("Klasių užkrovimas");
        memDifference("Klasių užkrovimas");
        // tyrimas prasideda nuo čia

        TelefonuDuomenys duom = new TelefonuDuomenys(gen.generuotiSarasa(2000));
        memDifference("TelefonuDuomenys duom = new TelefonuDuomenys(gen.generuotiSarasa(2000));");
        TelefonuDuomenysLinked duom2 = new TelefonuDuomenysLinked(gen.generuotiSarasa(2000));
        memDifference("TelefonuDuomenysLinked duom2 = new TelefonuDuomenysLinked(gen.generuotiSarasa(2000));");

        TelefonuDuomenys duom3 = new TelefonuDuomenys(gen.generuotiSarasa(16000));
        memDifference("TelefonuDuomenys duom3 = new TelefonuDuomenys(gen.generuotiSarasa(16000));");
        TelefonuDuomenysLinked duom4 = new TelefonuDuomenysLinked(gen.generuotiSarasa(16000));
        memDifference("TelefonuDuomenysLinked duom4 = new TelefonuDuomenysLinked(gen.generuotiSarasa(16000));");

        SugrupuotiTelefonai hashmap = new SugrupuotiTelefonai(HashMap.class);
        hashmap.fromList(gen.generuotiSarasa(2000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("hashmap.fromList(gen.generuotiSarasa(2000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");


        SugrupuotiTelefonai treemap = new SugrupuotiTelefonai(TreeMap.class);
        treemap.fromList(gen.generuotiSarasa(2000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("treemap.fromList(gen.generuotiSarasa(2000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");


        SugrupuotiTelefonai hashmap2 = new SugrupuotiTelefonai(HashMap.class);
        hashmap2.fromList(gen.generuotiSarasa(4000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("hashmap2.fromList(gen.generuotiSarasa(4000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");

        SugrupuotiTelefonai treemap2 = new SugrupuotiTelefonai(TreeMap.class);
        treemap2.fromList(gen.generuotiSarasa(4000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("treemap2.fromList(gen.generuotiSarasa(4000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");


        SugrupuotiTelefonai hashmap3 = new SugrupuotiTelefonai(HashMap.class);
        hashmap3.fromList(gen.generuotiSarasa(8000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("hashmap3.fromList(gen.generuotiSarasa(8000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");

        SugrupuotiTelefonai treemap3 = new SugrupuotiTelefonai(TreeMap.class);
        treemap3.fromList(gen.generuotiSarasa(8000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);
        memDifference("treemap3.fromList(gen.generuotiSarasa(8000), SugrupuotiTelefonai.Tipas.GAMINTOJAS);");

    }
//******************************************************************************    
// metode main sukuriamas JFrame langas, kuriame pateikiama tyrimo informacija
// padarius tiriamų objektų pakeitimus, skirtinguose languose galime stebėti
// kaip keičiasi užimamos atminties kiekis
    public static void main(String[] args) {
        AtmintiesTyrimas at = new AtmintiesTyrimas();
        at.atmintiesSunaudojimoTyrimas();
        
        JFrame fr = new JFrame();
        fr.add(new JTextArea(at.sb.toString()));
        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.setVisible(true);
        fr.pack();
    }       
}
