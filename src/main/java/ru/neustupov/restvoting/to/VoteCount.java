package ru.neustupov.restvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteCount {

    private Integer restId;
    private Long voteCount;


    public Long getVoteCount() {
        return voteCount;
    }
}
