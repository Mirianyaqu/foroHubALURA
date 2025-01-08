package foro.Hub.APi.controller;

import foro.Hub.APi.domain.usuario.RegistroUsuarioDTO;
import foro.Hub.APi.domain.usuario.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class InactivacionUsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @DeleteMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id) {
        try {
            RegistroUsuarioDTO respuesta = usuarioService.desactivarUsuario(id);
            return ResponseEntity.ok(respuesta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
