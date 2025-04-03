package br.unesp.rc.CondominiumModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.CondominiumModel.model.Condominium;


public interface CondominiumRepository extends JpaRepository<Condominium, Long> {
    
}
