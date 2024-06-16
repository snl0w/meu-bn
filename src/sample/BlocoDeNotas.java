package sample;

public class BlocoDeNotas {
    private int codBloco;
    private String Titulo;

    public BlocoDeNotas() {
    }

    public BlocoDeNotas(String titulo) {
        this.Titulo = titulo;
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
