package br.unesp.rc.CondominiumModel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.CondominiumModel.model.Resident;
import br.unesp.rc.CondominiumModel.repository.ResidentRepository;

@Service
public class CondominiumResidentService {
    
    @Autowired
    ResidentRepository residentRepository;

    public List<Resident> findAll() {
        List<Resident> residents = residentRepository.findAll();
        return residents;
    }
    
    public Resident findById(String id) {
        Optional<Resident> resident = residentRepository.findById(id);
        if(resident.isEmpty()){
            throw new RuntimeException("Resident not found");
        } 
        return resident.get();
    }

    public Resident save(Resident resident) {
        Resident newResident = residentRepository.save(resident);
        return newResident;
    }

    public Resident update(Resident resident) {
        Resident oldResident = findById(resident.getId());
        updateResident(oldResident, resident);
        Resident updatedResident = residentRepository.save(oldResident);
        return updatedResident;
    }

    public void delete(String id) {
        residentRepository.deleteById(id);
    }

    public void updateResident(Resident oldResident, Resident newResident) {

        if (newResident.getName() != null) {
            oldResident.setName(newResident.getName());
        }

        if (newResident.getBirthDate() != null) {
            oldResident.setBirthDate(newResident.getBirthDate());
        }

        if (newResident.getResidentType() != null) {
            oldResident.setResidentType(newResident.getResidentType());
        }
    }
}
