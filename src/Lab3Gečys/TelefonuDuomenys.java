package Lab3Gečys;

import Lab2Gečys.Telefonas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jurgis on 2015-10-08.
 */
public class TelefonuDuomenys {

    public enum Tipas {
        GAMINTOJAS,
        MODELIS,
        NUPIRKIMO_METAI
    }

    private Tipas mTipas;

    private HashMap<String, List<Telefonas>> mMap;

    public TelefonuDuomenys() {

    }

    public void readFrom(File file, Tipas kaipSugrupuoti) {
        mTipas = kaipSugrupuoti;
        try {
            Scanner scanner = new Scanner(file);
            if(mMap == null){
                mMap = new HashMap<>();
            }
            if (mMap.size() > 0) {
                mMap.clear();
            }
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                Telefonas tel = Telefonas.FromString(data);
                switch (kaipSugrupuoti) {
                    case GAMINTOJAS:
                        if (!mMap.containsKey(tel.getGamintojas())) {
                            mMap.put(tel.getGamintojas(), new ArrayList<>());
                        }
                        mMap.get(tel.getGamintojas()).add(tel);
                        break;
                    case MODELIS:
                        if (!mMap.containsKey(tel.getModelis())) {
                            mMap.put(tel.getModelis(), new ArrayList<>());
                        }
                        mMap.get(tel.getModelis()).add(tel);
                        break;
                    case NUPIRKIMO_METAI:
                        if (!mMap.containsKey(String.valueOf(tel.getNupirkimoMetai()))) {
                            mMap.put(String.valueOf(tel.getNupirkimoMetai()), new ArrayList<>());
                        }
                        mMap.get(String.valueOf(tel.getNupirkimoMetai())).add(tel);
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Telefonas> getGroup(String group){
        return mMap.get(group);
    }

    public void forEach(final Code code){
        mMap.forEach((s, tel) -> {
            for(Telefonas t : tel){
                code.run(t);
            }
        });
    }

    public interface Code{
        void run(Telefonas tel);
    }

}
