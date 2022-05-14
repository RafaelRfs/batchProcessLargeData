package com.site.rfs.batchprocesslargefile.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Client{
    private Integer id;
    private String name;
    private Integer planId;
    private Plan plan;

}
