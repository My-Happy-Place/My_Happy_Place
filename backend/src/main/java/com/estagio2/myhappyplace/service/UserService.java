package com.estagio2.myhappyplace.service;

import com.estagio2.myhappyplace.dto.MovieDTO;
import com.estagio2.myhappyplace.dto.UserDTO;
import com.estagio2.myhappyplace.entities.User;
import com.estagio2.myhappyplace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieService movieService;
    @Autowired
    private SeriesService seriesService;

    @Transactional(readOnly = true)
    public UserDTO findById(Long id){
        Optional<User> obj = this.userRepository.findById(id);
        if(obj.isPresent()){
            User user = obj.get();
            return new UserDTO(user);
        }
        return new UserDTO();
    }

    public boolean isFavorite(Integer idTMDB, Long idUser){
        UserDTO user = findById(idUser);
        List<Integer> idsTMDB = new ArrayList<>(user.getFavoriteMovies().stream().map(movie -> movie.getMovieId()).toList());
        idsTMDB.addAll(user.getFavoriteSeries().stream().map(serie -> serie.getSerieId()).toList());
        boolean isFavorite = false;
        for (Integer id : idsTMDB) {
            if(Objects.equals(id, idTMDB)){
                isFavorite = true;
            }
        }
        return isFavorite;
    }

    @Transactional(readOnly = true)
    public List<?> findFavorites(Long id, String descricao){
        UserDTO userDTO = findById(id);
        if (userDTO.getId() != null){
            if (descricao == null){
                List<Integer> idsMovies = userDTO.getFavoriteMovies().stream().map(i -> i.getMovieId()).toList();
                List<Integer> idsSeries = userDTO.getFavoriteSeries().stream().map(i -> i.getSerieId()).toList();
                return movieService.listAllFavorites(idsMovies, idsSeries);
            }
            if(descricao.trim().equalsIgnoreCase("m")){
                List<Integer> idsMovies = userDTO.getFavoriteMovies().stream().map(i -> i.getMovieId()).toList();
                return movieService.listFavoriteMovies(idsMovies);
            }
            if(descricao.trim().equalsIgnoreCase("s")){
                List<Integer> idsSeries = userDTO.getFavoriteSeries().stream().map(i -> i.getSerieId()).toList();
                return seriesService.listFavoriteSeries(idsSeries);
            }
        }

        return null;
    }

    @Transactional
    public UserDTO insert(UserDTO userDTO){
        User user = new User(null, userDTO.getName(), null, null);
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    public Optional<User> thereIsUser(Long id){
        return userRepository.findById(id);
    }

    @Transactional
    public UserDTO update(UserDTO userDTO){
        User obj = userRepository.findById(userDTO.getId()).get();
        obj.setName(userDTO.getName());
        obj.setFavoriteMovies(userDTO.getFavoriteMovies());
        obj.setFavoriteSeries(userDTO.getFavoriteSeries());
        obj = userRepository.save(obj);
        return new UserDTO(obj);
    }

    @Transactional
    public String delete(Long id){
        Optional<User> obj = userRepository.findById(id);
        if(obj.isPresent()){
            userRepository.deleteById(id);
            return "Usuario excluido com sucesso!";
        }
        return "Usuario não encontrado!";
    }
}
