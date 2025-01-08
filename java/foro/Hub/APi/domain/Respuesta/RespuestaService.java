package foro.Hub.APi.domain.Respuesta;


import foro.Hub.APi.Infra.Errores.ValidacionDeIntegridad;
import foro.Hub.APi.domain.topico.TopicoRepository;
import foro.Hub.APi.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RespuestaRepository repository;

    public RespuestaCreadaDTO respuestaCreadaDTO(RespuestaDTO respuestaDTO) {
        if (!usuarioRepository.findById(respuestaDTO.autorId()).isPresent()){
            throw new ValidacionDeIntegridad("Este ID de usuario no está registrado en la base de datos. ");
        }
        if (!topicoRepository.findById(respuestaDTO.topicoId()).isPresent()){
            throw new ValidacionDeIntegridad("Este id de tópico no está registrado en la base de datos. ");
        }
        var usuario = usuarioRepository.findById(respuestaDTO.autorId()).get();
        var topico =topicoRepository.findById(respuestaDTO.topicoId()).get();

        var revisar= new Respuesta(null,respuestaDTO.solucion(),usuario,topico,respuestaDTO.fechaCreacion(),respuestaDTO.activo());
        repository.save(revisar);
        return new RespuestaCreadaDTO(revisar);
    }

}