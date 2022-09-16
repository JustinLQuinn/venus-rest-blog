package qnns.venusrestblog.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import qnns.venusrestblog.repository.CategoriesRepository;
import qnns.venusrestblog.data.Category;

import java.util.List;

@RestController
@RequestMapping(produces = "application/json", value = "/api/categories")
public class CategoriesController {
    private CategoriesRepository categoriesRepository;
    @GetMapping("")
    private List<Category> fetchAllCategories() {
        return categoriesRepository.findAll();
    }

    @GetMapping("/search")
    private Category fetchCategoryByCategoryName(@RequestParam String categoryName) {
        Category cat = categoriesRepository.findByName(categoryName);
        if(cat == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category " + categoryName + " not found");
        }
        return cat;
    }
}
