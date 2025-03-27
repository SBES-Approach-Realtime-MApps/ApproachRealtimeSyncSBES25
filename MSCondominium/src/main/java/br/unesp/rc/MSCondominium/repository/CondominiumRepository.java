package br.unesp.rc.MSCondominium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSCondominium.model.Condominium;

public interface CondominiumRepository extends JpaRepository<Condominium, Long> {
    
}
