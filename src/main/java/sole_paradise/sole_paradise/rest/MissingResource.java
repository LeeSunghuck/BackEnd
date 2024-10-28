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
import sole_paradise.sole_paradise.model.MissingDTO;
import sole_paradise.sole_paradise.service.MissingService;


@RestController
@RequestMapping(value = "/api/missings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MissingResource {

    private final MissingService missingService;

    public MissingResource(final MissingService missingService) {
        this.missingService = missingService;
    }

    @GetMapping
    public ResponseEntity<List<MissingDTO>> getAllMissings() {
        return ResponseEntity.ok(missingService.findAll());
    }

    @GetMapping("/{missingId}")
    public ResponseEntity<MissingDTO> getMissing(
            @PathVariable(name = "missingId") final Integer missingId) {
        return ResponseEntity.ok(missingService.get(missingId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Integer> createMissing(@RequestBody @Valid final MissingDTO missingDTO) {
        final Integer createdMissingId = missingService.create(missingDTO);
        return new ResponseEntity<>(createdMissingId, HttpStatus.CREATED);
    }

    @PutMapping("/{missingId}")
    public ResponseEntity<Integer> updateMissing(
            @PathVariable(name = "missingId") final Integer missingId,
            @RequestBody @Valid final MissingDTO missingDTO) {
        missingService.update(missingId, missingDTO);
        return ResponseEntity.ok(missingId);
    }

    @DeleteMapping("/{missingId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMissing(
            @PathVariable(name = "missingId") final Integer missingId) {
        missingService.delete(missingId);
        return ResponseEntity.noContent().build();
    }

}
