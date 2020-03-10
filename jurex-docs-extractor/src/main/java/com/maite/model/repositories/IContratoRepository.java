package com.maite.model.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maite.model.entities.Contrato;

@Repository
public interface IContratoRepository  extends JpaRepository<Contrato, Long> {


}
