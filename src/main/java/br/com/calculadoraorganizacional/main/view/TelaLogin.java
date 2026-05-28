package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

import javax.swing.*;

public class TelaLogin extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;

    public TelaLogin() {

        setTitle("Calculadora Organizacional - Login");

        setSize(400, 300);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        JLabel labelLogin = new JLabel("Login:");
        labelLogin.setBounds(50, 50, 100, 30);
        add(labelLogin);

        campoLogin = new JTextField();
        campoLogin.setBounds(150, 50, 180, 30);
        add(campoLogin);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(50, 100, 100, 30);
        add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(150, 100, 180, 30);
        add(campoSenha);

        botaoEntrar = new JButton("Entrar");
        botaoEntrar.setBounds(130, 170, 120, 40);
        add(botaoEntrar);

        botaoEntrar.addActionListener(e -> realizarLogin());

        setVisible(true);
    }

    private void realizarLogin() {

        String login = campoLogin.getText();

        String senha = new String(campoSenha.getPassword());

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.realizarLogin(login, senha);

        if (usuario != null) {

            new TelaCalculadora(usuario.getNome());

            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Login ou senha incorretos!");
        }
    }
}