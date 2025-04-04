package br.unesp.rc.ResidentModel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.unesp.rc.ResidentModel.model.Resident;
import br.unesp.rc.ResidentModel.repository.ResidentRepository;



@Service
public class ResidentService {

    @Autowired
    private ResidentRepository residentRepository;

    public Resident save(Resident resident) {
        Resident newResident = residentRepository.save(resident);
        return newResident;
    }

    public List<Resident> findAll() {
        List<Resident> residents = residentRepository.findAll();
        return residents;
    }

    public Resident findById(String id) {
        Optional<Resident> optionalResident = residentRepository.findById(id);
        if (optionalResident.isEmpty()) {
            throw new RuntimeException("Resident not found");
        }
        return optionalResident.get();
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

        if (newResident.getResidentType() != null) {
            oldResident.setResidentType(newResident.getResidentType());
        }

        if (newResident.getBirthDate() != null) {
            oldResident.setBirthDate(newResident.getBirthDate());
        }

        if (newResident.getContact() != null) {
            oldResident.setContact(newResident.getContact());
        }

        if (newResident.getAddress() != null) {
            oldResident.setAddress(newResident.getAddress());
        }
    }
    
}
