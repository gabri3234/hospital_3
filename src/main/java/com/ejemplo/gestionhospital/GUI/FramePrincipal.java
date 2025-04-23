package com.ejemplo.gestionhospital.GUI;
import com.ejemplo.gestionhospital.ConexionDB;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FramePrincipal extends JFrame {

    private JTextArea prueba = new JTextArea();

    public FramePrincipal(){
        setTitle("Hospital Flow");
        setSize(1600, 900);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        this.add(prueba);
        mostrarTablas();

        setVisible(true);

    }


    public void mostrarTablas(){
        try (Connection conn = ConexionDB.getConnection()) {

            DatabaseMetaData metaData = conn.getMetaData();

            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                prueba.append("\n📦 Tabla: " + tableName);

                // Obtener columnas
                ResultSet columns = metaData.getColumns(null, null, tableName, "%");
                prueba.append("🔠 Columnas: ");
                while (columns.next()) {
                    prueba.append(columns.getString("COLUMN_NAME") + " | ");
                }


                // Mostrar filas
                Statement stmt = conn.createStatement();
                ResultSet rows = stmt.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData rsMeta = rows.getMetaData();
                int columnCount = rsMeta.getColumnCount();

                while (rows.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        prueba.append(rsMeta.getColumnName(i) + ": " + rows.getString(i) + " | ");
                    }
                }

                rows.close();
                columns.close();
            }
            tables.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
}
}
