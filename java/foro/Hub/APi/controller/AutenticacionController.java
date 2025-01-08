package foro.Hub.APi.controller;

import foro.Hub.APi.Infra.security.JWTTokenDTO;
import foro.Hub.APi.Infra.security.TokenService;
import foro.Hub.APi.domain.usuario.Usuario;
import foro.Hub.APi.domain.usuario.UsuarioDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticacionController {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private TokenService tokenService;

        @PostMapping
       // @Transactional
        public ResponseEntity autenticarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
            Authentication authToken = new UsernamePasswordAuthenticationToken(usuarioDTO.email(), usuarioDTO.clave());
            var autenticacionUsuario = authenticationManager.authenticate(authToken);
            var token = tokenService.generarToken((Usuario) autenticacionUsuario.getPrincipal());
            return ResponseEntity.ok(new JWTTokenDTO(token));
        }



}