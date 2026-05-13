package model;

public class LayananReguler extends LayananEkspedisi {

    public LayananReguler() {
        super("Reguler");
    }

    @Override
    public double hitungOngkir(double berat) {
        return berat * 10000;
    }
}
