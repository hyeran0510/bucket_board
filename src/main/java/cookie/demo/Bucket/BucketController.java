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
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/bucket")
@RequiredArgsConstructor
@Controller
public class BucketController {

    private final BucketListService bucketListService;

    @GetMapping("/list")
    public String getBucketList(Model model) {
        List<Bucket> bucketList = bucketListService.getList();
        model.addAttribute("bucketList", bucketList);
        return "bucket_list";
    }

    @GetMapping("/create")
    public String bucketCreate(BucketForm bucketForm) {
        return "bucket_form";
    }

    @PostMapping("/create")
    public String bucketCreate(@Valid BucketForm bucketForm, BindingResult bindingResult,
                               @RequestParam("file") MultipartFile file,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "bucket_form";
        }
        bucketListService.create(bucketForm.getTitle(), bucketForm.getItems(), file, bucketForm.getRating());
        return "redirect:/bucket/list";
    }

    @GetMapping("/modify/{id}")
    public String modifyBucketListItem(@PathVariable("id") Long id, Model model) {
        Bucket bucket = bucketListService.getBucket(id);
        if (bucket != null) {
            BucketForm bucketForm = new BucketForm();
            bucketForm.setId(bucket.getId());
            bucketForm.setTitle(bucket.getTitle());
            bucketForm.setItems(bucket.getItems());
            bucketForm.setRating(bucket.getRating());
            model.addAttribute("bucketForm", bucketForm);
            return "bucket_form";
        }
        return "redirect:/bucket/list";
    }

    @PostMapping("/modify")
    public String modifyBucketListItem(@Valid @ModelAttribute BucketForm bucketForm, BindingResult bindingResult,
                                       @RequestParam(value = "file", required = false) MultipartFile file,
                                       RedirectAttributes redirectAttributes) throws IOException {
        if (bindingResult.hasErrors()) {
            return "bucket_form";
        }

        if (file != null && file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/bucket/modify/" + bucketForm.getId();
        }

        Bucket bucket = bucketListService.getBucket(bucketForm.getId());
        if (bucket != null) {
            bucket.setTitle(bucketForm.getTitle());
            bucket.setItems(bucketForm.getItems());
            bucket.setRating(bucketForm.getRating());
            bucket.setCreateDate(LocalDateTime.now());
            bucketListService.modify(bucket, file);
        }

        return "redirect:/bucket/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteBucketListItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bucketListService.deleteBucket(id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error occurred while deleting the bucket item.");
            return "redirect:/bucket/list";
        }
        return "redirect:/bucket/list";
    }
}