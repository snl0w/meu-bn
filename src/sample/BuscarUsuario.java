package sample;

public class BuscarUsuario {

    private static int usuarioLogadoId;

    // Este método deve ser chamado durante o processo de login para definir o ID do usuário logado
    public static void setUsuarioLogadoId(int id) {
        usuarioLogadoId = id;
    }

    public static int getUsuarioLogadoId() {
        return usuarioLogadoId;
    }

    // Outros métodos para buscar usuários
}
