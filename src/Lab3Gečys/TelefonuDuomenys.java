package Lab3Gečys;

import Lab2Gečys.Telefonas;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jurgis on 2015-10-15.
 */
public class TelefonuDuomenys extends ArrayList<Telefonas> {

    private OnListUpdate updateListener = null;

    public TelefonuDuomenys(){
        super();
    }

    @Override
    public boolean add(Telefonas telefonas) {
        if(updateListener != null)
            updateListener.OnElementAdded(telefonas);
        return super.add(telefonas);
    }

    public void setUpdateListener(OnListUpdate updateListener){
        this.updateListener = updateListener;
    }



    public void fromFile(File file){
        try {
            Scanner scanner = new Scanner(file);
            if (size() > 0) {
                clear();
            }
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                try{
                    Telefonas tel = Telefonas.FromString(data);
                    add(tel);
                }catch (IllegalStateException e){
                    System.out.println("Invalid data: " + data);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public interface OnListUpdate{
        void OnElementAdded(Telefonas tel);
    }

}
