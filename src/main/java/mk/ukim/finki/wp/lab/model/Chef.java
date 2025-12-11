package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chef {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String bio;

    @OneToMany(mappedBy = "chef", fetch = FetchType.EAGER)
    private List<Dish> dishes;

    public Chef(String firstName, String lastName, String bio) {
        this.id = (long) (Math.random() * 1000);
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.dishes = new ArrayList<>();
    }

    public Chef(Long id, String firstName, String lastName, String bio) {
        this.id = (long) (Math.random() * 1000);
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
}
