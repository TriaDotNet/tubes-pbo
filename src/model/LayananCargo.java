package model;

public class LayananCargo extends LayananEkspedisi {

    public LayananCargo() {
        super("Cargo");
    }

    @Override
    public double hitungOngkir(double berat) {
        return berat * 5000;
    }
}
