package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

import javax.swing.*;

public class TelaLogin extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;
    private JButton botaoEntrar;
    private JButton botaoCadastrar;

    public TelaLogin() {

        setTitle("Calculadora Organizacional - Login");

        setSize(400, 350);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(null);

        // LOGIN

        JLabel labelLogin = new JLabel("Login:");

        labelLogin.setBounds(50, 50, 100, 30);

        add(labelLogin);

        campoLogin = new JTextField();

        campoLogin.setBounds(150, 50, 180, 30);

        add(campoLogin);

        // SENHA

        JLabel labelSenha = new JLabel("Senha:");

        labelSenha.setBounds(50, 100, 100, 30);

        add(labelSenha);

        campoSenha = new JPasswordField();

        campoSenha.setBounds(150, 100, 180, 30);

        add(campoSenha);

        // BOTÃO ENTRAR

        botaoEntrar = new JButton("Entrar");

        botaoEntrar.setBounds(130, 170, 120, 40);

        add(botaoEntrar);

        botaoEntrar.addActionListener(e -> realizarLogin());

        // BOTÃO CADASTRAR

        botaoCadastrar = new JButton("Cadastrar");

        botaoCadastrar.setBounds(130, 230, 120, 40);

        add(botaoCadastrar);

        botaoCadastrar.addActionListener(e -> new TelaCadastro());

        setVisible(true);
    }

    private void realizarLogin() {

        String login = campoLogin.getText();

        String senha = new String(campoSenha.getPassword());

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuario = usuarioDAO.realizarLogin(login, senha);

        if (usuario != null) {

            new TelaDashboard(
                    usuario.getNome(),
                    usuario.getId()
            );
        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Login ou senha incorretos!"
            );
        }
    }
}