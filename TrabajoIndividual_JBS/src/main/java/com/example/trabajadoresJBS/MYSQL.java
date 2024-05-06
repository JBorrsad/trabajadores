package com.example.trabajadoresJBS;


import java.sql.*;
import java.time.LocalDate;

public class MYSQL {
    private static final String URL = "jdbc:mysql://localhost:3306/trabajadores";
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "root";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA);
    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public static void cerrarRecursos(PreparedStatement statement, ResultSet resultSet) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el PreparedStatement: " + e.getMessage());
            }
        }
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar el ResultSet: " + e.getMessage());
            }
        }
    }

    public static void insertarTrabajador(Trabajador trabajador) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = obtenerConexion();
            if (conexion != null) {
                String sql = "INSERT INTO trabajador (nombre, puesto, salario, fecha_alta) VALUES (?, ?, ?, ?)";
                statement = conexion.prepareStatement(sql);
                statement.setString(1, trabajador.getNombre());
                statement.setString(2, trabajador.getPuesto());
                statement.setInt(3, trabajador.getSalario());
                statement.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
                statement.executeUpdate();
                System.out.println("Trabajador insertado correctamente en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar el trabajador en la base de datos: " + e.getMessage());
        } finally {
            cerrarRecursos(statement, null);
            cerrarConexion(conexion);
        }
    }

    public static void actualizarTrabajador(Trabajador trabajador) {
        String sql = "UPDATE trabajadores SET nombre = ?, puesto = ?, salario = ? WHERE id = ?";

        try (Connection conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA); PreparedStatement declaracion = conexion.prepareStatement(sql)) {

            // Establecer los parámetros de la consulta
            declaracion.setString(1, trabajador.getNombre());
            declaracion.setString(2, trabajador.getPuesto());
            declaracion.setInt(3, trabajador.getSalario());
            declaracion.setString(4, trabajador.getId());

            // Ejecutar la consulta
            declaracion.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar el trabajador en la base de datos: " + e.getMessage());
        }
    }


    public static void eliminarTrabajador(Trabajador trabajador) {
        Connection conexion = null;
        PreparedStatement statement = null;
        try {
            conexion = obtenerConexion();
            if (conexion != null) {
                String sql = "DELETE FROM trabajador WHERE nombre = ? AND puesto = ? AND salario = ?";
                statement = conexion.prepareStatement(sql);
                statement.setString(1, trabajador.getNombre());
                statement.setString(2, trabajador.getPuesto());
                statement.setInt(3, trabajador.getSalario());
                statement.executeUpdate();
                System.out.println("Trabajador eliminado correctamente de la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar el trabajador de la base de datos: " + e.getMessage());
        } finally {
            cerrarRecursos(statement, null);
            cerrarConexion(conexion);
        }
    }


}

