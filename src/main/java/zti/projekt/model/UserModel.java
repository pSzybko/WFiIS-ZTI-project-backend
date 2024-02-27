package zti.projekt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity(name = "user")
@Table(name = "user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username")
})
public class UserModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", schema = "public", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleModel> roles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "user_followers", schema = "public",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private Set<UserModel> followers = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "followers")
    @JsonIgnore
    private Set<UserModel> following = new HashSet<>();

    public UserModel() {
    }

    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleModel> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleModel> roles) {
        this.roles = roles;
    }
    public Set<UserModel> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserModel> followers) {
        this.followers = followers;
    }

    public Set<UserModel> getFollowing() {
        return following;
    }

    public void setFollowing(Set<UserModel> following) {
        this.following = following;
    }

    public void follow(UserModel user) {
        following.add(user);
        user.getFollowers().add(this);
    }

    public void unfollow(UserModel user) {
        following.remove(user);
        user.getFollowers().remove(this);
    }
}