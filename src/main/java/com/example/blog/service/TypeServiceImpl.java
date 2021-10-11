package com.example.blog.service;

import com.example.blog.NotFoundException;
import com.example.blog.dao.TypeRepository;
import com.example.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRepository typeRepository;
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).get();
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {

        return typeRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type update(Long id, Type type) {
         Type t=typeRepository.findById(id).get();
         if(t==null){
             throw new NotFoundException("不存在此类型");
         }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
       typeRepository.deleteById(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC,"blogs.size");
        Pageable pageable=PageRequest.of(0,size,sort);
        return typeRepository.findTop(pageable);
    }
}
