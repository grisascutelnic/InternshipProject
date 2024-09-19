package com.example.project.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;
import java.util.Date;

import static java.util.Base64.getDecoder;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "announcements")
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "price")
    private double price;

    @Column(name = "number")
    private String number;

    @Size(max = 1000)
    @Column(name = "description")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(name = "category")
    private String category;

    @Column(name = "type")
    private String type;

    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "profile_id")  // Creează o coloană foreign key în tabela "announcements"
    private Profile profile;

    public String getBase64Image() {
        if (this.image != null) {
            return Base64.getEncoder().encodeToString(this.image);
        }
        return null;
    }

    public void setBase64Image(String base64Image) {
        if (base64Image != null) {
            this.image = getDecoder().decode(base64Image);
        }
        this.image = null;
    }
}
