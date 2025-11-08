# AplikasiCekCuaca
Tugas 6 - Galih Yudha Haryanto (2310010440)

# 🌦️ Aplikasi Cek Cuaca Sederhana

Aplikasi desktop berbasis **Java Swing** untuk menampilkan informasi cuaca secara real-time menggunakan **API OpenWeatherMap**.  
Dilengkapi fitur **penyimpanan kota favorit**, **ekspor & impor data ke file CSV**, serta tampilan gambar kondisi cuaca.

---

## 🧱 Fitur Utama

✅ **Cek Cuaca Real-Time**  
- Mengambil data dari API eksternal OpenWeatherMap berdasarkan nama kota yang dimasukkan pengguna.  
- Menampilkan **suhu**, **kelembapan**, dan **kondisi cuaca** (Cerah, Hujan, Berawan, dll).  
- Menampilkan **ikon cuaca otomatis** sesuai kondisi (gambar cerah, mendung, hujan, dsb).

✅ **Kota Favorit**  
- Menyimpan kota yang sering dicek ke file `favorit.txt`.  
- Kota favorit otomatis dimuat kembali saat aplikasi dijalankan.  
- Bisa dihapus dengan menekan tombol **Delete** pada keyboard saat memilih di combo box.

✅ **Ekspor & Impor Data CSV**  
- Menyimpan data hasil cuaca dari tabel ke file `.csv`.  
- Dapat memuat ulang data cuaca yang sudah diekspor.  
- Format CSV aman dan tervalidasi (header: `Kota,Suhu (°C),Kelembapan (%),Kondisi`).

✅ **Tabel Data Cuaca (JTable)**  
- Menampilkan daftar hasil pengecekan cuaca lengkap.  
- Dapat diekspor ke file CSV agar data bisa diakses kembali.

---

## 🧩 Komponen GUI yang Digunakan
| Komponen | Nama Variabel | Fungsi |
|-----------|----------------|--------|
| JFrame | `FormCekCuaca` | Wadah utama aplikasi |
| JPanel | `jPanel1` | Panel utama untuk menyusun komponen |
| JLabel | `lblSuhu`, `lblKelembapan`, `lblKondisi`, `lblGambar` | Menampilkan hasil cuaca dan ikon |
| JTextField | `txtKota` | Input nama kota |
| JComboBox | `cmbKotaFavorite` | Menampilkan daftar kota favorit |
| JButton | `btnCekCuaca`, `btnSimpanKota`, `btnExport`, `btnImport` | Tombol interaksi utama |
| JTable | `tblDataCuaca` | Menampilkan data hasil cuaca |
| JScrollPane | `jScrollPane1` | Membungkus tabel agar bisa discroll |


---

## ⚙️ Cara Menggunakan

### 1️⃣ Jalankan Program
Buka proyek di **NetBeans** dan jalankan kelas utama:
### 2️⃣ Cek Cuaca
1. Ketik nama kota di kolom **"Kota"**.  
2. Klik tombol **"CEK CUACA"**.  
3. Data cuaca akan muncul di label dan tabel.

### 3️⃣ Simpan Kota Favorit
1. Setelah mengecek cuaca, klik tombol **"SIMPAN"**.  
2. Kota akan tersimpan ke `favorit.txt` dan muncul di combo box.  
3. Kota favorit otomatis dimuat saat aplikasi dijalankan ulang.

### 4️⃣ Hapus Kota Favorit
- Pilih kota di combo box, lalu tekan **tombol Delete** pada keyboard.  
- Kota akan dihapus dari daftar dan file `favorit.txt`.

### 5️⃣ Ekspor Data Cuaca
1. Klik tombol **"EXPORT"**.  
2. Pilih lokasi dan beri nama file `.csv`.  
3. Data dari tabel akan disimpan ke file tersebut.

### 6️⃣ Impor Data Cuaca
1. Klik tombol **"IMPORT"**.  
2. Pilih file `.csv` dengan format yang sesuai.  
3. Data akan dimuat ke tabel aplikasi.

---

## 🔑 API yang Digunakan

**[OpenWeatherMap API](https://openweathermap.org/api)
