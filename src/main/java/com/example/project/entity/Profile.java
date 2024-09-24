package com.example.project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "profiles")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date registrationDate;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(unique = true, name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "services")
    private String services;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "total_rating")
    private int totalRating = 0;

    @Column(name = "number_of_ratings")
    private int numberOfRatings = 0;

    @ElementCollection
    @CollectionTable(name = "rated_by_emails", joinColumns = @JoinColumn(name = "profile_id"))
    @Column(name = "email")
    private List<String> ratedByEmails = new ArrayList<>();

    public void addRater(String email) {
        if (!ratedByEmails.contains(email)) {
            ratedByEmails.add(email);
        }
    }

    public boolean hasRated(String email) {
        return ratedByEmails.contains(email);
    }

    // Metodă pentru calcularea mediei rating-urilor
    public double getAverageRating() {
        if (numberOfRatings == 0) {
            return 0;
        }
        return (double) totalRating / numberOfRatings;
    }

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Announcement> announcements = new ArrayList<>();

    @OneToMany(mappedBy = "profile")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Event> events = new ArrayList<>();

    public String getBase64Image() {
        if (this.image != null) {
            return Base64.getEncoder().encodeToString(this.image);
        }
        return null;
    }

    public void setBase64Image(String base64Image) {
        if (base64Image != null) {
            this.image = Base64.getDecoder().decode(base64Image);
        } else {
            this.image = null;
        }
    }
}
