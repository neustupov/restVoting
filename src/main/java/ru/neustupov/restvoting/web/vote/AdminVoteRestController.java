package ru.neustupov.restvoting.web.vote;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.neustupov.restvoting.View;
import ru.neustupov.restvoting.model.Vote;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(AdminVoteRestController.REST_URL)
public class AdminVoteRestController extends AbstractVoteController {

    static final String REST_URL = "/rest/admin/votes";

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Vote get(@PathVariable("id") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/todays", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getTodaysVotes() {
        return super.getTodaysVotes();
    }

    @GetMapping(value = "/todaysByRest", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Vote> getTodaysVotesOfAuthUserAndRest(@RequestParam("restId") int restId){
        return super.getTodaysVotesOfAuthUserAndRest(restId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated(View.Web.class) @RequestBody Vote vote, @RequestParam("restId") int restId) {
        Vote created = super.create(vote, restId);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("id") int id, @Validated(View.Web.class) @RequestBody Vote vote,
                       @RequestParam("restId") int restId) {
        super.update(id, vote, restId);
    }

    @Override
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        super.delete(id);
    }

    @Override
    @GetMapping(value = "/filter")
    public List<Vote> getBetween(
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(value = "restId") int restId) {
        return super.getBetween(startDate, endDate, restId);
    }
}
