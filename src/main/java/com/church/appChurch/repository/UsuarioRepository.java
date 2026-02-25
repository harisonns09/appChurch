package com.church.appChurch.repository;

import com.church.appChurch.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface UsuarioRepository extends ListCrudRepository<Usuario, Long> {

    Usuario findByUsername(String username);

    List<Usuario> findByIgrejaId(Long igrejaId);

}
