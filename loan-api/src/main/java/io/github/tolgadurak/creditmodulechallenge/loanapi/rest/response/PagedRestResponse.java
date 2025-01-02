package io.github.tolgadurak.creditmodulechallenge.loanapi.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedRestResponse<T> {
    private List<T> items;
    private Integer page;
    private Integer size;
    private Integer pageCount;
    private Long totalCount;
}
