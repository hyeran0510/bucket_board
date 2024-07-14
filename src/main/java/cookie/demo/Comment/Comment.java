package cookie.demo.Comment;


import cookie.demo.Answer.Answer;
import cookie.demo.Question.Question;
import cookie.demo.User.SiteUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private SiteUser author;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;


    @ManyToOne
    private Answer answer;

    //수정, 삭제 후 상세페이지로 가는 메서드
    public Integer getQuestionId() {
        Integer result = null;
        if (this.question != null){
            result = this.question.getId();
        } else if (this.answer != null) {
            result = this.answer.getQuestion().getId();
        }
        return result;
        }
    }




