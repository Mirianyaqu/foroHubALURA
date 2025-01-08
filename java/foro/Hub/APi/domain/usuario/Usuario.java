package foro.Hub.APi.domain.usuario;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


        @Entity(name = "Usuario")
        @Table(name = "usuarios")
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public class Usuario implements UserDetails {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(name = "nombre", nullable = false)
            private String name;

            @Column(name = "email", unique = true, nullable = false)
            private String email;

            @Column(name = "username", unique = true, nullable = false)
            private String username;

            @Column(name = "clave", nullable = false)
            private String password;

            @Column(nullable = false)
            private boolean activo;

            public Usuario(RegistroUsuarioDTO registroUsuarioDTO, BCryptPasswordEncoder passwordEncoder) {
                this.name = registroUsuarioDTO.nombre();
                this.email = registroUsuarioDTO.email();
                this.username = registroUsuarioDTO.username();
                this.password = passwordEncoder.encode(registroUsuarioDTO.clave());
                this.activo= true;
            }


            public void actualizacionUsuario(ActualizacionUsuarioDTO actualizacionUsuarioDTO) {
                if (actualizacionUsuarioDTO.nombre() != null) {
                    this.name = actualizacionUsuarioDTO.nombre();
                }
                if (actualizacionUsuarioDTO.email() != null) {
                    this.email = actualizacionUsuarioDTO.email();
                }
                if (actualizacionUsuarioDTO.username() != null) {
                    this.username = actualizacionUsuarioDTO.username();
                }
                if (actualizacionUsuarioDTO.clave() != null) {
                    this.password = actualizacionUsuarioDTO.clave(); // Si se pasa una nueva contraseña, actualizamos
                }
            }


            public void deactivateUser() {
                this.activo = false;
            }

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new SimpleGrantedAuthority("ROLE_USER"));
            }

            public Long getId() {
                return id;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return username;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return activo;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public void setActivo(boolean activo) {
                this.activo = activo;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Usuario usuario = (Usuario) o;
                return id != null && id.equals(usuario.id);
            }

            @Override
            public int hashCode() {
                return Objects.hash(id);
            }


        }

