package br.com.alyson.apimanagertask.domain.service;

import br.com.alyson.apimanagertask.domain.exception.BusinessException;
import br.com.alyson.apimanagertask.domain.model.Category;
import br.com.alyson.apimanagertask.domain.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Profile("test")
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveCategory(){
        // Given
        Category category = new Category();
        category.setName("Test");
        category.setDescription("Description test");

        // When
        Mockito.when(categoryRepository.save(category)).thenReturn(category);

        // Then
        Category categorySaved = categoryService.save(category);

        assertEquals(categorySaved.getName(), category.getName());
    }

    @Test
    public void testCategoryExists(){
        // Given
        Category category = new Category();
        category.setName("Test");

        // Mock the repository save method
        when(categoryRepository.save(category)).thenReturn(category);

        // When
        Category categorySaved = categoryService.save(category);

        // Mock the repository findByName method to return the saved category
        when(categoryRepository.findByName(categorySaved.getName())).thenReturn(categorySaved);

        // Create a new category with the same name
        Category category1 = new Category();
        category1.setName(categorySaved.getName());

        assertThrows(BusinessException.class, () -> categoryService.save(category1));
     }

     @Test
     public void testUpdateCategory(){
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Test");
        category.setDescription("Description test");

        // Mock the repository findById method
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // When
        Category categoryUpdated = new Category();
        categoryUpdated.setName("Test Updated");
        categoryUpdated.setDescription("Description test updated");

        // Mock the repository save method
        when(categoryRepository.save(category)).thenReturn(categoryUpdated);

        // Then
        Category category1 = categoryService.update(1L, categoryUpdated);

        assertEquals(category1.getName(), categoryUpdated.getName());
        assertEquals(category1.getDescription(), categoryUpdated.getDescription());
     }

     @Test
     public void testDeleteCategory(){
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Test");
        category.setDescription("Description test");

        // Mock the repository findById method
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // When
        categoryService.delete(1L);

        // Then
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteById(1L);
     }

     @Test
     public void testFindByIdCategory(){
        // Given
        Category category = new Category();
        category.setId(1L);
        category.setName("Test");
        category.setDescription("Description test");

        // Mock the repository findById method
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        // When
        Category category1 = categoryService.findById(1L);

        // Then
        assertEquals(category1.getId(), category.getId());
        assertEquals(category1.getName(), category.getName());
        assertEquals(category1.getDescription(), category.getDescription());
     }
}
