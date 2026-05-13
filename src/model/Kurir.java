package model;

public class Kurir {

    private int id_kurir;
    private String nama_kurir;
    private String no_plat;
    private String no_hp;

    public Kurir() {
    }

    public Kurir(int id_kurir, String nama_kurir, String no_plat, String no_hp) {
        this.id_kurir = id_kurir;
        this.nama_kurir = nama_kurir;
        this.no_plat = no_plat;
        this.no_hp = no_hp;
    }

    public Kurir(String nama_kurir, String no_plat, String no_hp) {
        this.nama_kurir = nama_kurir;
        this.no_plat = no_plat;
        this.no_hp = no_hp;
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

    public String getNo_plat() {
        return no_plat;
    }

    public void setNo_plat(String no_plat) {
        this.no_plat = no_plat;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }
}
