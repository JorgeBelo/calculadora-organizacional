package br.com.calculadoraorganizacional.dao;

import br.com.calculadoraorganizacional.ConexaoMySQL;
import br.com.calculadoraorganizacional.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // CADASTRAR USUÁRIO

    public void cadastrarUsuario(Usuario usuario) {

        String sql =
                "INSERT INTO usuarios (nome, login, senha) VALUES (?, ?, ?)";

        try {

            Connection conn = ConexaoMySQL.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, usuario.getNome());

            stmt.setString(2, usuario.getLogin());

            stmt.setString(3, usuario.getSenha());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {

                System.out.println("Usuário cadastrado com sucesso!");

            } else {

                System.out.println("Nenhum usuário foi cadastrado!");
            }

            stmt.close();

            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro ao cadastrar usuário!");

            e.printStackTrace();
        }
    }

    // LOGIN

    public Usuario realizarLogin(String login, String senha) {

        String sql =
                "SELECT * FROM usuarios WHERE login = ? AND senha = ?";

        try {

            Connection conn = ConexaoMySQL.conectar();

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, login);

            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));

                usuario.setNome(rs.getString("nome"));

                usuario.setLogin(rs.getString("login"));

                usuario.setSenha(rs.getString("senha"));

                return usuario;
            }

            rs.close();

            stmt.close();

            conn.close();

        } catch (SQLException e) {

            System.out.println("Erro ao realizar login!");

            e.printStackTrace();
        }

        return null;
    }
}