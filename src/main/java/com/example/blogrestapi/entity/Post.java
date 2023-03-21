package com.example.blogrestapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private String content;

    @OneToMany(cascade = CascadeType.ALL,
    mappedBy = "post",
    orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public Post() {}

    public void addComment(Comment comment){
        this.comments.add(comment);
        comment.setPost(this);
    }
    public void removeComment(Comment comment){
        this.comments.remove(comment);
    }
}
