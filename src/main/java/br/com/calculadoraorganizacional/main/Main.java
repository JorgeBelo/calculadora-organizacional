package br.com.calculadoraorganizacional.main;

import br.com.calculadoraorganizacional.dao.HistoricoDAO;
import br.com.calculadoraorganizacional.dao.UsuarioDAO;
import br.com.calculadoraorganizacional.model.Historico;
import br.com.calculadoraorganizacional.model.Usuario;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        Usuario usuarioLogado = usuarioDAO.realizarLogin("jorge", "123");

        if (usuarioLogado != null) {

            System.out.println("Login realizado com sucesso!");
            System.out.println("Bem-vindo: " + usuarioLogado.getNome());

            Scanner scanner = new Scanner(System.in);

            System.out.println("Digite o primeiro número:");
            double numero1 = scanner.nextDouble();

            System.out.println("Digite a operação (+, -, *, /):");
            String operacao = scanner.next();

            System.out.println("Digite o segundo número:");
            double numero2 = scanner.nextDouble();

            double resultado = 0;

            switch (operacao) {

                case "+":
                    resultado = numero1 + numero2;
                    break;

                case "-":
                    resultado = numero1 - numero2;
                    break;

                case "*":
                    resultado = numero1 * numero2;
                    break;

                case "/":
                    resultado = numero1 / numero2;
                    break;

                default:
                    System.out.println("Operação inválida!");
                    return;
            }

            System.out.println("Resultado: " + resultado);

            String expressao = numero1 + " " + operacao + " " + numero2;

            Historico historico = new Historico();

            historico.setUsuarioId(usuarioLogado.getId());
            historico.setExpressao(expressao);
            historico.setResultado(String.valueOf(resultado));

            HistoricoDAO historicoDAO = new HistoricoDAO();

            historicoDAO.salvarHistorico(historico);

        } else {

            System.out.println("Login ou senha incorretos!");
        }
    }
}