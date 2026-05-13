package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import koneksi.KoneksiDB;

public class KurirDAO implements IDAO {

    private Kurir kurir;

    public KurirDAO() {
    }

    public KurirDAO(Kurir kurir) {
        this.kurir = kurir;
    }

    public void setKurir(Kurir kurir) {
        this.kurir = kurir;
    }

    @Override
    public void insert() {
        insertAndGetId();
    }

    public int insertAndGetId() {
        String sql = "INSERT INTO tabel_kurir (nama_kurir, no_plat, no_hp) VALUES (?, ?, ?)";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                     java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, kurir.getNama_kurir());
            ps.setString(2, kurir.getNo_plat());
            ps.setString(3, kurir.getNo_hp());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    int id = keys.getInt(1);
                    kurir.setId_kurir(id);
                    return id;
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal insert kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }

    @Override
    public void update() {
        String sql = "UPDATE tabel_kurir SET nama_kurir = ?, no_plat = ?, no_hp = ? WHERE id_kurir = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kurir.getNama_kurir());
            ps.setString(2, kurir.getNo_plat());
            ps.setString(3, kurir.getNo_hp());
            ps.setInt(4, kurir.getId_kurir());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data kurir berhasil diperbarui.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal update kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM tabel_kurir WHERE id_kurir = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, kurir.getId_kurir());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data kurir berhasil dihapus.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal hapus kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Kurir> getAll() {
        List<Kurir> list = new ArrayList<>();
        String sql = "SELECT id_kurir, nama_kurir, no_plat, no_hp FROM tabel_kurir ORDER BY id_kurir";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Kurir(
                        rs.getInt("id_kurir"),
                        rs.getString("nama_kurir"),
                        rs.getString("no_plat"),
                        rs.getString("no_hp")
                ));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }
}
