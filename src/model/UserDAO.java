package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import koneksi.KoneksiDB;

public class UserDAO implements IDAO {

    private User user;

    public UserDAO() {
    }

    public UserDAO(User user) {
        this.user = user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void insert() {
        String sql = "INSERT INTO tabel_user (username, password, role, id_kurir) VALUES (?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            if (user.getId_kurir() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, user.getId_kurir());
            }
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data user berhasil ditambahkan.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal insert user: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void update() {
        String sql = "UPDATE tabel_user SET username = ?, password = ?, role = ?, id_kurir = ? WHERE id_user = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            if (user.getId_kurir() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, user.getId_kurir());
            }
            ps.setInt(5, user.getId_user());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data user berhasil diperbarui.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal update user: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void delete() {
        String sql = "DELETE FROM tabel_user WHERE id_user = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, user.getId_user());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data user berhasil dihapus.");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal hapus user: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> list = new ArrayList<>();
        String sql = "SELECT id_user, username, password, role, id_kurir FROM tabel_user ORDER BY id_user";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal ambil data user: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return list;
    }

    public User findByCredentials(String username, String password) {
        String sql = "SELECT id_user, username, password, role, id_kurir "
                   + "FROM tabel_user WHERE username = ? AND password = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error saat login: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public boolean isUsernameExist(String username) {
        String sql = "SELECT 1 FROM tabel_user WHERE username = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal cek username: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void insertSilent(User user) {
        String sql = "INSERT INTO tabel_user (username, password, role, id_kurir) VALUES (?, ?, ?, ?)";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getRole());
            if (user.getId_kurir() == null) {
                ps.setNull(4, Types.INTEGER);
            } else {
                ps.setInt(4, user.getId_kurir());
            }
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal insert user: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void deleteByKurir(int idKurir) {
        String sql = "DELETE FROM tabel_user WHERE id_kurir = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKurir);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal hapus akun kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public User findByKurir(int idKurir) {
        String sql = "SELECT id_user, username, password, role, id_kurir "
                   + "FROM tabel_user WHERE id_kurir = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKurir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal ambil akun kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void updatePasswordByKurir(int idKurir, String newUsername, String newPassword) {
        String sql = "UPDATE tabel_user SET username = ?, password = ? WHERE id_kurir = ?";
        try (Connection conn = KoneksiDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newUsername);
            ps.setString(2, newPassword);
            ps.setInt(3, idKurir);
            ps.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Gagal update akun kurir: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        int idKurir = rs.getInt("id_kurir");
        Integer idKurirBoxed = rs.wasNull() ? null : idKurir;
        return new User(
                rs.getInt("id_user"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("role"),
                idKurirBoxed
        );
    }
}
