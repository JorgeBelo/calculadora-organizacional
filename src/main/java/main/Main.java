package br.com.calculadoraorganizacional.main;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

public class Main {

    public static void main(String[] args) {

        Usuario usuario = new Usuario();

        usuario.setNome("Jorge");
        usuario.setLogin("jorge");
        usuario.setSenha("123");

        UsuarioDAO dao = new UsuarioDAO();

        dao.cadastrarUsuario(usuario);
    }
}