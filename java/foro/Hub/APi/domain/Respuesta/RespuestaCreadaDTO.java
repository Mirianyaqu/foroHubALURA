package foro.Hub.APi.domain.Respuesta;

import java.time.LocalDateTime;

public record RespuestaCreadaDTO(
        Long id,
        String solucion,
        Long autorId,
        Long topicoId,
        LocalDateTime fechaCreacion
) {
    public RespuestaCreadaDTO(Respuesta revision) {
        this(
                revision.getId(),
                revision.getSolucion(),
                revision.getAutor().getId(),
                revision.getTopico().getId(),
                revision.getFechaCreacion()
        );
    }
}
