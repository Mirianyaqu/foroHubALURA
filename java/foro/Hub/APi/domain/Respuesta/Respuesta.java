package foro.Hub.APi.domain.Respuesta;


import foro.Hub.APi.domain.topico.Topico;
import foro.Hub.APi.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name = "Respuesta")
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "solucion", nullable = false)
    private String solucion;

    @ManyToOne
    @JoinColumn(name = "autor", referencedColumnName = "id", nullable = false)
    private Usuario autor;

    @ManyToOne
    @JoinColumn(name = "topico", referencedColumnName = "id", nullable = false)
    private Topico topico;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    
    public Respuesta(Long id, String solucion, Usuario autor, Topico topico, LocalDateTime fechaCreacion, boolean activo) {
        this.id = id;
        this.solucion = solucion;
        this.autor = autor;
        this.topico = topico;
        this.fechaCreacion = fechaCreacion != null ? fechaCreacion : LocalDateTime.now(); // Si no se proporciona fecha, se usa la actual
        this.activo = true; // La respuesta se activa por defecto
    }

    
    public void respuestaActualizada(RespuestaActualizadaDTO respuestaActualizadaDTO) {
        if (respuestaActualizadaDTO.solucion() != null) {
            this.solucion = respuestaActualizadaDTO.solucion();
        }
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public void deactivateResponse() {
        this.activo = false;
    }



}
