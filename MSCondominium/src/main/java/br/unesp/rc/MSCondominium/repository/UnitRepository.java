package br.unesp.rc.MSCondominium.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.MSCondominium.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long>{
    
}
