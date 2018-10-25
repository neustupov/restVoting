package ru.neustupov.restvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
public class VoteTo extends BaseTo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Getter
    @Setter
    @NotNull
    private Date date;
}
