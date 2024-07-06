import dto.UserDTO;

import java.sql.*;

public class SetearVariablesDB {


    public static void registrarDTO(UserDTO dto) {
        // Conexión a la base de datos PostgreSQL
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://10.36.191.35:5444/PRUGESTORA", "prugestora", "pruebas2023*")) {
            // Crear la sentencia SQL para establecer las variables de sesión
            String sql1 = "SET  session.osuser = '" + dto.getUsername() + "'";
            String sql2 = "SET  session.ip = '" + dto.getIp() + "'";

            // Crear las declaraciones SQL
            try (Statement stmt = conn.createStatement()) {
                // Ejecutar las declaraciones SQL
                stmt.executeUpdate(sql1);
                stmt.executeUpdate(sql2);

                System.out.println("Valores de sesión actualizados correctamente.");


                // Ahora, ejecutamos la consulta antes de realizar la inserción
                String consulta = "SELECT COALESCE(current_setting('session.osuser', 'true'), 'No_Identificado')";
                try (PreparedStatement stmtConsulta = conn.prepareStatement(consulta)) {
                    // Ejecutar la consulta
                    ResultSet rs = stmtConsulta.executeQuery();
                    if (rs.next()) {
                        String resultadoConsulta = rs.getString(1);
                        System.out.println("Respuesta de la consulta: " + resultadoConsulta);
                    } else {
                        System.out.println("La consulta no devolvió ningún resultado.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la consulta: " + e.getMessage());
                }


                String sql3 = "INSERT INTO obl_alertas.tsis_configurationalertbk (canid, candsdescription, cannmexpirationdays) VALUES(?, ?, ?)";
                try (PreparedStatement stmt3 = conn.prepareStatement(sql3)) {
                    // Establecer los valores para la inserción
                    stmt3.setInt(1, 12);  // canid
                    stmt3.setString(2, "Prueba Historicos 1"); // candsdescription
                    stmt3.setInt(3, 30); // cannmexpirationdays

                    // Ejecutar la inserción
                    stmt3.executeUpdate();

                    System.out.println("Inserción realizada correctamente.");
                } catch (SQLException e) {
                    System.out.println("Error al ejecutar la inserción: " + e.getMessage());
                }




            } catch (SQLException e) {
                System.out.println("Error al ejecutar la declaración SQL: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    public static void main(String[] args){

        // Crear un objeto DTO con valores de ejemplo
        UserDTO dto = new UserDTO();
        dto.setUsername("usuario_ejemplo");
        dto.setIp("192.168.1.100");

        registrarDTO(dto);

    }

}
