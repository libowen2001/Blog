package com.example.blog.service;

import com.example.blog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);//保存一个分类对象
    Type getType(Long id);//根据id来获取一个分类对象
    Page<Type> listType(Pageable pageable);//把查询出来的对象进行分页
    Type update(Long id,Type type);//根据id查询对象，并更新对象
    void deleteType(Long id);//删除一个分类对象
    Type getTypeByName(String name);
    List<Type> listType();

}
