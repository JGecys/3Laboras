package Lab3Gečys;

import Lab2Gečys.Telefonas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NaujasTelefonasSasaja extends JFrame implements ActionListener {

    JLabel mGamintojas = new JLabel("Gamintojas");
    JTextField mGamintojasField = new JTextField();

    JLabel mModelis = new JLabel("Modelis");
    JTextField mModelisField = new JTextField();

    JLabel mKaina = new JLabel("Kaina");
    JTextField mKainaField = new JTextField();

    JLabel mNupirkimoMetai = new JLabel("Nupiirkimo metai");
    JTextField mNupirkimoField = new JTextField();

    JLabel mGarantija = new JLabel("Garantija");
    JTextField mGarantijaField = new JTextField();

    JViewport empty = new JViewport();
    JButton registruoti = new JButton("Registruoti");

    private OnRegisterListener listener;

    public void SetOnRegisterListener(OnRegisterListener list){
        listener = list;
    }

    public NaujasTelefonasSasaja(){
        super();
        JPanel panel = ((JPanel) getContentPane());
        panel.setLayout(new GridLayout(0, 2));
        panel.add(mGamintojas);
        panel.add(mGamintojasField);
        panel.add(mModelis);
        panel.add(mModelisField);
        panel.add(mKaina);
        panel.add(mKainaField);
        mKainaField.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(mNupirkimoMetai);
        panel.add(mNupirkimoField);
        mNupirkimoField.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(mGarantija);
        panel.add(mGarantijaField);
        mGarantijaField.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(empty);
        panel.add(registruoti);
        registruoti.addActionListener(this);
        ((GridLayout) panel.getLayout()).setVgap(2);
        ((GridLayout) panel.getLayout()).setVgap(16);
        pack();
        setResizable(false);

        revalidate();

        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(listener != null){
            try{
                Telefonas tel = Telefonas.FromString(mGamintojasField.getText() + " " +
                        mModelisField.getText() + " " +
                        mKainaField.getText() + " " +
                        mNupirkimoField.getText() + " " +
                        mGarantijaField.getText());

                listener.OnRegister(tel);
                clear();
            }catch (IllegalStateException ill) {
                JOptionPane.showMessageDialog(this,
                        "Netinkami parametrai", "Klaida", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void clear(){
        mGamintojasField.setText("");
        mModelisField.setText("");
        mKainaField.setText("");
        mNupirkimoField.setText("");
        mGarantijaField.setText("");
    }

    public interface OnRegisterListener{
        void OnRegister(Telefonas t);
    }

}
