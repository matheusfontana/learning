package com.acme.lombok_exercises.classes.e1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * Task:
 * Add annotations to support static factory, builder, and toBuilder
 */
@Builder(toBuilder = true)
@AllArgsConstructor(staticName = "of")
@Getter
public class Product {
    private final String name;
    private final double price;
    private final String category;
    private boolean inStock;
}
