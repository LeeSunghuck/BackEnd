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
import sole_paradise.sole_paradise.model.PetItemDTO;
import sole_paradise.sole_paradise.service.PetItemService;
import sole_paradise.sole_paradise.util.ReferencedException;
import sole_paradise.sole_paradise.util.ReferencedWarning;


@RestController
@RequestMapping(value = "/api/petItems", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetItemResource {

    private final PetItemService petItemService;

    public PetItemResource(final PetItemService petItemService) {
        this.petItemService = petItemService;
    }

    @GetMapping
    public ResponseEntity<List<PetItemDTO>> getAllPetItems() {
        return ResponseEntity.ok(petItemService.findAll());
    }

    @GetMapping("/{petItemId}")
    public ResponseEntity<PetItemDTO> getPetItem(
            @PathVariable(name = "petItemId") final Integer petItemId) {
        return ResponseEntity.ok(petItemService.get(petItemId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPetItem(@RequestBody @Valid final PetItemDTO petItemDTO) {
        final Integer createdPetItemId = petItemService.create(petItemDTO);
        return new ResponseEntity<>(createdPetItemId, HttpStatus.CREATED);
    }

    @PutMapping("/{petItemId}")
    public ResponseEntity<Integer> updatePetItem(
            @PathVariable(name = "petItemId") final Integer petItemId,
            @RequestBody @Valid final PetItemDTO petItemDTO) {
        petItemService.update(petItemId, petItemDTO);
        return ResponseEntity.ok(petItemId);
    }

    @DeleteMapping("/{petItemId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePetItem(
            @PathVariable(name = "petItemId") final Integer petItemId) {
        final ReferencedWarning referencedWarning = petItemService.getReferencedWarning(petItemId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        petItemService.delete(petItemId);
        return ResponseEntity.noContent().build();
    }

}
