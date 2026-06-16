package br.com.calculadoraorganizacional.main.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoMySQL {

    private static final String URL =
            "jdbc:mysql://localhost:3306/calculadora_organizacional";

    private static final String USUARIO = "root";

    private static final String SENHA = "root";

    public static Connection conectar() {

        try {

            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);

            System.out.println("Conectado com sucesso!");

            return conn;

        } catch (SQLException e) {

            System.out.println("Erro ao conectar!");
            e.printStackTrace();

            return null;
        }
    }
}