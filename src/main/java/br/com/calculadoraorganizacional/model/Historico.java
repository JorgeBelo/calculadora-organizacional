package br.com.calculadoraorganizacional.model;

public class Historico {

    private int id;
    private int usuarioId;
    private String tipo;
    private String expressao;
    private String resultado;
    private String dataOperacao;

    public Historico() {}

    public Historico(int usuarioId, String tipo, String expressao, String resultado) {
        this.usuarioId = usuarioId;
        this.tipo = tipo;
        this.expressao = expressao;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getExpressao() {
        return expressao;
    }

    public void setExpressao(String expressao) {
        this.expressao = expressao;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getDataOperacao() {
        return dataOperacao;
    }

    public void setDataOperacao(String dataOperacao) {
        this.dataOperacao = dataOperacao;
    }
}