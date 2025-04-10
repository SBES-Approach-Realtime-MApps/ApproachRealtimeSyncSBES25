package br.unesp.rc.CondominiumModel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.unesp.rc.CondominiumModel.model.Resident;

@Repository("condominiumResidentRepository")
public interface ResidentRepository extends JpaRepository<Resident, String> {
    
}
