package foro.Hub.APi.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record ActualizacionUsuarioDTO(
                @NotNull Long id,
                String nombre,
                String email,
                boolean activo,
                String clave,
                String username

        ){

}
