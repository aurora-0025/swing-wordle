package Wordle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import Wordle.db.SQLiteConnector;
import Wordle.utils.RoundedBorder;
import Wordle.utils.Colors.TileColor;

public class LeaderboardScreen extends JPanel {

    public JButton closeButton = new JButton("X");
    public LeaderboardScreen() {
        SQLiteConnector db = new SQLiteConnector();
        setOpaque(false);
        setLayout(new BorderLayout());
        JPanel lbContainer = new JPanel();
        lbContainer.setOpaque(false);
        lbContainer.setLayout(new BoxLayout(lbContainer, BoxLayout.Y_AXIS));
        closeButton.setFocusable(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder());
        closeButton.setOpaque( false );
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.setFont(new Font("Clear Sans", Font.PLAIN, 16));
        closeButton.setContentAreaFilled(false);
        closeButton.setForeground(Color.WHITE);

        JLabel headingLabel = new JLabel("Leaderboard");
        headingLabel.setBackground(new Color(TileColor.NOT_FOUND));
        headingLabel.setForeground(Color.WHITE);
        headingLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 26));

        Box closeButtonContainer = Box.createHorizontalBox();
        closeButtonContainer.add(Box.createHorizontalStrut(10));
        closeButtonContainer.add(headingLabel);
        closeButtonContainer.add(Box.createHorizontalGlue());
        closeButtonContainer.add(closeButton);
        closeButtonContainer.add(Box.createHorizontalStrut(10));
        lbContainer.add(closeButtonContainer);
        lbContainer.add(Box.createVerticalStrut(20));

        String[] columnNames = { "#", "User", "wins", "Streak" };

        DefaultTableModel lbTableModel = new DefaultTableModel(columnNames, 0);
        try {
            ResultSet lbData = db.getLeaderBoard();
            int pos = 1;
            while(lbData.next()) {
                String[] item={String.valueOf(pos), lbData.getString("username"), lbData.getString("wins"), lbData.getString("maxStreak")};
                lbTableModel.addRow(item);
                pos++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JTable lbTable = new JTable(lbTableModel);
        JScrollPane sp = new JScrollPane(lbTable);
        sp.getViewport().setBackground(new Color(0x121213));
        sp.setBorder(new EmptyBorder(0, 0, 0, 0));

        lbTable.setEnabled(false);
        resizeColumnWidth(lbTable);
        lbTable.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        lbTable.setFillsViewportHeight(true);
        lbTable.setShowGrid(false);
        lbTable.setRowHeight(25);
        lbTable.setBackground(new Color(0x121213));
        lbTable.setForeground(Color.WHITE);
        lbTable.getTableHeader().setOpaque(false);
        lbTable.getTableHeader().setBorder(new LineBorder(new Color(0x22c55e), 1, true));
        lbTable.getTableHeader().setBackground(Color.CYAN);
        lbTable.getTableHeader().setResizingAllowed(false);
        lbTable.getTableHeader().setReorderingAllowed(false);

        final DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        final DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();

        cellRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        cellRenderer.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        headerRenderer.setHorizontalAlignment( SwingConstants.CENTER );

        for (int i = 0; i < lbTable.getColumnCount(); i++) {
            TableColumn currentCol = lbTable.getColumnModel().getColumn(i);
            currentCol.setCellRenderer(cellRenderer);
        }
        lbTable.getTableHeader().setDefaultRenderer(headerRenderer);
        lbTable.setBorder(new LineBorder(new Color(0x22c55e), 1, true));
        lbContainer.add(sp);
        add(lbContainer, BorderLayout.CENTER);
    }

    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 70; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            if(width > 100)
                width=100;
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
}

