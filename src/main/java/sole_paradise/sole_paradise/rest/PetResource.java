package sole_paradise.sole_paradise.rest;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sole_paradise.sole_paradise.model.PetDTO;
import sole_paradise.sole_paradise.service.PetService;
import sole_paradise.sole_paradise.util.ReferencedException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/pets", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetResource {

    private final PetService petService;

    public PetResource(final PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    public ResponseEntity<List<PetDTO>> getAllPets() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{petId}")
    public ResponseEntity<PetDTO> getPet(@PathVariable(name = "petId") final Integer petId) {
        return ResponseEntity.ok(petService.get(petId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPet(@RequestBody @Valid final PetDTO petDTO) {
        final Integer createdPetId = petService.create(petDTO);
        return new ResponseEntity<>(createdPetId, HttpStatus.CREATED);
    }

    @PutMapping("/{petId}")
    public ResponseEntity<Integer> updatePet(@PathVariable(name = "petId") final Integer petId,
            @RequestBody @Valid final PetDTO petDTO) {
        petService.update(petId, petDTO);
        return ResponseEntity.ok(petId);
    }

    @DeleteMapping("/{petId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePet(@PathVariable(name = "petId") final Integer petId) {
        final ReferencedWarning referencedWarning = petService.getReferencedWarning(petId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        petService.delete(petId);
        return ResponseEntity.noContent().build();
    }

}
