package br.unesp.rc.MSCondominium.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.MSCondominium.model.Condominium;
import br.unesp.rc.MSCondominium.repository.CondominiumRepository;

@Service
public class CondominiumService {
    
    @Autowired
    CondominiumRepository condominiumRepository;

    public List<Condominium> findAll() {
        List<Condominium> condominiums = condominiumRepository.findAll();
        return condominiums;
    }
    
    public Condominium findById(Long id) {
        Optional<Condominium> condominium = condominiumRepository.findById(id);
        if(condominium.isEmpty()){
            throw new RuntimeException("Condominium not found");
        } 
        return condominium.get();
    }

    public Condominium save(Condominium condominium) {
        Condominium newCondominium = condominiumRepository.save(condominium); 
        return newCondominium;
    }

    public void delete(long id) {
        condominiumRepository.deleteById(id);
    }


    public Condominium update(Condominium condominium) {
        Condominium oldCondominium = findById(condominium.getId());
        updateCondominium(oldCondominium, condominium);
        Condominium updatedCondominium = condominiumRepository.save(oldCondominium);
        return updatedCondominium;
    }

    public void updateCondominium(Condominium oldCondominium, Condominium newCondominium){
        if (newCondominium.getName() != null) {
            oldCondominium.setName(newCondominium.getName());
        }

        if (newCondominium.getCondominiumType() != null) {
            oldCondominium.setCondominiumType(newCondominium.getCondominiumType());
        }
    }
}
