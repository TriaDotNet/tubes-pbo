package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import koneksi.KoneksiDB;

public class PaketDAO implements IDAO {

    private Paket paket;

    public PaketDAO() {
    }

    public PaketDAO(Paket paket) {
        this.paket = paket;
    }

    public void setPaket(Paket paket) {
        this.paket = paket;
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO tabel_paket (no_resi, nama_pengirim, nama_penerima, alamat_tujuan, "
                   + "jenis_layanan, berat_kg, total_biaya, id_kurir) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paket.getNo_resi());
            ps.setString(2, paket.getNama_pengirim());
            ps.setString(3, paket.getNama_penerima());
            ps.setString(4, paket.getAlamat_tujuan());
            ps.setString(5, paket.getJenis_layanan());
            ps.setDouble(6, paket.getBerat_kg());
            ps.setDouble(7, paket.getTotal_biaya());
            ps.setInt(8, paket.getId_kurir());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data paket berhasil ditambahkan.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal insert paket: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update() {
        String sql = "UPDATE tabel_paket SET nama_pengirim = ?, nama_penerima = ?, alamat_tujuan = ?, "
                   + "jenis_layanan = ?, berat_kg = ?, total_biaya = ?, id_kurir = ? WHERE no_resi = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paket.getNama_pengirim());
            ps.setString(2, paket.getNama_penerima());
            ps.setString(3, paket.getAlamat_tujuan());
            ps.setString(4, paket.getJenis_layanan());
            ps.setDouble(5, paket.getBerat_kg());
            ps.setDouble(6, paket.getTotal_biaya());
            ps.setInt(7, paket.getId_kurir());
            ps.setString(8, paket.getNo_resi());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data paket berhasil diperbarui.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal update paket: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM tabel_paket WHERE no_resi = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paket.getNo_resi());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data paket berhasil dihapus.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal hapus paket: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Paket> getAll() {
        List<Paket> list = new ArrayList<>();
        String sql = "SELECT p.no_resi, p.nama_pengirim, p.nama_penerima, p.alamat_tujuan, "
                   + "p.jenis_layanan, p.berat_kg, p.total_biaya, p.id_kurir, k.nama_kurir "
                   + "FROM tabel_paket p "
                   + "LEFT JOIN tabel_kurir k ON p.id_kurir = k.id_kurir "
                   + "ORDER BY p.no_resi";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Paket(
                        rs.getString("no_resi"),
                        rs.getString("nama_pengirim"),
                        rs.getString("nama_penerima"),
                        rs.getString("alamat_tujuan"),
                        rs.getString("jenis_layanan"),
                        rs.getDouble("berat_kg"),
                        rs.getDouble("total_biaya"),
                        rs.getInt("id_kurir"),
                        rs.getString("nama_kurir")
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data paket: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public boolean isResiExist(String no_resi) {
        String sql = "SELECT 1 FROM tabel_paket WHERE no_resi = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, no_resi);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal cek resi: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }
}
