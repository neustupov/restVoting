package ru.neustupov.restvoting.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.neustupov.restvoting.HasId;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseTo implements HasId{

    protected Integer id;
}
