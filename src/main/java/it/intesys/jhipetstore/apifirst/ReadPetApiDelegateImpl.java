package it.intesys.jhipetstore.apifirst;

import it.intesys.jhipetstore.domain.Pet;
import it.intesys.jhipetstore.repository.PetRepository;
import it.intesys.jhipetstore.web.api.ReadPetsApiDelegate;
import it.intesys.jhipetstore.web.api.model.PetApiDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReadPetApiDelegateImpl implements ReadPetsApiDelegate {

    private final PetRepository petRepository;

    public ReadPetApiDelegateImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public ResponseEntity<List<PetApiDTO>> listPets(Integer limit) {
        Page<Pet> petList = petRepository.findAll(PageRequest.of(0, limit));

        List<PetApiDTO> petApiDTOList = petList.stream()
            .map(p -> new PetApiDTO()
                .id(p.getId())
                .name(p.getName())
                .tag(p.getTags())
            ).collect(Collectors.toList());

        return ResponseEntity.ok(petApiDTOList);
    }

    @Override
    public ResponseEntity<PetApiDTO> showPetById(String petId) {
        Pet pet = petRepository.findById(Long.parseLong(petId))
            .orElseThrow(() -> Problem.valueOf(Status.NOT_FOUND, "Cannot found pet with id " + petId));

        return ResponseEntity.ok(
            new PetApiDTO().id(pet.getId())
                .name(pet.getName())
                .tag(pet.getTags()));
    }
}
