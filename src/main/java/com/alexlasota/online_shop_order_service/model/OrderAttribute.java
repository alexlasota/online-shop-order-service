package com.alexlasota.online_shop_order_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "order_attributes")
@Getter
@Setter
@NoArgsConstructor
@ToString(of = {"id", "name", "attributeValue"})
public class OrderAttribute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String attributeValue;
}