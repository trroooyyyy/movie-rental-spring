package edu.net.movierental.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "price")
@Builder
@AllArgsConstructor
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "genre", unique = true, nullable = false)
    private String genre;
    private double rentWithoutPenalty;
    private double priceWithoutPenalty;
    private double priceforPenalDays;
}
