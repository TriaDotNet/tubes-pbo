package controller;

import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.LayananEkspedisi;
import model.Paket;
import model.PaketDAO;

public class PaketController {

    private final PaketDAO dao;

    public PaketController() {
        this.dao = new PaketDAO();
    }

    public double hitungOngkir(String jenisLayanan, double berat) {
        LayananEkspedisi layanan = LayananEkspedisi.fromJenis(jenisLayanan);
        return layanan.hitungOngkir(berat);
    }

    public void insert(String noResi, String pengirim, String penerima, String alamat,
                       String jenisLayanan, double berat, int idKurir) {
        double total = hitungOngkir(jenisLayanan, berat);
        Paket p = new Paket(noResi, pengirim, penerima, alamat,
                jenisLayanan, berat, total, idKurir);
        dao.setPaket(p);
        dao.insert();
    }

    public void update(String noResi, String pengirim, String penerima, String alamat,
                       String jenisLayanan, double berat, int idKurir) {
        double total = hitungOngkir(jenisLayanan, berat);
        Paket p = new Paket(noResi, pengirim, penerima, alamat,
                jenisLayanan, berat, total, idKurir);
        dao.setPaket(p);
        dao.update();
    }

    public void delete(String noResi) {
        Paket p = new Paket();
        p.setNo_resi(noResi);
        dao.setPaket(p);
        dao.delete();
    }

    public List<Paket> getAll() {
        return dao.getAll();
    }

    public boolean isResiExist(String no_resi) {
        return dao.isResiExist(no_resi);
    }

    public void loadTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"No Resi", "Pengirim", "Penerima", "Alamat Tujuan",
                             "Layanan", "Berat (kg)", "Total Biaya", "Kurir"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Paket p : getAll()) {
            model.addRow(new Object[]{
                    p.getNo_resi(),
                    p.getNama_pengirim(),
                    p.getNama_penerima(),
                    p.getAlamat_tujuan(),
                    p.getJenis_layanan(),
                    p.getBerat_kg(),
                    p.getTotal_biaya(),
                    p.getNama_kurir()
            });
        }
        table.setModel(model);
    }
}
