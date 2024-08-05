package br.com.alyson.apimanagertask.domain.service;

import br.com.alyson.apimanagertask.domain.exception.BusinessException;
import br.com.alyson.apimanagertask.domain.exception.DatabaseException;
import br.com.alyson.apimanagertask.domain.exception.EntityNotFoundException;
import br.com.alyson.apimanagertask.domain.model.Category;
import br.com.alyson.apimanagertask.domain.repository.CategoryRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    @Transactional
    public Category save(Category category) {

        Category categoryExists = categoryRepository.findByName(category.getName());

        if (categoryExists != null && !categoryExists.equals(category)) {
            throw new BusinessException("Category already exists");
        }
        try {
            categoryRepository.save(category);
        } catch (ConstraintViolationException e) {
            throw new DatabaseException("There was a database error", e.getCause());
        }
        Category CategorySaved = categoryRepository.save(category);

        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = findById(id);

        try {
            categoryRepository.deleteById(category.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("There was a database error", e.getCause());
        }

    }

    @Transactional
    public Category update(Long id, Category category) {
        Category category1 = findById(id);
        BeanUtils.copyProperties(category, category1, "id");
        return categoryRepository.save(category1);
    }
}
