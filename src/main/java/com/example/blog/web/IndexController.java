package com.example.blog.web;



import com.example.blog.service.BlogService;
import com.example.blog.service.TagService;
import com.example.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;
    @GetMapping(value = "/")
    public String  indexhtml(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                              Model model){
       model.addAttribute("page",blogService.listblog(pageable));
       model.addAttribute("types",typeService.listTypeTop(5));
       model.addAttribute("tags",tagService.listTagTop(8));
       model.addAttribute("recommendblogs",blogService.listrecommend(3));
        return "index";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        blogService.viewsadd(id);
        model.addAttribute("blog",blogService.getAndconvert(id));
        return "/blog";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model){
        model.addAttribute("page",blogService.listquery("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "/search";
    }

    @GetMapping("/newblogs")
    public String newBlogs(Model model){
        model.addAttribute("title","Blog");
        model.addAttribute("newblogs",blogService.listrecommend(3));
        //return "_fragments";
        return "_fragments::newblog";
    }
}
