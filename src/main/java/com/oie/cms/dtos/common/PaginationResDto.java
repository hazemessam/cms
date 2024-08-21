package com.oie.cms.dtos.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PaginationResDto<T> {
     private List<T> items;
     private long itemsTotalCount;
}
