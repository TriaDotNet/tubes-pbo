package model;

public class LayananExpress extends LayananEkspedisi {

    public LayananExpress() {
        super("Express");
    }

    @Override
    public double hitungOngkir(double berat) {
        return berat * 15000;
    }
}
