package br.unesp.rc.ResidentModel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.unesp.rc.ResidentModel.model.Resident;


public interface ResidentRepository extends MongoRepository<Resident, String> {
    
}
