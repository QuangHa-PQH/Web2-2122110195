package com.phamquangha.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.phamquangha.demo.entity.Category;
import com.phamquangha.demo.repository.CategoryRepository;
import com.phamquangha.demo.dto.CategoryDTO; // Import DTO for Category

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository; // Inject CategoryRepository

    // 1. GET all categories
    @GetMapping
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = new ArrayList<>();

        for (Category category : categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(category.getId());
            categoryDTO.setName(category.getName());
            categoryDTO.setSlug(category.getSlug());

            // Chỉ lấy ID của các sản phẩm trong category
            categoryDTO.setProductIds(category.getProducts().stream()
                    .map(product -> product.getId())
                    .toList());

            categoryDTOs.add(categoryDTO);
        }

        return categoryDTOs;
    }

    // 2. GET category by ID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            CategoryDTO categoryDTO = new CategoryDTO();
            Category c = category.get();
            categoryDTO.setId(c.getId());
            categoryDTO.setName(c.getName());
            categoryDTO.setSlug(c.getSlug());

            // Chỉ lấy ID của các sản phẩm trong category
            categoryDTO.setProductIds(c.getProducts().stream()
                    .map(product -> product.getId())
                    .toList());

            return new ResponseEntity<>(categoryDTO, HttpStatus.OK); // Trả về CategoryDTO thay vì Category entity
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Trả về 404 nếu không tìm thấy
        }
    }

    // 3. POST to create a new category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryDTO) {
        // Chuyển DTO thành Category entity
        Category newCategory = new Category();
        newCategory.setName(categoryDTO.getName());
        newCategory.setSlug(categoryDTO.getSlug());

        try {
            Category savedCategory = categoryRepository.save(newCategory);
            return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Lỗi khi lưu
        }
    }

    // 4. PUT to update an existing category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        Optional<Category> existingCategoryOpt = categoryRepository.findById(id);
        if (existingCategoryOpt.isPresent()) {
            Category existingCategory = existingCategoryOpt.get();
            existingCategory.setName(categoryDTO.getName());
            existingCategory.setSlug(categoryDTO.getSlug());

            try {
                Category updatedCategory = categoryRepository.save(existingCategory);
                return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
            } catch (Exception e) {
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // Lỗi khi cập nhật
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Trả về 404 nếu không tìm thấy category
        }
    }

    // 5. DELETE to delete a category by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCategory(@PathVariable Long id) {
        try {
            categoryRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Trả về 204 nếu xóa thành công
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Lỗi khi xóa
        }
    }
}
