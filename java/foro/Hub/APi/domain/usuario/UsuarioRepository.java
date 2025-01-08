package foro.Hub.APi.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;
@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    Page<Usuario> findByActiveTrue(Pageable pageable);

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Usuario> findByUsername(String username);

}