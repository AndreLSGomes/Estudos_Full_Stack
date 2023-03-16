package com.andre.bibliotecavirtual.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.andre.bibliotecavirtual.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}