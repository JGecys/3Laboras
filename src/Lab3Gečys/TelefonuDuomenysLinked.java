package Lab3Gečys;

import Lab2Gečys.Telefonas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by Jurgis on 2015-10-15.
 */
public class TelefonuDuomenysLinked extends LinkedList<Telefonas> {

    private OnListUpdate updateListener = null;

    public TelefonuDuomenysLinked() {
        super();
    }

    public TelefonuDuomenysLinked(List<Telefonas> list){
        super(list);
    }

    @Override
    public boolean add(Telefonas telefonas) {
        if (updateListener != null)
            updateListener.OnElementAdded(telefonas);
        return super.add(telefonas);
    }

    public void setUpdateListener(OnListUpdate updateListener) {
        this.updateListener = updateListener;
    }



    public void fromFile(File file) {
        try {
            Scanner scanner = new Scanner(file);
            if (size() > 0) {
                clear();
            }
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                try {
                    Telefonas tel = Telefonas.FromString(data);
                    add(tel);
                } catch (IllegalStateException e) {
                    System.out.println("Invalid data: " + data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean atrinktiPagalGamintoja(String gamintojas, JTextArea area) {
        int i = 0;
        for (Telefonas a : this) {
            if (a.getGamintojas().startsWith(gamintojas)) {
                area.append("  - " + a.toString() + "\n");
                i++;
            }
        }
        return i != 0;
    }

    public boolean atrinktiPagalGamintojaLentele(DefaultTableModel lentelėsModelis, String gamintojas) {
        for (Telefonas tel : this) {
            if (tel.getGamintojas().startsWith(gamintojas)) {
                lentelėsModelis.addRow(tel.toObjectArray());
            }
        }
        return lentelėsModelis.getRowCount() > 0;
    }

    public void rikiuotiPagalKaina() {
        sort(new Telefonas.ComparatorPagalKaina());
    }

    public interface OnListUpdate {
        void OnElementAdded(Telefonas tel);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Telefonų duomenys:\n");
        for (Telefonas tel : this) {
            builder.append("  - ").append(tel.toString()).append("\n");
        }
        return builder.toString();
    }
}
