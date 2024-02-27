package zti.projekt.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zti.projekt.exception.UserNotFoundException;
import zti.projekt.model.UserModel;
import zti.projekt.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono użytkownika o podanym ID: " + id));
    }
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
    public void followUser(Integer followerId, Integer followedId) {
        UserModel follower = getUserById(followerId);
        UserModel followed = getUserById(followedId);

        follower.follow(followed);
        userRepository.save(follower);
    }

    public void unfollowUser(Integer followerId, Integer followedId) {
        UserModel follower = getUserById(followerId);
        UserModel followed = getUserById(followedId);

        follower.unfollow(followed);
        userRepository.save(follower);
    }
    public boolean isFollowing(Integer followerId, Integer followedId) {
        UserModel follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found - id: " + followerId));
        UserModel followed = userRepository.findById(followedId)
                .orElseThrow(() -> new UserNotFoundException("Followed not found - id: " + followedId));

        return follower.getFollowing().contains(followed);
    }

    public boolean isFollower(Integer followerId, Integer followedId) {
        UserModel follower = userRepository.findById(followerId)
                .orElseThrow(() -> new UserNotFoundException("Follower not found - id: " + followerId));
        UserModel followed = userRepository.findById(followedId)
                .orElseThrow(() -> new UserNotFoundException("Followed not found - id: " + followedId));

        return followed.getFollowers().contains(follower);
    }
}
