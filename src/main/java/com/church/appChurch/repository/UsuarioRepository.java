package com.church.appChurch.repository;

import com.church.appChurch.model.Usuario;
import org.springframework.data.repository.ListCrudRepository;

public interface UsuarioRepository extends ListCrudRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
