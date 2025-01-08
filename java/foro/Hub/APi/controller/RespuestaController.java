package foro.Hub.APi.controller;


import foro.Hub.APi.Infra.Errores.ValidacionDeIntegridad;
import foro.Hub.APi.domain.Respuesta.*;
import foro.Hub.APi.domain.topico.TopicoRepository;
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
@ResponseBody
@RequestMapping("/respuesta")

public class RespuestaController {

    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespuestaService respuestaService;

    /***********************************
     * REST API POST
     * Registrar nueva Respuesta
     * ENDPOINT :
     * http://localhost:8080/respuesta/registrar
     ************************************/
    @PostMapping("/registrar")
    @Transactional
    public ResponseEntity<?> registrarRespuesta(@RequestBody @Valid RespuestaDTO respuestaDTO) throws ValidacionDeIntegridad {
        // Crear respuesta
        var respuestaRegistrada = respuestaService.respuestaCreadaDTO(respuestaDTO);
        return ResponseEntity.ok(respuestaRegistrada);
    }

    /**************************************
     * REST API GET
     * Obtener todas las Respuestas
     * ENDPOINT :
     * http://localhost:8080/respuesta/listar
     ***************************************/
    @GetMapping("/listar")
    public ResponseEntity<Page<ListarRespuestasDTO>> listarRespuestas(@PageableDefault(size = 10) Pageable paged) {
        return ResponseEntity.ok(respuestaRepository.findByActivoTrue(paged).map(ListarRespuestasDTO::new));
    }

    /************************************************
     * REST API PUT
     * Actualizar una respuesta por id
     * ENDPOINT :
     * http://localhost:8080/respuesta/actualizar/{id}
     *************************************************/
    @PutMapping("/actualizar/{id}")
    @Transactional
    public ResponseEntity<?> actualizarRespuesta(@PathVariable Long id, @RequestBody @Valid RespuestaActualizadaDTO respuestaActualizadaDTO) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.respuestaActualizada(respuestaActualizadaDTO);
        return ResponseEntity.ok(new RespuestaDTO(
                respuesta.getId(),
                respuesta.getSolucion(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo()
        ));
    }

    /************************************************
     * REST API DELETE
     * Eliminar una respuesta por id
     * ENDPOINT :
     * http://localhost:8080/respuesta/eliminar/{id}
     *************************************************/
    @DeleteMapping("/eliminar/{id}")
    @Transactional
    public ResponseEntity<?> eliminarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.deactivateResponse();
        return ResponseEntity.noContent().build();
    }

    /*******************************************
     * REST API GET
     * Obtener una Respuesta pasando el id
     * ENDPOINT :
     * http://localhost:8080/respuesta/{id}
     ********************************************/
    @GetMapping("/{id}")
    public ResponseEntity<RespuestaDTO> obtenerRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var respuestaId = new RespuestaDTO(
                respuesta.getId(),
                respuesta.getSolucion(),
                respuesta.getFechaCreacion(),
                respuesta.getAutor().getId(),
                respuesta.getTopico().getId(),
                respuesta.getActivo()
                        );
        return ResponseEntity.ok(respuestaId);
    }
}
