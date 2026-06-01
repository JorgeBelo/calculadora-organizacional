package br.com.calculadoraorganizacional.view;

import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Usuario;

import javax.swing.*;

public class TelaCadastro extends JFrame {

    private JTextField campoNome;

    private JTextField campoLogin;

    private JPasswordField campoSenha;

    private JButton botaoCadastrar;

    public TelaCadastro() {

        setTitle("Cadastro de Usuário");

        setSize(400, 350);

        setLocationRelativeTo(null);

        setLayout(null);

        // NOME

        JLabel labelNome = new JLabel("Nome:");

        labelNome.setBounds(50, 40, 100, 30);

        add(labelNome);

        campoNome = new JTextField();

        campoNome.setBounds(150, 40, 180, 30);

        add(campoNome);

        // LOGIN

        JLabel labelLogin = new JLabel("Login:");

        labelLogin.setBounds(50, 90, 100, 30);

        add(labelLogin);

        campoLogin = new JTextField();

        campoLogin.setBounds(150, 90, 180, 30);

        add(campoLogin);

        // SENHA

        JLabel labelSenha = new JLabel("Senha:");

        labelSenha.setBounds(50, 140, 100, 30);

        add(labelSenha);

        campoSenha = new JPasswordField();

        campoSenha.setBounds(150, 140, 180, 30);

        add(campoSenha);

        // BOTÃO CADASTRAR

        botaoCadastrar = new JButton("Cadastrar");

        botaoCadastrar.setBounds(120, 220, 140, 40);

        add(botaoCadastrar);

        botaoCadastrar.addActionListener(e -> cadastrarUsuario());

        setVisible(true);
    }

    private void cadastrarUsuario() {

        String nome = campoNome.getText();

        String login = campoLogin.getText();

        String senha = new String(campoSenha.getPassword());

        // VALIDAR CAMPOS

        if (nome.isEmpty() || login.isEmpty() || senha.isEmpty()) {

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

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        usuarioDAO.cadastrarUsuario(usuario);

        JOptionPane.showMessageDialog(
                this,
                "Usuário cadastrado com sucesso!"
        );

        dispose();
    }
}