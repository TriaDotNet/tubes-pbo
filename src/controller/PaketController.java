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
                       String namaBarang, String jenisLayanan, double berat, int idKurir) {
        double total = hitungOngkir(jenisLayanan, berat);
        Paket p = new Paket(noResi, pengirim, penerima, alamat, namaBarang,
                jenisLayanan, berat, total, Paket.STATUS_DIPROSES, idKurir);
        dao.setPaket(p);
        dao.insert();
    }

    public void update(String noResi, String pengirim, String penerima, String alamat,
                       String namaBarang, String jenisLayanan, double berat,
                       String statusPaket, int idKurir) {
        double total = hitungOngkir(jenisLayanan, berat);
        Paket p = new Paket(noResi, pengirim, penerima, alamat, namaBarang,
                jenisLayanan, berat, total, statusPaket, idKurir);
        dao.setPaket(p);
        dao.update();
    }

    public void delete(String noResi) {
        Paket p = new Paket();
        p.setNo_resi(noResi);
        dao.setPaket(p);
        dao.delete();
    }

    public void updateStatus(String noResi, String status) {
        dao.updateStatus(noResi, status);
    }

    public boolean updateStatusByKurir(String noResi, String status, int idKurir) {
        return dao.updateStatusByKurir(noResi, status, idKurir);
    }

    public List<Paket> getAll() {
        return dao.getAll();
    }

    public List<Paket> getByKurir(int idKurir) {
        return dao.getByKurir(idKurir);
    }

    public boolean isResiExist(String no_resi) {
        return dao.isResiExist(no_resi);
    }

    public void loadTable(JTable table) {
        DefaultTableModel model = buildModel();
        for (Paket p : getAll()) {
            model.addRow(rowOf(p));
        }
        table.setModel(model);
    }

    public void loadTableByKurir(JTable table, int idKurir) {
        DefaultTableModel model = buildModel();
        for (Paket p : getByKurir(idKurir)) {
            model.addRow(rowOf(p));
        }
        table.setModel(model);
    }

    private DefaultTableModel buildModel() {
        return new DefaultTableModel(
                new Object[]{"No Resi", "Pengirim", "Penerima", "Alamat Tujuan",
                             "Nama Barang", "Layanan", "Berat (kg)", "Total Biaya",
                             "Status", "Kurir"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private Object[] rowOf(Paket p) {
        return new Object[]{
                p.getNo_resi(),
                p.getNama_pengirim(),
                p.getNama_penerima(),
                p.getAlamat_tujuan(),
                p.getNama_barang(),
                p.getJenis_layanan(),
                p.getBerat_kg(),
                p.getTotal_biaya(),
                p.getStatus_paket(),
                p.getNama_kurir()
        };
    }
}
