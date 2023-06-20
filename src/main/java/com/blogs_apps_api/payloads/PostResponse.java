package com.blogs_apps_api.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private List<PostsDto> results;
    private int pageNumber;
    private int pageSize;
    private long totalRecords;

    private  int totalPages;
    private boolean isLastPage;
}
