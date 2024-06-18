package sample;

public class Notas {
    private int codNota;
    private int codBloco;
    private String conteudo;
    private String titulo;

    public Notas() {
    }

    public Notas(int codNota, int codBloco, String titulo, String conteudo) {
        this.codNota = codNota;
        this.codBloco = codBloco;
        this.titulo = titulo;
        this.conteudo = conteudo;
    }

    public int getCodNota() {
        return codNota;
    }

    public void setCodNota(int codNota) {
        this.codNota = codNota;
    }

    public int getCodBloco() {
        return codBloco;
    }

    public void setCodBloco(int codBloco) {
        this.codBloco = codBloco;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}
