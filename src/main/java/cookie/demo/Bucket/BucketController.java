package cookie.demo.Bucket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/bucket")
@RequiredArgsConstructor
@Controller
public class BucketController {

    private final BucketListService bucketListService;

    @GetMapping("/list")
    public String getBucketList(Model model) {
        model.addAttribute("bucketList", bucketListService.getList());
        return "bucket_list";
    }

    @GetMapping("/create")
    public String bucketCreateForm(Model model) {
        model.addAttribute("bucketForm", new BucketForm());
        return "bucket_form";
    }

    @PostMapping("/create")
    public String createBucket(@ModelAttribute BucketForm bucketForm,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            // Handle file upload error
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/bucket/create";
        }

        bucketListService.create(bucketForm.getTitle(), bucketForm.getItems(), file, bucketForm.getRating());
        return "redirect:/bucket/list";
    }

    @GetMapping("/edit/{id}")
    public String editBucketListItem(@PathVariable Long id, Model model) {
        Bucket bucket = bucketListService.getBucket(id);
        if (bucket != null) {
            BucketForm bucketForm = new BucketForm();
            bucketForm.setId(bucket.getId());
            bucketForm.setTitle(bucket.getItems());
            bucketForm.setItems(bucket.getContent());
            model.addAttribute("bucketForm", bucketForm);
            return "bucket_form";
        }
        return "redirect:/bucket/list";
    }

    @PostMapping("/update")
    public String updateBucketListItem(@ModelAttribute BucketForm bucketForm,
                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                       RedirectAttributes redirectAttributes) throws IOException {
        if (file != null && file.isEmpty()) {
            // Handle file upload error
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/bucket/edit/" + bucketForm.getId();
        }

        Bucket bucket = bucketListService.getBucket(bucketForm.getId());
        if (bucket != null) {
            bucket.setItems(bucketForm.getTitle());
            bucket.setContent(bucketForm.getItems());
            bucket.setCreateDate(LocalDateTime.now());
            bucketListService.updateBucket(bucket, file);
        }

        return "redirect:/bucket/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBucketListItem(@PathVariable Long id) {
        bucketListService.deleteBucket(id);
        return "redirect:/bucket/list";
    }

}