package br.unesp.rc.MSResident.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.unesp.rc.MSResident.model.Resident;

public interface ResidentRepository extends MongoRepository<Resident, String> {
    
}
