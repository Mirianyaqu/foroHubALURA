package foro.Hub.APi.domain.usuario;


import foro.Hub.APi.Infra.Errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;


    public RegistroUsuarioDTO registrarUsuario(RegistroUsuarioDTO registroUsuarioDTO) {

        if (usuarioRepository.findByEmail(registroUsuarioDTO.email()).isPresent()) {
            throw new ValidacionDeIntegridad("Este correo electrónico ya está registrado.");
        }


        if (usuarioRepository.findByUsername(registroUsuarioDTO.username()).isPresent()) {
            throw new ValidacionDeIntegridad("Este nombre de usuario ya está en uso.");
        }


        var usuario = new Usuario(
                registroUsuarioDTO.id(),
                registroUsuarioDTO.nombre(),
                registroUsuarioDTO.email(),
                registroUsuarioDTO.username(),
                passwordEncoder.encode(registroUsuarioDTO.clave()),
                true
        );
        usuarioRepository.save(usuario);


        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getPassword()
        );
    }


    public RegistroUsuarioDTO actualizacionUsuario(Long id,ActualizacionUsuarioDTO  actualizacionUsuarioDTO) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));

        // Actualizar los campos si se proporcionan nuevos valores
        if (actualizacionUsuarioDTO.nombre() != null) {
            usuario.setName(actualizacionUsuarioDTO.nombre());
        }
        if (actualizacionUsuarioDTO.email() != null) {
            usuario.setEmail(actualizacionUsuarioDTO.email());
        }
        if (actualizacionUsuarioDTO.username() != null) {
            usuario.setUsername(actualizacionUsuarioDTO.username());
        }
        if (actualizacionUsuarioDTO.clave() != null && !actualizacionUsuarioDTO.clave().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(actualizacionUsuarioDTO.clave()));
        }


//        if (actualizacionUsuarioDTO.activo() != null) {
//            usuario.setActivo(actualizacionUsuarioDTO.activo());
//        }

        usuarioRepository.save(usuario);


        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getPassword()
        );
    }


    public RegistroUsuarioDTO desactivarUsuario(Long id) {
        var usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ValidacionDeIntegridad("El usuario con el ID proporcionado no existe."));


        usuario.setActivo(false);
        usuarioRepository.save(usuario);


        return new RegistroUsuarioDTO(
                usuario.getId(),
                usuario.getName(),
                usuario.getEmail(),
                usuario.getUsername(),
                usuario.getPassword()
        );
    }
}
