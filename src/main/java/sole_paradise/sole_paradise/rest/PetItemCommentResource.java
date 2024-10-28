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
import sole_paradise.sole_paradise.model.PetItemCommentDTO;
import sole_paradise.sole_paradise.service.PetItemCommentService;


@RestController
@RequestMapping(value = "/api/petItemComments", produces = MediaType.APPLICATION_JSON_VALUE)
public class PetItemCommentResource {

    private final PetItemCommentService petItemCommentService;

    public PetItemCommentResource(final PetItemCommentService petItemCommentService) {
        this.petItemCommentService = petItemCommentService;
    }

    @GetMapping
    public ResponseEntity<List<PetItemCommentDTO>> getAllPetItemComments() {
        return ResponseEntity.ok(petItemCommentService.findAll());
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<PetItemCommentDTO> getPetItemComment(
            @PathVariable(name = "commentId") final Integer commentId) {
        return ResponseEntity.ok(petItemCommentService.get(commentId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createPetItemComment(
            @RequestBody @Valid final PetItemCommentDTO petItemCommentDTO) {
        final Integer createdCommentId = petItemCommentService.create(petItemCommentDTO);
        return new ResponseEntity<>(createdCommentId, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Integer> updatePetItemComment(
            @PathVariable(name = "commentId") final Integer commentId,
            @RequestBody @Valid final PetItemCommentDTO petItemCommentDTO) {
        petItemCommentService.update(commentId, petItemCommentDTO);
        return ResponseEntity.ok(commentId);
    }

    @DeleteMapping("/{commentId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deletePetItemComment(
            @PathVariable(name = "commentId") final Integer commentId) {
        petItemCommentService.delete(commentId);
        return ResponseEntity.noContent().build();
    }

}
