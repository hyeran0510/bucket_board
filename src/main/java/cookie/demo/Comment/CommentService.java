package cookie.demo.Comment;



import cookie.demo.Question.DataNotFoundException;
import cookie.demo.Question.Question;
import cookie.demo.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(Question question, String content, SiteUser author) {
        Comment c = new Comment();
        c.setContent(content);
        c.setCreateDate(LocalDateTime.now());
        c.setQuestion(question);
        c.setAuthor(author);
        c = this.commentRepository.save(c);
        return c;
    }

    public Comment getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("댓글을 찾을 수 없습니다!");

        }
        //return this.commentRepository.findById(id);
    }
    public Comment
    modify(Comment cmt, String content) {
        cmt.setContent(content);
        cmt.setModifyDate(LocalDateTime.now());
        cmt = this.commentRepository.save(cmt);
        return cmt;
    }

    public void delete(Comment c) {
        this.commentRepository.delete(c);
    }
}
