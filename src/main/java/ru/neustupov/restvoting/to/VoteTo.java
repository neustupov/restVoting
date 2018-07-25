package ru.neustupov.restvoting.to;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class VoteTo extends BaseTo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Date date;

    public VoteTo() {}

    public VoteTo(VoteTo voteTo) {
        this(voteTo.getDate());
    }

    public VoteTo(Date date) {
        this.date = date;
    }

    public VoteTo(Integer id, int userId, Date date, int restId) {
        super(id);
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
