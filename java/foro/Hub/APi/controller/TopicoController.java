package foro.Hub.APi.controller;

import foro.Hub.APi.Infra.Errores.ValidacionDeIntegridad;
import foro.Hub.APi.domain.topico.*;
import foro.Hub.APi.domain.usuario.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
//@ResponseBody
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository topicoRepository;

//    @Autowired
//    private UsuarioRepository usuarioRepository;
//    @Autowired
//    private TopicoService topicoService;

    /***********************************
     * REST API POST
     * Registrar nuevo Topico
     * ENDPOINT :
     * http://localhost:8080/topico/registrar
     ************************************/
    @PostMapping("/registrar")
   //@Transactional
    public ResponseEntity<?> registrarTopico(@RequestBody @Valid TopicoDTO topicoDTO) throws ValidacionDeIntegridad {
        // Crear tópico
        var topicoRegistrado = topicoService.topicoCreado(topicoDTO);
        return ResponseEntity.ok(topicoRegistrado);
    }

    /**************************************
     * REST API GET
     * Obtener todos los Topicos
     * ENDPOINT :
     * http://localhost:8080/topico/listar
     ***************************************/
    @GetMapping("/listar")
    public ResponseEntity<Page<ListarTopicosDTO>> listarTopicos(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(topicoRepository.findByActiveTrue((org.springframework.data.domain.Pageable) paged).map(ListarTopicosDTO::new));
    }

    /************************************************
     * REST API PUT
     * Actualizar un topico por id
     * ENDPOINT :
     * http://localhost:8080/topico/actualizar/{id}
     *************************************************/
    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoActualizadoDTO topicoActualizadoDTO) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.actualizarTopico(topicoActualizadoDTO);
        return ResponseEntity.ok(new RespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus().name(),
                //topico.getAutor().getId(),
                topico.getAutor(),
                topico.getNombreCurso(),
                topico.getFecha()));
    }

    /************************************************
     * REST API DELETE
     * Eliminar un topico por id
     * ENDPOINT :
     * http://localhost:8080/topico/eliminar/{id}
     *************************************************/
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.desactivarTopico();
        return ResponseEntity.noContent().build();
    }

    /*******************************************
     * REST API GET
     * Obtener un Topico pasando el id
     * ENDPOINT :
     * http://localhost:8080/topico/{id}
     ********************************************/
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaTopicoDTO> obtenerTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        var topicoId = new RespuestaTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getStatus().name(),
                topico.getAutor(),
                topico.getNombreCurso(),
                topico.getFecha());
        return ResponseEntity.ok(topicoId);
    }
}
