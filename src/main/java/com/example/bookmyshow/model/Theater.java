package com.example.bookmyshow.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theater")
@Getter
@Setter
@NoArgsConstructor
public class Theater {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="theater_seq")
    @SequenceGenerator(name = "theater_seq", sequenceName = "theater_seq", initialValue = 1, allocationSize=1)
    private Long id;

    private String name;
    private String city;

    @Embedded
    private Address address;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.MERGE)
    @JoinTable(name = "theater_offers",
            joinColumns = @JoinColumn(name = "theater_id"),
            inverseJoinColumns = @JoinColumn(name = "offers_id"),
            uniqueConstraints={@UniqueConstraint(columnNames={"theater_id","offers_id"})} )
    private List<Offer> offers = new ArrayList<>();


    public Theater(Long theaterid) {
        this.id=theaterid;
    }
}