# ImobiCalc Pro

Sistema de Simulação Imobiliária — Projeto acadêmico SENAC (Programador de Sistemas)

## Funcionalidades

### ✅ Concluído (5 novas telas)

- **Comparador Price x SAC** (1100x740): Compara tabelas Price e SAC lado a lado com resumo de economia
- **Cronograma de Parcelas** (900x700): Cronograma mês a mês com exportação .txt via JFileChooser
- **Capacidade de Crédito** (680x560): 4 cards em grid com capacidade baseada na renda
- **Custos Totais de Compra** (730x660): ITBI, escritura, registro, avaliação bancária, outros — cards dinâmicos
- **Relatório de Clientes** (860x680): Busca por nome, tabela com dados cadastrais, exportação .txt formatada

### Telas originais mantidas
- Login / Cadastro, Simular Financiamento, Gerar Proposta, Histórico, Calculadora

### Dashboard atualizado
- 9 botões no total (4 originais + 5 novos) com tema escuro/claro

## Estrutura do projeto

```
src/main/java/br/com/calculadoraorganizacional/main/
├── Main.java
├── connection/
│   └── ConexaoMySQL.java
├── dao/
│   ├── ClienteDAO.java
│   ├── HistoricoDAO.java
│   ├── SimulacaoDAO.java
│   └── UsuarioDAO.java
├── model/
│   ├── Cliente.java
│   ├── Historico.java
│   └── Usuario.java
└── view/
    ├── RoundedButton.java
    ├── TelaCadastro.java
    ├── TelaCalculadora.java
    ├── TelaCapacidadeCredito.java
    ├── TelaComparadorPriceSAC.java
    ├── TelaCronogramoParcelas.java
    ├── TelaCustosTotaisCompra.java
    ├── TelaDashboard.java
    ├── TelaFinanciamento.java
    ├── TelaHistorico.java
    ├── TelaLogin.java
    ├── TelaPropostas.java
    └── TelaRelatorioCliente.java
```

## Para compilar

```bash
JAVAC="/c/Program Files/JetBrains/IntelliJ IDEA 2025.3.3/jbr/bin/javac"
CP="/c/Users/molos/.m2/repository/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar"
mkdir -p target/classes

# Camada base
$JAVAC -d target/classes -cp "$CP" \
  src/main/java/br/com/calculadoraorganizacional/main/model/*.java \
  src/main/java/br/com/calculadoraorganizacional/main/connection/ConexaoMySQL.java \
  src/main/java/br/com/calculadoraorganizacional/main/dao/*.java \
  src/main/java/br/com/calculadoraorganizacional/main/view/RoundedButton.java

# Views (precisa de target/classes no classpath)
$JAVAC -d target/classes -cp "target/classes;$CP" \
  src/main/java/br/com/calculadoraorganizacional/main/view/*.java \
  src/main/java/br/com/calculadoraorganizacional/main/Main.java
```

## Para executar

```bash
$JAVAC -cp "target/classes;$CP" br.com.calculadoraorganizacional.main.Main
```

## Requisitos

- Java 11+ (compilado com JBR 21 do IntelliJ)
- MySQL com banco `imobicalc` e tabelas: usuarios, historico, simulacoes, clientes
- MySQL Connector/J 9.3.0






//Ajustes: 
Casa decimal
Interface 
registrar cliente
aparecer historico 
mudar tema só na tela principal 
histórico no ultimo lugar da tabela 

