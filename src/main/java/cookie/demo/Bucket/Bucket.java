package cookie.demo.Bucket;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Bucket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200)
    private String items;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private String fileName;

    private String filePath;

    private int rating; // 별점 필드 추가


}
