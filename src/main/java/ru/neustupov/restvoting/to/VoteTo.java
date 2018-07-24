package ru.neustupov.restvoting.to;

import java.io.Serializable;
import java.util.Date;

public class VoteTo extends BaseTo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int userId;

    private Date date;

    private int restId;

    public VoteTo() {}

    public VoteTo(VoteTo voteTo) {
        this(voteTo.getUserId(), voteTo.getDate(), voteTo.getRestId());
    }

    public VoteTo(int userId, Date date, int restId) {
        this.userId = userId;
        this.date = date;
        this.restId = restId;
    }

    public VoteTo(Integer id, int userId, Date date, int restId) {
        super(id);
        this.userId = userId;
        this.date = date;
        this.restId = restId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }
}
