package com.company;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class main extends JFrame{
    private JPanel panelMain;
    private JList jListPasien;
    private JTextField textFieldNama;
    private JTextField textFieldPenyakit;
    private JTextField textFieldUmur;
    private JButton ButtonClear;
    private JButton ButtonSakit;
    private JButton ButtonSembuh;
    private DefaultListModel defaultListModel = new DefaultListModel();
    private List<String> arrayListPasien = new ArrayList<>();
    private LinkedList<Pasien> listPasien = new LinkedList<>();

    class Pasien{
        private String nama;
        private String penyakit;
        private int umur;

        public String getNama() {
            return nama;
        }                               //getter
                                                                                        //oopII
        public void setNama(String nama) {      //setter
            this.nama = nama;
        }                 //setter

        public String getPenyakit() {              //getter
            return penyakit;
        }

        public void setPenyakit(String penyakit) { this.penyakit = penyakit; }

        public int getUmur() {
            return umur;
        }

        public void setUmur(int umur) {
            this.umur = umur;
        }
    }

    public main(){
        this.setContentPane(panelMain);                                         // function
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        ButtonClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        ButtonSakit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nama = textFieldNama.getText();
                String penyakit = textFieldPenyakit.getText();
                int umur = (int) Double.parseDouble(textFieldUmur.getText());

                Pasien pasien = new Pasien();
                pasien.setNama(nama);
                pasien.setPenyakit(penyakit);
                pasien.setUmur(umur); //????

                insertFirst(pasien);
                clearForm();
                refreshDataModel();
            }
        });
        jListPasien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = jListPasien.getSelectedIndex();

                Pasien hasilSearch = search(arrayListPasien.get(index));

                if (hasilSearch !=null){
                    textFieldNama.setText(hasilSearch.getNama());
                    textFieldPenyakit.setText(hasilSearch.getPenyakit());
                    textFieldUmur.setText(String.valueOf(hasilSearch.getUmur()));
                }
            }
        });
        ButtonSembuh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = jListPasien.getSelectedIndex();

                if (index < 0)                                                     //pengkondisian
                    return;
                String nama = jListPasien.getSelectedValue().toString();

                for (int i = 0; i < arrayListPasien.size(); i++){
                    if (arrayListPasien.get(i).equals(nama)){
                        arrayListPasien.remove(i);
                        break;
                    }
                }
                clearForm();
                fromListPasienToDataModel();
            }

            private void fromListPasienToDataModel() {
                List<String> listPasien = new ArrayList<>();

                for (int i = 0; i <arrayListPasien.size(); i++){                //perulangan
                    listPasien.add(
                            arrayListPasien.get(i)
                    );
                }
                refhresDataModel(listPasien);
            }
            private void refhresDataModel(List<String> list){
                defaultListModel.clear();
                defaultListModel.addAll(list);
                jListPasien.setModel(defaultListModel);
            }


        });
    }

    private Pasien search(String nama){
        for (Pasien pasien : listPasien){
            if (pasien == null)
                break;

            if (pasien.getNama().equals(nama))
                return pasien;
        }
        return null;
    }

    private void insertFirst(Pasien pasien){
        listPasien.sakit(pasien);                                
    }

    private void refreshDataModel(){
        arrayListPasien.clear();

        for (Pasien pasien : listPasien){
            if (pasien == null)
                break;

            arrayListPasien.add(pasien.getNama());
        }
        defaultListModel.clear();
        defaultListModel.addAll(arrayListPasien);
        jListPasien.setModel(defaultListModel);
    }

    private void clearForm(){                                    
        textFieldNama.setText("");
        textFieldPenyakit.setText("");
        textFieldUmur.setText("");
    }
    public static void main(String[] args) {
        main main = new main();
        main.setVisible(true);
    }
}
