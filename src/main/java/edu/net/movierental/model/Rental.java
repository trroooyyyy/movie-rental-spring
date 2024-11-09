package edu.net.movierental.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rental")
@Builder
@AllArgsConstructor
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentalStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date rentalFinish;
}
