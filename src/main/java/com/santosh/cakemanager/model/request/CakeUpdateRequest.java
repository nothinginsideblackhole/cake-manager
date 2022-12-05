package com.santosh.cakemanager.model.request;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class CakeUpdateRequest {

    private String description;

    private String imageLink;
}
