package ru.neustupov.restvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "meals")
public class Meal extends AbstractNamedEntity {

    @Column(name = "price",  nullable = false)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Menu menu;

    public Meal(Meal m) {
        this(m.getId(), m.getName(), m.getPrice());
    }

    public Meal(int id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public Meal(String name, Integer price) {
        super(null, name);
        this.price = price;
    }
}
