package com.example.blog.service;

import com.example.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag saveTag(Tag Tag);//保存一个分类对象
    Tag getTag(Long id);//根据id来获取一个分类对象
    Page<Tag> listTag(Pageable pageable);//把查询出来的对象进行分页
    Tag update(Long id,Tag Tag);//根据id查询对象，并更新对象
    void deleteTag(Long id);//删除一个分类对象
    Tag getTagByName(String name);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    List<Tag> listTagTop(Integer size);
}
