package controller;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Kurir;
import model.KurirDAO;

public class KurirController {

    private final KurirDAO dao;

    public KurirController() {
        this.dao = new KurirDAO();
    }

    public void insert(Kurir kurir) {
        dao.setKurir(kurir);
        dao.insert();
    }

    public void update(Kurir kurir) {
        dao.setKurir(kurir);
        dao.update();
    }

    public void delete(Kurir kurir) {
        dao.setKurir(kurir);
        dao.delete();
    }

    public List<Kurir> getAll() {
        return dao.getAll();
    }

    public void loadTable(JTable table) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Kurir", "Nama Kurir", "No Plat", "No HP"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        for (Kurir k : getAll()) {
            model.addRow(new Object[]{
                    k.getId_kurir(),
                    k.getNama_kurir(),
                    k.getNo_plat(),
                    k.getNo_hp()
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
