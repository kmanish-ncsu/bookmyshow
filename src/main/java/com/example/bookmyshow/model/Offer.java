package com.example.bookmyshow.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "offer")
@Getter
@Setter
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="offer_seq")
    @SequenceGenerator(name = "offer_seq", sequenceName = "offer_seq", initialValue = 1, allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "offers")
    private List<Theater> theaters = new java.util.ArrayList<>();

//    private String city;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    public enum OfferType{
        THIRD_TICKET,AFTERNOON_TICKET
    }
}