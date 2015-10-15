package Lab3Gečys;

import Lab2Gečys.Telefonas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Jurgis on 2015-10-08.
 */
public class SugrupuotiTelefonai extends HashMap<String, List<Telefonas>> {

    public enum Tipas {
        GAMINTOJAS,
        MODELIS,
        NUPIRKIMO_METAI
    }

    private Tipas mTipas;

    public SugrupuotiTelefonai() {
        super();
    }

    public void fromList(List<Telefonas> list, Tipas kaipSugrupuoti) {
        mTipas = kaipSugrupuoti;
        if (size() > 0) {
            clear();
        }
        for (Telefonas tel : list) {
            add(tel);
        }
    }

    public void add(Telefonas tel) {
        switch (mTipas) {
            case GAMINTOJAS:
                if (!containsKey(tel.getGamintojas())) {
                    put(tel.getGamintojas(), new ArrayList<>());
                }
                get(tel.getGamintojas()).add(tel);
                break;
            case MODELIS:
                if (!containsKey(tel.getModelis())) {
                    put(tel.getModelis(), new ArrayList<>());
                }
                get(tel.getModelis()).add(tel);
                break;
            case NUPIRKIMO_METAI:
                if (!containsKey(String.valueOf(tel.getNupirkimoMetai()))) {
                    put(String.valueOf(tel.getNupirkimoMetai()), new ArrayList<>());
                }
                get(String.valueOf(tel.getNupirkimoMetai())).add(tel);
                break;
        }
    }

    public void readFrom(File file, Tipas kaipSugrupuoti) {
        mTipas = kaipSugrupuoti;
        try {
            Scanner scanner = new Scanner(file);
            if (size() > 0) {
                clear();
            }
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                Telefonas tel = Telefonas.FromString(data);
                add(tel);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Telefonas> getGroup(String group) {
        return get(group);
    }

    public Iterator<Map.Entry<String, List<Telefonas>>> getIterator() {
        return entrySet().iterator();
    }

    public void forEach(final Code code) {
        forEach((s, tel) -> {
            for (Telefonas t : tel) {
                code.run(t);
            }
        });
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String, java.util.List<Telefonas>>> ite = getIterator();
        while (ite.hasNext()) {
            int i = 1;
            Map.Entry<String, java.util.List<Telefonas>> obj = ite.next();
            builder.append("----------- " + obj.getKey() + " -----------------\n");
            for (Telefonas tel : obj.getValue()) {
                builder.append(" " + i + ". " + tel.toString() + "\n");
                i++;
            }
        }
        return builder.toString();
    }

    public interface Code {
        void run(Telefonas tel);
    }

}
