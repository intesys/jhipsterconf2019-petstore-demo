package it.intesys.jhipetstore.apifirst;

import it.intesys.jhipetstore.domain.Pet;
import it.intesys.jhipetstore.repository.PetRepository;
import it.intesys.jhipetstore.web.api.WritePetsApiDelegate;
import it.intesys.jhipetstore.web.api.model.PetApiDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WritePetApiDelegateImpl implements WritePetsApiDelegate {

    private final PetRepository petRepository;

    public WritePetApiDelegateImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public ResponseEntity<Void> createPets(PetApiDTO petApiDTO) {
        Pet newPet = new Pet()
            .name(petApiDTO.getName())
            .tags(petApiDTO.getTag());
        petRepository.save(newPet);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
