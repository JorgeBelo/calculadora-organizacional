CREATE DATABASE calculadora_organizacional;

USE calculadora_organizacional;

CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    login VARCHAR(50) UNIQUE NOT NULL,
    senha VARCHAR(100) NOT NULL
);


CREATE TABLE historico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    expressao VARCHAR(100),
    resultado VARCHAR(50),
    data_operacao DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (usuario_id)
    REFERENCES usuarios(id)
);

SELECT * FROM usuarios;

SELECT * FROM historico;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    nome VARCHAR(100),
    telefone VARCHAR(20),
    email VARCHAR(100),
    observacoes TEXT
);

CREATE TABLE simulacoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT,
    cliente_id INT,

    tipo VARCHAR(50),

    valor_imovel DECIMAL(12,2),

    entrada DECIMAL(12,2),

    prazo INT,

    juros DECIMAL(5,2),

    resultado TEXT,

    data_simulacao DATETIME DEFAULT CURRENT_TIMESTAMP
);