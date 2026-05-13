package model;

public abstract class LayananEkspedisi {

    private String namaLayanan;

    public LayananEkspedisi() {
    }

    public LayananEkspedisi(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public String getNamaLayanan() {
        return namaLayanan;
    }

    public void setNamaLayanan(String namaLayanan) {
        this.namaLayanan = namaLayanan;
    }

    public abstract double hitungOngkir(double berat);

    public static LayananEkspedisi fromJenis(String jenis) {
        if (jenis == null) return new LayananReguler();
        switch (jenis) {
            case "Express":
                return new LayananExpress();
            case "Cargo":
                return new LayananCargo();
            case "Reguler":
            default:
                return new LayananReguler();
        }
    }
}
