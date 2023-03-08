package com.ubs.api.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"productType", "industry", "origin"})
@EqualsAndHashCode(exclude = {"productType", "industry", "origin"})
@Entity
@Table(name = "STOCK")
public class Stock implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "QUANTITY", nullable = false)
    private Integer quantity;

    @Column(name = "PRICE", nullable = false)
    private BigDecimal price;

    @Column(name = "VOLUME", nullable = false)
    private BigDecimal volume;

    @Column(name = "PRODUCT_TYPE", nullable = false)
    private String productType;

    @Column(name = "INDUSTRY", nullable = false)
    private String industry;

    @Column(name = "ORIGIN", nullable = false)
    private String origin;

}
