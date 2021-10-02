package com.example.blog.service;

import com.example.blog.NotFoundException;
import com.example.blog.dao.TagRepository;
import com.example.blog.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {

        return tagRepository.save(tag);
    }
    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {

        return tagRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Tag update(Long id, Tag tag) {
        Tag t=tagRepository.findById(id).get();
        if(t==null){
            throw new NotFoundException("不存在此类型");
        }
        BeanUtils.copyProperties(tag,t);
        return tagRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }
}
