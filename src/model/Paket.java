package model;

public class Paket {

    private String no_resi;
    private String nama_pengirim;
    private String nama_penerima;
    private String alamat_tujuan;
    private String nama_barang;
    private String jenis_layanan;
    private double berat_kg;
    private double total_biaya;
    private String status_paket;
    private int id_kurir;
    private String nama_kurir;

    public static final String STATUS_DIPROSES = "Diproses";
    public static final String STATUS_SEDANG_DIKIRIM = "Sedang Dikirim";
    public static final String STATUS_TERKIRIM = "Terkirim";

    public Paket() {
    }

    public Paket(String no_resi, String nama_pengirim, String nama_penerima,
                 String alamat_tujuan, String nama_barang, String jenis_layanan,
                 double berat_kg, double total_biaya, String status_paket, int id_kurir) {
        this.no_resi = no_resi;
        this.nama_pengirim = nama_pengirim;
        this.nama_penerima = nama_penerima;
        this.alamat_tujuan = alamat_tujuan;
        this.nama_barang = nama_barang;
        this.jenis_layanan = jenis_layanan;
        this.berat_kg = berat_kg;
        this.total_biaya = total_biaya;
        this.status_paket = status_paket;
        this.id_kurir = id_kurir;
    }

    public Paket(String no_resi, String nama_pengirim, String nama_penerima,
                 String alamat_tujuan, String nama_barang, String jenis_layanan,
                 double berat_kg, double total_biaya, String status_paket, int id_kurir,
                 String nama_kurir) {
        this(no_resi, nama_pengirim, nama_penerima, alamat_tujuan, nama_barang,
                jenis_layanan, berat_kg, total_biaya, status_paket, id_kurir);
        this.nama_kurir = nama_kurir;
    }

    public String getNo_resi() {
        return no_resi;
    }

    public void setNo_resi(String no_resi) {
        this.no_resi = no_resi;
    }

    public String getNama_pengirim() {
        return nama_pengirim;
    }

    public void setNama_pengirim(String nama_pengirim) {
        this.nama_pengirim = nama_pengirim;
    }

    public String getNama_penerima() {
        return nama_penerima;
    }

    public void setNama_penerima(String nama_penerima) {
        this.nama_penerima = nama_penerima;
    }

    public String getAlamat_tujuan() {
        return alamat_tujuan;
    }

    public void setAlamat_tujuan(String alamat_tujuan) {
        this.alamat_tujuan = alamat_tujuan;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getJenis_layanan() {
        return jenis_layanan;
    }

    public void setJenis_layanan(String jenis_layanan) {
        this.jenis_layanan = jenis_layanan;
    }

    public double getBerat_kg() {
        return berat_kg;
    }

    public void setBerat_kg(double berat_kg) {
        this.berat_kg = berat_kg;
    }

    public double getTotal_biaya() {
        return total_biaya;
    }

    public void setTotal_biaya(double total_biaya) {
        this.total_biaya = total_biaya;
    }

    public String getStatus_paket() {
        return status_paket;
    }

    public void setStatus_paket(String status_paket) {
        this.status_paket = status_paket;
    }

    public int getId_kurir() {
        return id_kurir;
    }

    public void setId_kurir(int id_kurir) {
        this.id_kurir = id_kurir;
    }

    public String getNama_kurir() {
        return nama_kurir;
    }

    public void setNama_kurir(String nama_kurir) {
        this.nama_kurir = nama_kurir;
    }
}
