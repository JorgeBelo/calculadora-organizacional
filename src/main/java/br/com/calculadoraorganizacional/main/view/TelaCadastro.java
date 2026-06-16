package br.com.calculadoraorganizacional.main.view;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

import javax.swing.*;
import java.awt.*;

public class TelaCadastro extends JFrame {

    private JTextField campoNome;
    private JTextField campoLogin;
    private JPasswordField campoSenha;

    private RoundedButton botaoCadastrar;
    private RoundedButton botaoVoltar;

    public TelaCadastro() {

        setTitle("ImobiCalc Pro - Cadastro");

        setSize(500, 550);

        setLocationRelativeTo(null);

        setResizable(false);

        setLayout(null);

        JPanel painelPrincipal = new JPanel();

        painelPrincipal.setLayout(null);

        painelPrincipal.setBackground(
                new Color(15, 23, 42)
        );

        painelPrincipal.setBounds(0, 0, 500, 550);

        add(painelPrincipal);

        // TÍTULO

        JLabel titulo = new JLabel(
                "Criar Conta",
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
                "Cadastro de Usuário",
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

        // CARD

        JPanel card = new JPanel();

        card.setLayout(null);

        card.setBackground(
                new Color(30, 41, 59)
        );

        card.setBounds(
                50,
                120,
                380,
                320
        );

        painelPrincipal.add(card);

        // NOME

        JLabel lblNome = new JLabel("Nome");

        lblNome.setForeground(Color.WHITE);

        lblNome.setBounds(
                40,
                20,
                100,
                25
        );

        card.add(lblNome);

        campoNome = new JTextField();

        campoNome.setBounds(
                40,
                45,
                300,
                35
        );

        card.add(campoNome);

        // LOGIN

        JLabel lblLogin = new JLabel("Login");

        lblLogin.setForeground(Color.WHITE);

        lblLogin.setBounds(
                40,
                95,
                100,
                25
        );

        card.add(lblLogin);

        campoLogin = new JTextField();

        campoLogin.setBounds(
                40,
                120,
                300,
                35
        );

        card.add(campoLogin);

        // SENHA

        JLabel lblSenha = new JLabel("Senha");

        lblSenha.setForeground(Color.WHITE);

        lblSenha.setBounds(
                40,
                170,
                100,
                25
        );

        card.add(lblSenha);

        campoSenha = new JPasswordField();

        campoSenha.setBounds(
                40,
                195,
                300,
                35
        );

        card.add(campoSenha);

        // BOTÃO CADASTRAR

        botaoCadastrar =
                new RoundedButton("Cadastrar");

        botaoCadastrar.setBounds(
                40,
                255,
                140,
                45
        );

        botaoCadastrar.setBackground(
                new Color(16, 185, 129)
        );

        botaoCadastrar.setForeground(
                Color.WHITE
        );

        botaoCadastrar.addActionListener(
                e -> cadastrarUsuario()
        );

        card.add(botaoCadastrar);

        // BOTÃO VOLTAR

        botaoVoltar =
                new RoundedButton("Voltar");

        botaoVoltar.setBounds(
                200,
                255,
                140,
                45
        );

        botaoVoltar.setBackground(
                new Color(59, 130, 246)
        );

        botaoVoltar.setForeground(
                Color.WHITE
        );

        botaoVoltar.addActionListener(
                e -> dispose()
        );

        card.add(botaoVoltar);

        setVisible(true);
    }

    private void cadastrarUsuario() {

        String nome =
                campoNome.getText();

        String login =
                campoLogin.getText();

        String senha =
                new String(
                        campoSenha.getPassword()
                );

        if (
                nome.isEmpty() ||
                        login.isEmpty() ||
                        senha.isEmpty()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos!"
            );

            return;
        }

        Usuario usuario = new Usuario();

        usuario.setNome(nome);

        usuario.setLogin(login);

        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO =
                new UsuarioDAO();

        usuarioDAO.cadastrarUsuario(
                usuario
        );

        JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso!"
        );

        dispose();
    }
}