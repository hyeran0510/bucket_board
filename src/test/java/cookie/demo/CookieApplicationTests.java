package cookie.demo;

import cookie.demo.Question.Question;
import cookie.demo.Question.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CookieApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Test
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("무엇을 만들건가요?");
		q1.setContent("울 강쥐 쿠키랑 관련된거요");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

	}

}
