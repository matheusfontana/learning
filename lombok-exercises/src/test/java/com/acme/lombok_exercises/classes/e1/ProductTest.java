package com.acme.lombok_exercises.classes.e1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Create a product using the static factory method
 * Create the same product using the builder pattern
 * Modify the product to be out of stock using toBuilder
 */
class ProductTest {

    @Test
    void shouldCreateAProductUsingTheStaticFactoryMethod(){
        Product product = Product.of("Macbook", 1299.00, "Laptops", true);

        assertNotNull(product);
        assertThat(product.getName()).isEqualTo("Macbook");
        assertThat(product.getPrice()).isEqualTo(1299.00);
        assertThat(product.getCategory()).isEqualTo("Laptops");
        assertThat(product.isInStock()).isTrue();
    }

    @Test
    void shouldCreateAProductUsingBuilderPattern(){
        Product productStatic = Product.of("Lenovo Thinkpad",
                1879.99,
                "Laptops",
                true);

        Product product = Product.builder()
                .name("Lenovo Thinkpad")
                .price(1879.99)
                .category("Laptops")
                .inStock(true)
                .build();

        assertThat(productStatic)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    void shouldModifyExistingProductToBeOutOfStock() {
        Product product = Product.builder()
                .name("Alienware Aurora")
                .price(1399.99)
                .category("Laptops")
                .inStock(true)
                .build();

        Product outOfStock = product.toBuilder().inStock(false).build();

        assertThat(outOfStock)
                .usingRecursiveComparison()
                .ignoringFields("inStock")
                .isEqualTo(product);

        assertThat(outOfStock.isInStock()).isFalse();
        assertThat(product.isInStock()).isTrue();
    }
}