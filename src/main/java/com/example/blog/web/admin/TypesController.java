package com.example.blog.web.admin;

import com.example.blog.po.Type;
import com.example.blog.service.TypeService;
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
public class TypesController {
    @Autowired
    private TypeService typeService;
    @GetMapping("/types")
    //@PageableDefault:这个注解是设置每页排几个，按照id来排，倒叙的方式
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type",new Type());
        return "admin/type -input";
    }

    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id,Model model){
        model.addAttribute("type",typeService.getType(id));
        return "admin/type -input";
    }
    @PostMapping("/types/{id}")
    public String editinput(@Valid Type type, BindingResult bindingResult,@PathVariable Long id, RedirectAttributes attributes){
        Type type2=typeService.getTypeByName(type.getName());
        if(type2!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能重复");
        }
        Type t=typeService.update(id,type);

        if(t==null){
            attributes.addFlashAttribute("message","更新失败");
        }else{
            attributes.addFlashAttribute("message","更新成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types2")
    public String save(@Valid Type type, BindingResult bindingResult, RedirectAttributes attributes){
        Type type1=typeService.getTypeByName(type.getName());
        if(type1!=null){
            bindingResult.rejectValue("name","nameError","分类名称不能重复");
        }
        Type t=typeService.saveType(type);
        if(t==null){
            attributes.addFlashAttribute("message","操作失败");
        }else{
            attributes.addFlashAttribute("message","操作成功");
        }
        return "redirect:/admin/types";
    }
//    删除分类
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id,RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }
}
