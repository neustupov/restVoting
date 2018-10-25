package ru.neustupov.restvoting.to;

import lombok.Getter;
import lombok.Setter;
import ru.neustupov.restvoting.model.AbstractNamedEntity;
import ru.neustupov.restvoting.model.Menu;
import ru.neustupov.restvoting.model.Vote;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "restaurants")
public class RestaurantWithVotes extends AbstractNamedEntity {

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Menu> menus;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Vote> votes;

    @Getter
    @Setter
    private Integer numberOfVotes;

    public RestaurantWithVotes(@NotNull int id, @NotNull String name, int numberOfVotes) {
        super(id, name);
        this.numberOfVotes = numberOfVotes;
    }
}
