package com.blogs_apps_api.entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name = "name" , nullable = false , length = 50)
    private String name;
    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

}
