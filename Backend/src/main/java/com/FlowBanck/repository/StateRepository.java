package com.FlowBanck.repository;

import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.EnumState;
import com.FlowBanck.entity.Rol;
import com.FlowBanck.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StateRepository extends JpaRepository<State,Long> {

    Optional<State> findByState(EnumState state);
}
