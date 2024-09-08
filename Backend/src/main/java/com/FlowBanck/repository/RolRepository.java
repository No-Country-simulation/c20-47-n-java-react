package com.FlowBanck.repository;

import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long> {

    Optional<Rol> findByRol(EnumRol rol);

   /* @Query("select u from Rol u where u.rol = ?1")
    Optional<Rol> findByName(EnumRol name);*/
}
