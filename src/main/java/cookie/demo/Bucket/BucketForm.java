package cookie.demo.Bucket;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BucketForm {

    private Long id;
    private String title;
    private String items;

    public int getRating() {
        return 0;
    }
}