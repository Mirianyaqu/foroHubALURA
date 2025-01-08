package foro.Hub.APi.domain.topico;


import foro.Hub.APi.domain.usuario.Usuario;

import java.time.LocalDateTime;

public record RespuestaTopicoDTO(
        Long id,
        String titulo,
        String mensaje,
        Status status,
        Usuario autorId,
        String nombreCurso,
        LocalDateTime fecha) {
    public RespuestaTopicoDTO(Topico topico) {
        this(
                topico.getId(),                // Obtiene el ID del tópico
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus(),
                topico.getAutor(),             // Cambié esto para obtener el ID del autor.
                topico.getNombreCurso(),
                topico.getFecha()
        );
    }

}