package adeo.leroymerlin.cdp.entity;

import javax.persistence.*;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public Member() {
        // constructeur par défaut sans paramètres
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
