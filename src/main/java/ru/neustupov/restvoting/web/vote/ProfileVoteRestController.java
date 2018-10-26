package ru.neustupov.restvoting.web.vote;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.neustupov.restvoting.model.Vote;
import ru.neustupov.restvoting.to.VoteTo;

import java.net.URI;

@RestController
@RequestMapping(ProfileVoteRestController.REST_URL)
public class ProfileVoteRestController extends AbstractVoteController{

    static final String REST_URL = "/rest/profile/votes";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated @RequestBody VoteTo voteTo,
                                                   @RequestParam("restId") int restId) {
        Vote created = super.createFromTo(voteTo, restId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") int id, @Validated @RequestBody VoteTo voteTo,
                       @RequestParam("restId") int restId) {
        super.updateFromTo(id, voteTo, restId);
    }
}
