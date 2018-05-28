package ru.neustupov.restvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.neustupov.restvoting.View;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "menus")
public class Menu extends AbstractBaseEntity {

    @Column(name = "add_date", columnDefinition = "date default current_date",  nullable = false)
    @NotNull
    private Date addDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "menu")
    @JsonIgnoreProperties("menu")
    private Set<Meal> meals;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rest", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull(groups = View.Persist.class)
    private Restaurant restaurant;

    public Menu() {
    }

    public Menu(Menu m) {
        this(m.getId(), m.getAddDate());
    }

    public Menu(Integer id, Date addDate) {
        super(id);
        this.addDate = addDate;
    }

    public Menu(Date addDate) {
        this.addDate = addDate;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Set<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
