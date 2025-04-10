package br.unesp.rc.ResidentModel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.unesp.rc.ResidentModel.model.Resident;


@Repository("residentResidentRepository")
public interface ResidentRepository extends MongoRepository<Resident, String> {
    
}
