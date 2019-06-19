/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "User")
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "user.findByusername", query = "SELECT U FROM User u WHERE u.username = :username")})
@Getter
@Setter
@NoArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class User {

    @Id
    @ToString.Include
    @GeneratedValue(strategy = GenerationType.TABLE)
    private long id;
    @Column(unique = true)
    @ToString.Include
    private String username;
    private String bio;
    private byte[] photo;
    private String location;
    private String website;
    @Getter(AccessLevel.PRIVATE) //Todo check wat het acces level precies moet zijn
    private String wachtwoord;
    @Enumerated(EnumType.STRING)
    private Language language;
    @Enumerated(EnumType.STRING)
    private Role role;
    @ManyToMany(cascade = {
        CascadeType.MERGE
    })
    @JoinTable(name = "followers",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following;
    @ManyToMany(mappedBy = "following")
    private List<User> followers;
    @OneToMany(mappedBy = "owner")
    private List<Kweet> kweets;
    @ManyToMany(mappedBy = "mentions")
    private List<Kweet> mentions;
    @ManyToMany(mappedBy = "likes")
    private List<Kweet> likes;

    public User(long id, String username, String bio, byte[] photo, String location, String website, String wachtwoord, Language language, Role role, List<User> following, List<User> followers, List<Kweet> kweets, List<Kweet> mentions, List<Kweet> likes) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.photo = photo;
        this.location = location;
        this.website = website;
        this.wachtwoord = wachtwoord;
        this.language = language;
        this.role = role;
        this.following = following;
        this.followers = followers;
        this.kweets = kweets;
        this.mentions = mentions;
        this.likes = likes;
    }

    public User(long id, String username, String bio, byte[] photo, String location, String website, String wachtwoord, Language language) {
        this.id = id;
        this.username = username;
        this.bio = bio;
        this.photo = photo;
        this.location = location;
        this.website = website;
        this.wachtwoord = wachtwoord;
        this.language = language;
        this.role = Role.User;
        this.following = new ArrayList<User>();
        this.followers = new ArrayList<User>();
        this.kweets = new ArrayList<Kweet>();
        this.likes = new ArrayList<Kweet>();
        this.mentions = new ArrayList<Kweet>();

    }

    public User(String username, String bio, byte[] photo, String location, String website, String wachtwoord, Language language) {
        this.username = username;
        this.bio = bio;
        this.photo = photo;
        this.location = location;
        this.website = website;
        this.wachtwoord = wachtwoord;
        this.language = language;
        this.role = Role.User;
        this.following = new ArrayList<User>();
        this.followers = new ArrayList<User>();
        this.kweets = new ArrayList<Kweet>();
        this.likes = new ArrayList<Kweet>();
        this.mentions = new ArrayList<Kweet>();
    }

    /**
     * Simple User (for creating initial user)
     * @param username
     * @param wachtwoord
     */
    public User(String username, String wachtwoord) {
        this.username = username;
        this.wachtwoord = wachtwoord;
        this.bio = "";
        this.photo = new byte[1];
        this.location = "";
        this.website = "";
        this.language = Language.English;
        this.role = Role.User;
        this.following = new ArrayList<User>();
        this.followers = new ArrayList<User>();
        this.kweets = new ArrayList<Kweet>();
        this.likes = new ArrayList<Kweet>();
        this.mentions = new ArrayList<Kweet>();
    }

    public void addKweet(Kweet k) {
        if (!kweets.contains(k)) {
            this.kweets.add(k);
        }
    }

    public void removeFollower(User u) {
        this.followers.remove(u);
    }

    public void removeFollowing(User u) {
        this.following.remove(u);
    }

    public void addFollower(User u) {
        if (!this.followers.contains(u)) {
            this.followers.add(u);
        }
    }

    public void addFollowing(User u) {
        if (!this.following.contains(u)) {
            this.following.add(u);
        }
    }

    public void addMention(Kweet k) {
        if (!this.mentions.contains(k)) {
            this.mentions.add(k);
        }
    }

    public void removeMention(Kweet k) {
        this.mentions.remove(k);
    }

    public void addLike(Kweet k) {
        if (!this.likes.contains(k)) {
            this.likes.add(k);
        }
    }

    public void removeLike(Kweet k) {
        this.likes.remove(k);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        
        if (!User.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final User other = (User) obj;
        
        if (this.getId() != other.getId() && this.getUsername() != other.getUsername()) {
            return false;
        }

        return true;
    }
    
    public JsonObject toJson() {
        //moet photo hierbij? TODO  misschien kweets gewoon toJson en dan meegeven ipv id?
        JsonObjectBuilder userJson = Json.createObjectBuilder();
        userJson.add("username", this.username);
        userJson.add("bio", this.bio);
        userJson.add("location", this.location);
        userJson.add("website", this.website);
        userJson.add("language", this.language.toString());
        userJson.add("role", this.role.toString());

        JsonArrayBuilder followingArray = Json.createArrayBuilder();
        for (User u : following) {
            followingArray.add(u.getUsername());
        }
        userJson.add("following", followingArray);

        JsonArrayBuilder followersArray = Json.createArrayBuilder();
        for (User u : followers) {
            followersArray.add(u.getUsername());
        }
        userJson.add("followers", followersArray);

        JsonArrayBuilder kweetsArray = Json.createArrayBuilder();
        for (Kweet k : kweets) {
            kweetsArray.add(k.getId());
        }
        userJson.add("kweets", kweetsArray);

        JsonArrayBuilder mentionsArray = Json.createArrayBuilder();
        for (Kweet k : mentions) {
            mentionsArray.add(k.getId());
        }
        userJson.add("mentions", mentionsArray);

        JsonArrayBuilder likesArray = Json.createArrayBuilder();
        for (Kweet k : likes) {
            likesArray.add(k.getId());
        }
        userJson.add("likes", likesArray);

        return userJson.build();
    }

}
