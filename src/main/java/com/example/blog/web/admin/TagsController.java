package com.example.blog.web.admin;

import com.example.blog.po.Tag;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagsController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    //@PageableDefault:这个注解是设置每页排几个，按照id来排，倒叙的方式
    public String tags(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",tagService.listTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tag -input";
    }

    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id,Model model){
        model.addAttribute("tag",tagService.getTag(id));
        return "admin/tag -input";
    }
    @PostMapping("/tags/{id}")
    public String editinput(@Valid Tag type, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes attributes){
        Tag type2=tagService.getTagByName(type.getName());
        if(type2!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能为空");
        }
        Tag t=tagService.update(id,type);

        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags2")
    public String save(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes attributes){
        Tag tag1=tagService.getTagByName(tag.getName());
        if(tag1!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能为空");
        }
        Tag t=tagService.saveTag(tag);
        if(t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/tags";
    }
//    删除标签
    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
