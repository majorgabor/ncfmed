
package hu.elte.mynews.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class News extends BaseEntity{
    @Column(nullable = false)
    private String text;
    
    @Column(nullable = false)
    private Integer likes = 0;
    
    @Column(nullable = false)
    private Integer dislikes = 0;
    
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date = new Date();
    
    @OneToMany(targetEntity = Comment.class, 
               cascade = CascadeType.ALL,
               mappedBy = "news")
    private List<Comment> comments;
    
    @JoinColumn
    @ManyToOne(targetEntity = User.class)
    private User user;
}