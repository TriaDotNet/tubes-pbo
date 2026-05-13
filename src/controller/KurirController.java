package controller;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Kurir;
import model.KurirDAO;
import model.User;
import model.UserDAO;

public class KurirController {

    private final KurirDAO dao;
    private final UserDAO userDao;

    public KurirController() {
        this.dao = new KurirDAO();
        this.userDao = new UserDAO();
    }

    public void insert(Kurir kurir) {
        dao.setKurir(kurir);
        dao.insert();
    }

    public boolean insertWithAccount(Kurir kurir, String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Username dan password akun kurir wajib diisi.",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (userDao.isUsernameExist(username.trim())) {
            JOptionPane.showMessageDialog(null,
                    "Username '" + username.trim() + "' sudah dipakai.",
                    "Duplikat", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        dao.setKurir(kurir);
        int idKurir = dao.insertAndGetId();
        if (idKurir <= 0) {
            return false;
        }

        User akun = new User();
        akun.setUsername(username.trim());
        akun.setPassword(password.trim());
        akun.setRole("Kurir");
        akun.setId_kurir(idKurir);
        userDao.insertSilent(akun);

        JOptionPane.showMessageDialog(null,
                "Kurir & akun login berhasil dibuat.\nUsername: " + username.trim(),
                "Sukses", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    public void update(Kurir kurir) {
        dao.setKurir(kurir);
        dao.update();
    }

    public void updateAccount(int idKurir, String username, String password) {
        if (username == null || username.trim().isEmpty()
                || password == null || password.trim().isEmpty()) {
            return;
        }
        User existing = userDao.findByKurir(idKurir);
        if (existing == null) {
            User akun = new User();
            akun.setUsername(username.trim());
            akun.setPassword(password.trim());
            akun.setRole("Kurir");
            akun.setId_kurir(idKurir);
            if (userDao.isUsernameExist(username.trim())) {
                JOptionPane.showMessageDialog(null,
                        "Username '" + username.trim() + "' sudah dipakai user lain.",
                        "Duplikat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            userDao.insertSilent(akun);
            JOptionPane.showMessageDialog(null, "Akun login kurir berhasil dibuat.");
        } else {
            if (!existing.getUsername().equals(username.trim())
                    && userDao.isUsernameExist(username.trim())) {
                JOptionPane.showMessageDialog(null,
                        "Username '" + username.trim() + "' sudah dipakai user lain.",
                        "Duplikat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            userDao.updatePasswordByKurir(idKurir, username.trim(), password.trim());
            JOptionPane.showMessageDialog(null, "Akun login kurir berhasil diperbarui.");
        }
    }

    public void delete(Kurir kurir) {
        userDao.deleteByKurir(kurir.getId_kurir());
        dao.setKurir(kurir);
        dao.delete();
    }

    public User getAccountByKurir(int idKurir) {
        return userDao.findByKurir(idKurir);
    }

    public List<Kurir> getAll() {
        return dao.getAll();
    }

    public void loadTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Kurir", "Nama Kurir", "No Plat", "No HP", "Username"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Kurir k : getAll()) {
            User akun = userDao.findByKurir(k.getId_kurir());
            model.addRow(new Object[]{
                    k.getId_kurir(),
                    k.getNama_kurir(),
                    k.getNo_plat(),
                    k.getNo_hp(),
                    akun != null ? akun.getUsername() : "-"
            });
        }
        table.setModel(model);
    }

    public void loadComboBox(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        for (Kurir k : getAll()) {
            comboBox.addItem(k.getId_kurir() + " - " + k.getNama_kurir());
        }
    }
}
