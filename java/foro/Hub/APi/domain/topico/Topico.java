package foro.Hub.APi.domain.topico;

import foro.Hub.APi.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Topico")
@Table(name = "topicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "mensaje", nullable = false)
    private String mensaje;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @Column(name = "nombreCurso", nullable = false)
    private String nombreCurso;

    @Column(name = "activo", nullable = false)
    private boolean activo;


    public Topico(String titulo, String mensaje, LocalDateTime fecha, Status status, Usuario autor, String nombreCurso) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fecha = LocalDateTime.now();// asignacion automatica
        this.status = status;
        this.autor = autor;
        this.nombreCurso = nombreCurso;
        this.activo = true;
    }


    public void actualizarTopico(TopicoActualizadoDTO topicoActualizadoDTO) {
        if (topicoActualizadoDTO.titulo() != null) {
            this.titulo = topicoActualizadoDTO.titulo();
        }
        if (topicoActualizadoDTO.mensaje() != null) {
            this.mensaje = topicoActualizadoDTO.mensaje();
        }
        if (topicoActualizadoDTO.status() != null) {
            this.status = topicoActualizadoDTO.status();
        }
        if (topicoActualizadoDTO.nombreCurso() != null) {
            this.nombreCurso = topicoActualizadoDTO.nombreCurso();
        }
    }

    public void desactivarTopico() {
        this.activo = false;
    }
}
