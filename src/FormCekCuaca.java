
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author LENOVO
 */
public class FormCekCuaca extends javax.swing.JFrame {

    /**
     * Creates new form FormCekCuaca
     */
    public FormCekCuaca() {
        initComponents();
        loadFavoriteCities();
     
    }
private void loadFavoriteCities() {
    File file = new File("favorit.txt");
    if (!file.exists()) return; // kalau belum ada, langsung keluar

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                cmbKotaFavorite.addItem(line.trim());
            }
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "Gagal memuat kota favorit: " + ex.getMessage());
    }
}    
private void exportToCSV() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan Data Cuaca ke File CSV");
    int userSelection = fileChooser.showSaveDialog(this);

    if (userSelection == JFileChooser.APPROVE_OPTION) {
        File fileToSave = fileChooser.getSelectedFile();

        // Tambahkan ekstensi .csv jika belum ada
        if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
            fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave))) {
            javax.swing.table.TableModel model = tblDataCuaca.getModel();

            // Header CSV
            writer.write("Kota,Suhu (°C),Kelembapan (%),Kondisi\n");

            // Tulis isi tabel
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.write(
                        model.getValueAt(i, 0) + "," +
                        model.getValueAt(i, 1) + "," +
                        model.getValueAt(i, 2) + "," +
                        model.getValueAt(i, 3) + "\n"
                );
            }

            JOptionPane.showMessageDialog(this, "Data berhasil diekspor ke " + fileToSave.getAbsolutePath());

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Gagal menulis file: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
        }
    }
}
private void importFromCSV() {
    showCSVGuide();
    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Apakah Anda yakin file CSV sudah sesuai format?",
            "Konfirmasi Impor CSV",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm == JOptionPane.YES_OPTION) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Pilih File CSV Cuaca");
        int userSelection = fileChooser.showOpenDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToOpen = fileChooser.getSelectedFile();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileToOpen))) {
                String header = reader.readLine(); // baca header CSV

                if (!validateCSVHeader(header)) {
                    JOptionPane.showMessageDialog(this,
                            "Header CSV tidak valid.\nPastikan format header adalah:\nKota,Suhu (°C),Kelembapan (%),Kondisi",
                            "Kesalahan CSV", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDataCuaca.getModel();
                model.setRowCount(0); // hapus data lama

                String line;
                int rowCount = 0;
                int errorCount = 0;
                StringBuilder errorLog = new StringBuilder("Baris bermasalah:\n");

                while ((line = reader.readLine()) != null) {
                    rowCount++;
                    String[] data = line.split(",");

                    if (data.length != 4) {
                        errorCount++;
                        errorLog.append("Baris ").append(rowCount + 1).append(": jumlah kolom tidak sesuai.\n");
                        continue;
                    }

                    String kota = data[0].trim();
                    String suhu = data[1].trim();
                    String kelembapan = data[2].trim();
                    String kondisi = data[3].trim();

                    if (kota.isEmpty() || suhu.isEmpty() || kondisi.isEmpty()) {
                        errorCount++;
                        errorLog.append("Baris ").append(rowCount + 1).append(": kolom kosong.\n");
                        continue;
                    }

                    model.addRow(new Object[]{kota, suhu, kelembapan, kondisi});
                }

                if (errorCount > 0) {
                    JOptionPane.showMessageDialog(this,
                            errorLog.toString() + "\nTotal baris error: " + errorCount,
                            "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Data cuaca berhasil diimpor dari CSV!");
                }

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal membaca file: " + ex.getMessage(), "Kesalahan", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
private void showCSVGuide() {
    String guideMessage = "Format CSV untuk data cuaca:\n" +
            "- Header wajib: Kota,Suhu (°C),Kelembapan (%),Kondisi\n" +
            "- Contoh isi file CSV:\n" +
            "Jakarta,30,75,Cerah\n" +
            "Surabaya,32,80,Hujan\n\n" +
            "Pastikan file sesuai format sebelum diimpor.";
    JOptionPane.showMessageDialog(this, guideMessage, "Panduan CSV", JOptionPane.INFORMATION_MESSAGE);
}

private boolean validateCSVHeader(String header) {
    return header != null &&
            header.trim().equalsIgnoreCase("Kota,Suhu (°C),Kelembapan (%),Kondisi");
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSuhu = new javax.swing.JLabel();
        lblKelembapan = new javax.swing.JLabel();
        lblKondisi = new javax.swing.JLabel();
        txtKota = new javax.swing.JTextField();
        btnCekCuaca = new javax.swing.JButton();
        cmbKotaFavorite = new javax.swing.JComboBox<>();
        btnSimpanKota = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDataCuaca = new javax.swing.JTable();
        lblGambar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("APLIKASI CEK CUACA");

        jLabel2.setText("Kota");

        jLabel3.setText("Kota Favorite");

        lblSuhu.setText("Suhu :");

        lblKelembapan.setText("Kelembapan :");

        lblKondisi.setText("Kondisi :");

        btnCekCuaca.setText("CEK CUACA");
        btnCekCuaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCekCuacaActionPerformed(evt);
            }
        });

        cmbKotaFavorite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbKotaFavoriteActionPerformed(evt);
            }
        });
        cmbKotaFavorite.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbKotaFavoriteKeyPressed(evt);
            }
        });

        btnSimpanKota.setText("SIMPAN");
        btnSimpanKota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanKotaActionPerformed(evt);
            }
        });

        btnImport.setText("IMPORT");
        btnImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportActionPerformed(evt);
            }
        });

        btnExport.setText("EXPORT");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        tblDataCuaca.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kota", "Suhu (°C)", "Kelembapan (%)", "Kondisi"
            }
        ));
        jScrollPane1.setViewportView(tblDataCuaca);

        lblGambar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGambar.setText("Gambar");
        lblGambar.setPreferredSize(new java.awt.Dimension(128, 128));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(86, 86, 86))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(btnImport)
                .addGap(26, 26, 26)
                .addComponent(btnExport)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblKondisi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblKelembapan, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(lblSuhu, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cmbKotaFavorite, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCekCuaca, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(btnSimpanKota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(17, 17, 17))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtKota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCekCuaca))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(25, 25, 25)
                        .addComponent(lblSuhu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKelembapan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblKondisi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblGambar, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnImport, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnExport, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbKotaFavorite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSimpanKota)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbKotaFavoriteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbKotaFavoriteActionPerformed
    String kotaDipilih = (String) cmbKotaFavorite.getSelectedItem();
    txtKota.setText(kotaDipilih);        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKotaFavoriteActionPerformed

    private void btnCekCuacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCekCuacaActionPerformed
    try {
            String kota = txtKota.getText().trim();
            String apiKey = "7d08727fdfdeadc02bc53d8f9c607437"; // daftarkan di openweathermap.org
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + kota + "&units=metric&appid=" + apiKey;

            java.net.URL url = new java.net.URL(urlString);
            java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            org.json.JSONObject json = new org.json.JSONObject(response.toString());
            double suhu = json.getJSONObject("main").getDouble("temp");
            int kelembapan = json.getJSONObject("main").getInt("humidity");
            String kondisi = json.getJSONArray("weather").getJSONObject(0).getString("main");

            lblSuhu.setText("Suhu: " + suhu + " °C");
            lblKelembapan.setText("Kelembapan: " + kelembapan + "%");
            lblKondisi.setText("Kondisi: " + kondisi);

            java.net.URL imgURL;
            javax.swing.ImageIcon icon;

            if (kondisi.toLowerCase().contains("rain")) {
                imgURL = getClass().getResource("/img/hujan.png");
            } else if (kondisi.toLowerCase().contains("cloud")) {
                imgURL = getClass().getResource("/img/mendung.png");
            } else {
                imgURL = getClass().getResource("/img/cerah.png");
            }

            if (imgURL != null) {
                java.awt.Image img = new javax.swing.ImageIcon(imgURL).getImage();
                java.awt.Image scaledImg = img.getScaledInstance(lblGambar.getWidth(), lblGambar.getHeight(), java.awt.Image.SCALE_SMOOTH);
                lblGambar.setIcon(new javax.swing.ImageIcon(scaledImg));
            } else {
                lblGambar.setIcon(null);
            }

            // Tambahkan hasil ke tabel
            javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblDataCuaca.getModel();
            model.addRow(new Object[]{kota, suhu, kelembapan, kondisi});

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Gagal mengambil data cuaca: " + e.getMessage());
    }        // TODO add your handling code here:
    }//GEN-LAST:event_btnCekCuacaActionPerformed

    private void btnSimpanKotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanKotaActionPerformed
        String kota = txtKota.getText().trim();
           if (kota.isEmpty()) {
               JOptionPane.showMessageDialog(this, "Masukkan nama kota terlebih dahulu!");
               return;
           }

           // Cek apakah kota sudah ada di combo box
           for (int i = 0; i < cmbKotaFavorite.getItemCount(); i++) {
               if (cmbKotaFavorite.getItemAt(i).equalsIgnoreCase(kota)) {
                   JOptionPane.showMessageDialog(this, "Kota sudah ada di daftar favorit!");
                   return;
               }
           }

           try (FileWriter writer = new FileWriter("favorit.txt", true)) {
               writer.write(kota + "\n");
               cmbKotaFavorite.addItem(kota);
               JOptionPane.showMessageDialog(this, "Kota \"" + kota + "\" disimpan ke favorit!");
           } catch (IOException ex) {
               JOptionPane.showMessageDialog(this, "Gagal menyimpan kota: " + ex.getMessage());
           }  // TODO add your handling code here:
    }//GEN-LAST:event_btnSimpanKotaActionPerformed

    private void btnImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportActionPerformed
      importFromCSV();     // TODO add your handling code here:
    }//GEN-LAST:event_btnImportActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
      exportToCSV();     // TODO add your handling code here:
    }//GEN-LAST:event_btnExportActionPerformed

    private void cmbKotaFavoriteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbKotaFavoriteKeyPressed
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_DELETE) {
                String kotaDipilih = (String) cmbKotaFavorite.getSelectedItem();
                if (kotaDipilih == null) return;

                int konfirmasi = JOptionPane.showConfirmDialog(
                        this,
                        "Yakin ingin menghapus kota \"" + kotaDipilih + "\" dari favorit?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION
                );

                if (konfirmasi == JOptionPane.YES_OPTION) {
                    try {
                        File file = new File("favorit.txt");
                        if (!file.exists()) return;

                        // Baca semua kota, kecuali yang dihapus
                        java.util.List<String> lines = new java.util.ArrayList<>();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                if (!line.trim().equalsIgnoreCase(kotaDipilih.trim())) {
                                    lines.add(line);
                                }
                            }
                        }

                        // Tulis ulang file tanpa kota tersebut
                        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                            for (String line : lines) {
                                writer.write(line);
                                writer.newLine();
                            }
                        }

                        // Hapus dari combo box
                        cmbKotaFavorite.removeItem(kotaDipilih);
                        JOptionPane.showMessageDialog(this, "Kota \"" + kotaDipilih + "\" telah dihapus.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "Gagal menghapus: " + ex.getMessage());
                    }
                }
            }        // TODO add your handling code here:
    }//GEN-LAST:event_cmbKotaFavoriteKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCekCuaca.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormCekCuaca().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCekCuaca;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnSimpanKota;
    private javax.swing.JComboBox<String> cmbKotaFavorite;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblGambar;
    private javax.swing.JLabel lblKelembapan;
    private javax.swing.JLabel lblKondisi;
    private javax.swing.JLabel lblSuhu;
    private javax.swing.JTable tblDataCuaca;
    private javax.swing.JTextField txtKota;
    // End of variables declaration//GEN-END:variables
}
