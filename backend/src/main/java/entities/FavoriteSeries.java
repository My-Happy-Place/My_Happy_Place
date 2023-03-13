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
@Table(name = "FavoriteSeries")
@Entity
public class FavoriteSeries {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "favoriteSeries_id_seq")
    @SequenceGenerator(name = "favoriteSeries_id_seq", sequenceName = "favoriteSeries_id_seq", allocationSize = 1)
    private Long id;

    @ManyToMany(mappedBy = "favoriteSeries")
    private List<User> userList = new ArrayList<>();

    @Nonnull
    @Column(name = "movieId_TMDB")
    private Long movieId;
}
