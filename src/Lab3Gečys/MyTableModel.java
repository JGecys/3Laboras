package Lab3Gečys;

import Lab2Gečys.Telefonas;
import com.sun.deploy.ui.DialogTemplate;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jurgis on 2015-10-29.
 */
public class MyTableModel extends AbstractTableModel {

    private String[] columnNames = {"Gamintojas", "Modelis", "Kaina", "Nupirkimo metai", "Garantija"};

    private TelefonuDuomenys mDuomenys;
    private List<Integer> mAtrinkti;
    private boolean Sugrupuota;

    private JTable table;

    public MyTableModel(JTable table, TelefonuDuomenys duom) {
        super();
        this.mDuomenys = duom;
        this.table = table;
    }

    @Override
    public int getRowCount() {
        if (Sugrupuota) {
            return mAtrinkti.size();
        }
        return mDuomenys.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Telefonas tel;
        if (Sugrupuota) {
            tel = mDuomenys.get(mAtrinkti.get(rowIndex));
        } else {
            tel = mDuomenys.get(rowIndex);
        }
        switch (columnIndex) {
            case 0:
                return tel.getGamintojas();
            case 1:
                return tel.getModelis();
            case 2:
                return tel.getKaina();
            case 3:
                return tel.getNupirkimoMetai();
            case 4:
                return tel.getGarantija();
        }
        return tel;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Telefonas tel;
        if (Sugrupuota) {
            tel = mDuomenys.get(mAtrinkti.get(rowIndex));
        } else {
            tel = mDuomenys.get(rowIndex);
        }
        try {
            switch (columnIndex) {
                case 0:
                    tel.setGamintojas(((String) aValue));
                    break;
                case 1:
                    tel.setModelis(((String) aValue));
                    break;
                case 2:
                    tel.setKaina(Double.parseDouble(((String) aValue)));
                    break;
                case 3:
                    tel.setNupirkimoMetai(Integer.parseInt(((String) aValue)));
                    break;
                case 4:
                    tel.setGarantija(Integer.parseInt((String) aValue));
                    break;
            }

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Ivestas neteisingas formatas");
        }
    }

    public void PridetiTelefona(Telefonas tel){
        mDuomenys.add(tel);
        table.revalidate();
    }

    public boolean AtrinktiPagalGamintoja(String gam) {
        mAtrinkti = new ArrayList<>();
        int i = 0;
        for (Telefonas t : mDuomenys) {
            if (t.getGamintojas().startsWith(gam)) {
                mAtrinkti.add(i);
            }
            i++;
        }
        if (mAtrinkti.size() > 0) {
            Sugrupuota = true;
            table.revalidate();
            return true;
        }
        return false;
    }

    public void showAll(){
        Sugrupuota = false;
        table.revalidate();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public int findColumn(String columnName) {
        return Arrays.asList(columnNames).indexOf(columnName);
    }

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);
    }
}
