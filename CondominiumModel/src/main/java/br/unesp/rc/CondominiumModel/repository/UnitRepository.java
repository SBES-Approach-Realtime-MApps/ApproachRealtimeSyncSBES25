package br.unesp.rc.CondominiumModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.unesp.rc.CondominiumModel.model.Unit;


public interface UnitRepository extends JpaRepository<Unit, Long>{
    
}
