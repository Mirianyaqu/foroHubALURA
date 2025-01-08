package foro.Hub.APi.domain.usuario;

public record  ListarUsuariosDTO(
        Long id,
        String nombre,
        String email
) {

 public ListarUsuariosDTO(Usuario usuario){
 this(usuario.getId(),usuario.getName(),usuario.getEmail());
    }
}
