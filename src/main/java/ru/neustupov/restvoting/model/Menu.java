package ru.neustupov.restvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    @Column(name = "add_date", columnDefinition = "date default current_date",  nullable = false)
    @NotNull
    private LocalDate addDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @JsonIgnoreProperties("menu")
    private Set<Meal> meals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rest", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Menu(Menu m) {
        this(m.getId(), m.getAddDate());
    }

    public Menu(Integer id, LocalDate addDate) {
        super(id);
        this.addDate = addDate;
    }
}
