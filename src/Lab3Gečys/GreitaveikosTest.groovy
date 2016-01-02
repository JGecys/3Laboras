package Lab3Gečys

import Lab2Gečys.Telefonas
import Lab2Gečys.TelefonuGeneratorius
import studijosKTU.Timekeeper

/**
 * Created by Jurgis on 2015-11-05.
 */
class GreitaveikosTest extends GroovyTestCase {

    public static final int[] tiriamiKiekiai = [1000, 2000, 4000, 8000, 2_000_000];

    public void testGreitaveikaFormavimas(){
        println("Generavimo greitaveika:");
        System.gc(); System.gc(); System.gc();
        TelefonuGeneratorius gen = new TelefonuGeneratorius();
        Timekeeper tk = new Timekeeper(tiriamiKiekiai);
        for (int kiekis : tiriamiKiekiai) {
            //  Greitaveikos bandymai ir laiko matavimai
            tk.start();
            TelefonuDuomenys duomenys = gen.generuotiSarasa(kiekis);
            tk.finish("TelDuom");
            TelefonuDuomenysLinked duomenysLinked = gen.generuotiSarasa(kiekis);
            tk.finish("TelDuomLinked");
            SugrupuotiTelefonai sugrupuotiTelefonai = new SugrupuotiTelefonai(HashMap.class);
            sugrupuotiTelefonai.fromList(duomenys, SugrupuotiTelefonai.Tipas.GAMINTOJAS);
            tk.finish("SugrupHash");
            SugrupuotiTelefonai sugrupuotiTelefonai1 = new SugrupuotiTelefonai(TreeMap.class);
            sugrupuotiTelefonai1.fromList(duomenys, SugrupuotiTelefonai.Tipas.GAMINTOJAS);
            tk.finish("SugrupTree");
            tk.seriesFinish();
        }
    }

    public void testGreitaveikaPerziura(){
        println("Perziuros greitaveika:");
        System.gc(); System.gc(); System.gc();
        TelefonuGeneratorius gen = new TelefonuGeneratorius();
        Timekeeper tk = new Timekeeper(tiriamiKiekiai);
        for (int kiekis : tiriamiKiekiai) {
            //  Greitaveikos bandymai ir laiko matavimai
            TelefonuDuomenys duomenys = gen.generuotiSarasa(kiekis);
            TelefonuDuomenysLinked duomenysLinked = gen.generuotiSarasa(kiekis);
            SugrupuotiTelefonai sugrupuotiTelefonai = new SugrupuotiTelefonai(HashMap.class);
            sugrupuotiTelefonai.fromList(duomenys, SugrupuotiTelefonai.Tipas.GAMINTOJAS);
            SugrupuotiTelefonai sugrupuotiTelefonai1 = new SugrupuotiTelefonai(TreeMap.class);
            sugrupuotiTelefonai1.fromList(duomenys, SugrupuotiTelefonai.Tipas.GAMINTOJAS);
            tk.start();
            for (int i = 0; i < duomenys.size(); i++) {
            }
            tk.finish("TelDuom");
            for(Iterator<Telefonas> i = duomenysLinked.iterator(); i.hasNext(); i.next()){

            }
            tk.finish("TelDuomLinked");
            for(def i = sugrupuotiTelefonai.getIterator(); i.hasNext(); i.next()){

            }
            tk.finish("SugrupHash");
            for(def i = sugrupuotiTelefonai1.getIterator(); i.hasNext(); i.next()){

            }
            tk.finish("SugrupTree");
            tk.seriesFinish();
        }
    }



}
