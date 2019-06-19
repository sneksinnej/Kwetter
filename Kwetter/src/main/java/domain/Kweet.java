/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Kweet")
@Table(name = "kweet")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@NamedQueries({
    @NamedQuery(name = "Kweet.getKweets", query = "SELECT k FROM Kweet k")
    ,
    @NamedQuery(name = "Kweet.find", query = "SELECT k FROM Kweet k where k.id = :id")})
public class Kweet {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @ToString.Include
    private Long id;
    @Column(length = 140)
    private String text;
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @ManyToOne(cascade = {
        CascadeType.MERGE
    })
    @JoinColumn(name = "user_id")
    @ToString.Include
    private User owner;
    @ManyToMany(mappedBy = "kweets")
    private List<Hashtag> hashtags;
    @ManyToMany(cascade = {
        CascadeType.MERGE
    })
    @JoinTable(name = "kweet_likes",
            joinColumns = @JoinColumn(name = "kweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> likes;
    @ManyToMany(cascade = {
        CascadeType.MERGE
    })
    @JoinTable(name = "kweet_mentions",
            joinColumns = @JoinColumn(name = "kweet_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> mentions;

    public Kweet(Long id, String text, Date date, User owner, List<Hashtag> hashtags, List<User> likes, List<User> mentions) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.owner = owner;
        if (this.owner != null) {
            this.owner.addKweet(this);
        }
        this.hashtags = hashtags;
        this.likes = likes;
        this.mentions = mentions;
    }

    public Kweet(Long id, String text, User owner, List<User> mentions) {
        this.id = id;
        this.text = text;
        this.date = new Date();
        this.owner = owner;
        if (this.owner != null) {
            this.owner.addKweet(this);
        }
        this.hashtags = new ArrayList<Hashtag>();
        this.likes = new ArrayList<User>();
        this.mentions = mentions;
    }

    public Kweet(String text, User owner, List<User> mentions) {
        this.text = text;
        this.date = new Date();
        this.owner = owner;
        if (this.owner != null) {
            this.owner.addKweet(this);
        }
        this.hashtags = new ArrayList<Hashtag>();
        this.likes = new ArrayList<User>();
        this.mentions = mentions;
    }

    public void addMention(User u) {
        this.mentions.add(u);
        u.addMention(this);
    }
    
    public void removeMention(User u){
        this.mentions.remove(u);
        u.removeMention(this);
    }

    public boolean addHashtag(Hashtag h) {
        return (this.hashtags.add(h));
    }

    public void addLike(User u) {
        this.likes.add(u);
    }

    public void removeLike(User u) {
        this.likes.remove(u);
    }

    public Kweet(JsonObject input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Kweet.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Kweet other = (Kweet) obj;

        if (this.getId() != other.getId()) {
            return false;
        }

        return true;
    }

    public JsonObject toJson() {
        JsonObjectBuilder kweetJson = Json.createObjectBuilder();
        kweetJson.add("text", this.text);
        kweetJson.add("owner", this.owner.getUsername());

        JsonArrayBuilder hashtagsArray = Json.createArrayBuilder();
        for (Hashtag h : hashtags) {
            hashtagsArray.add(h.getText());
        }
        kweetJson.add("hashtags", hashtagsArray);

        JsonArrayBuilder likesArray = Json.createArrayBuilder();
        for (User u : likes) {
            likesArray.add(u.getUsername());
        }
        kweetJson.add("likes", likesArray);

        JsonArrayBuilder mentionsArray = Json.createArrayBuilder();
        for (User u : mentions) {
            mentionsArray.add(u.getUsername());
        }
        kweetJson.add("mentions", mentionsArray);
        return kweetJson.build();
    }

}
