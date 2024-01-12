import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class AppFrame extends JFrame {

    private InfoPanel infoPanel;

    public AppFrame() {
        DBHandler handler = new DBHandler();

        setTitle("Contacts list");
        setSize(500, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new GridLayout(0, 2));

        JTable table = new JTable(new DefaultTableModel(handler.getNicknames(), new String[] {""}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane list = new JScrollPane(table);
        add(list);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = table.rowAtPoint(e.getPoint());
                    int col = table.columnAtPoint(e.getPoint());

                    // Check if the click is within the table bounds
                    if (row >= 0 && col >= 0) {
                        // Get the value of the clicked cell
                        String nickname = (String) table.getValueAt(row, col);
                        infoPanel.reset(handler.getContactByNickname(nickname));
                    }
                }
            }
        });

        infoPanel = new InfoPanel();
        add(infoPanel);

        setVisible(true);
    }
}
