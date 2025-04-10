package org.example.tutorial4_2.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="Role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getUsersId(){
        StringBuilder result = new StringBuilder();

        for(User user : users){
            result.append(user.getId()).append(",");
        }

        if(!result.isEmpty()){
            result.setCharAt(result.length() - 1, '.');
        }

        return result.toString();
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role #" + id + ". Name: " + name + ". Users that have this role: " + getUsersId();
    }
}
