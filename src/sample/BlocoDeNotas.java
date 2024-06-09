package sample;

public class BlocoDeNotas {
    private int codBloco;
    private String Titulo;

    public BlocoDeNotas(int codBloco, String titulo) {
        this.codBloco = codBloco;
        Titulo = titulo;
    }

    public BlocoDeNotas(String titulo) {
        this.Titulo = Titulo;
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
        Titulo = titulo;
    }
}
