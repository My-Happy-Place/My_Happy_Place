package entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "FavoriteMovies")
@Entity
public class FavoriteMovies {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favoriteMovie_id_seq")
    @SequenceGenerator(name = "favoriteMovie_id_seq", sequenceName = "favoriteMovie_id_seq", allocationSize = 1)
    private Long id;

    @ManyToMany(mappedBy = "favoriteMovies")
    private List<User> userList = new ArrayList<>();

    @Nonnull
    @Column(name = "movieId_TMDB")
    private Long movieId;
}
