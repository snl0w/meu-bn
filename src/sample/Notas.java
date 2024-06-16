package sample;

public class Notas {
    private int codNota;
    private int codBloco;
    private String Titulo;

    public Notas() {
    }

    public Notas(String titulo) {
        this.Titulo = titulo;
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
        return Titulo;
    }

    public void setTitulo(String titulo) {
        this.Titulo = titulo;
    }
}
