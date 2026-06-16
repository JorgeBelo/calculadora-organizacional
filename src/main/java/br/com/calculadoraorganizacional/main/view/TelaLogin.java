package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {

    private JTextField campoLogin;
    private JPasswordField campoSenha;

    private RoundedButton botaoEntrar;
    private RoundedButton botaoCadastrar;

    public TelaLogin() {

        setTitle("ImobiCalc Pro");

        setSize(500, 500);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        JPanel painelPrincipal = new JPanel();

        painelPrincipal.setLayout(null);

        painelPrincipal.setBackground(
                new Color(15, 23, 42)
        );

        add(painelPrincipal);

        // TÍTULO

        JLabel titulo = new JLabel(
                "ImobiCalc Pro",
                SwingConstants.CENTER
        );

        titulo.setFont(
                new Font("Arial", Font.BOLD, 28)
        );

        titulo.setForeground(Color.WHITE);

        titulo.setBounds(
                50,
                30,
                380,
                40
        );

        painelPrincipal.add(titulo);

        // SUBTÍTULO

        JLabel subtitulo = new JLabel(
                "Sistema de Simulação Imobiliária",
                SwingConstants.CENTER
        );

        subtitulo.setForeground(
                new Color(203, 213, 225)
        );

        subtitulo.setBounds(
                50,
                70,
                380,
                25
        );

        painelPrincipal.add(subtitulo);

        // CARD CENTRAL

        JPanel card = new JPanel();

        card.setLayout(null);

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBounds(
                50,
                120,
                380,
                280
        );

        painelPrincipal.add(card);

        // LOGIN

        JLabel lblLogin = new JLabel("Login");

        lblLogin.setForeground(Color.WHITE);

        lblLogin.setBounds(
                40,
                30,
                100,
                25
        );

        card.add(lblLogin);

        campoLogin = new JTextField();

        campoLogin.setBounds(
                40,
                55,
                300,
                35
        );

        card.add(campoLogin);

        // SENHA

        JLabel lblSenha = new JLabel("Senha");

        lblSenha.setForeground(Color.WHITE);

        lblSenha.setBounds(
                40,
                105,
                100,
                25
        );

        card.add(lblSenha);

        campoSenha = new JPasswordField();

        campoSenha.setBounds(
                40,
                130,
                300,
                35
        );

        card.add(campoSenha);

        // BOTÃO ENTRAR

        botaoEntrar =
                new RoundedButton("Entrar");

        botaoEntrar.setBounds(
                40,
                205,
                140,
                45
        );

        botaoEntrar.setBackground(
                new Color(59, 130, 246)
        );

        botaoEntrar.setForeground(Color.WHITE);

        botaoEntrar.addActionListener(
                e -> realizarLogin()
        );

        card.add(botaoEntrar);

        // BOTÃO CADASTRAR

        botaoCadastrar =
                new RoundedButton("Cadastrar");

        botaoCadastrar.setBounds(
                200,
                205,
                140,
                45
        );

        botaoCadastrar.setBackground(
                new Color(16, 185, 129)
        );

        botaoCadastrar.setForeground(Color.WHITE);

        botaoCadastrar.addActionListener(
                e -> new TelaCadastro()
        );

        card.add(botaoCadastrar);

        setVisible(true);
    }

    private void realizarLogin() {

        String login =
                campoLogin.getText();

        String senha =
                new String(
                        campoSenha.getPassword()
                );

        UsuarioDAO usuarioDAO =
                new UsuarioDAO();

        Usuario usuario =
                usuarioDAO.realizarLogin(
                        login,
                        senha
                );

        if (usuario != null) {

            dispose();

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