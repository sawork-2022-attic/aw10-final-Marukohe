package com.micropos.common.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    private Product product;
    private int quantity;
}
