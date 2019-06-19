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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Hashtag")
@Table(name = "hashtag")
@NamedQueries({
    @NamedQuery(name = "hashtag.findBytext", query = "SELECT h FROM Hashtag h WHERE h.text = :text")})
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
public class Hashtag {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Getter(AccessLevel.PRIVATE)
    @ToString.Include
    private long id;
    @ToString.Include
    private String text;
    @ManyToMany(cascade = {
        CascadeType.MERGE
    })
    @JoinTable(name = "hashtag_kweets",
            joinColumns = @JoinColumn(name = "hashtag_id"),
            inverseJoinColumns = @JoinColumn(name = "kweet_id")
    )
    private List<Kweet> kweets;

    public Hashtag(long id, String text, Kweet kweet) {
        this.id = id;
        this.text = text;
        this.kweets = new ArrayList<Kweet>();
        this.kweets.add(kweet);
    }

    public Hashtag(long id, String text, List<Kweet> kweets) {
        this.id = id;
        this.text = text;
        this.kweets = kweets;
    }

    public Hashtag(String text, Kweet kweet) {
        this.text = text;
        this.kweets = kweets;
        this.kweets = new ArrayList<Kweet>();
        this.kweets.add(kweet);
    }

    public void addKweet(Kweet k) {
        this.kweets.add(k);
    }

    @Override
    public String toString() {
        return "id: " + id + " text: " + text;
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!Hashtag.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Hashtag other = (Hashtag) obj;

        if (this.getId() != other.getId() && this.getText() != other.getText()) {
            return false;
        }

        return true;
    }

    public JsonObject toJson() {
        JsonObjectBuilder hashtagJson = Json.createObjectBuilder();
        hashtagJson.add("text", this.text);
        JsonArrayBuilder kweetsArray = Json.createArrayBuilder();
        for (Kweet k : kweets) {
            kweetsArray.add(k.getId());
        }
        hashtagJson.add("kweets", kweetsArray);
        return hashtagJson.build();
    }

}
