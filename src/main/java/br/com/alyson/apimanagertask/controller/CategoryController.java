package br.com.alyson.apimanagertask.controller;

import br.com.alyson.apimanagertask.domain.model.Category;
import br.com.alyson.apimanagertask.domain.service.CategoryService;
import br.com.alyson.apimanagertask.dto.CategorySaveDto;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {

        List<Category> categories = categoryService.findAll();

        return ResponseEntity.ok(categories);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> findById(@PathVariable Long id) {

        Category category = categoryService.findById(id);

        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> save(@Valid @RequestBody CategorySaveDto categorySaveDto) {

        Category category = new Category();

        BeanUtils.copyProperties(categorySaveDto, category);

        Category categorySaved = categoryService.save(category);

        return ResponseEntity.ok(categorySaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @Valid @RequestBody CategorySaveDto categorySaveDto) {

        Category category = new Category();

        BeanUtils.copyProperties(categorySaveDto, category);

        Category categoryUpdated = categoryService.update(id, category);

        return ResponseEntity.ok(categoryUpdated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

}
