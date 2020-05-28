package application.data.service;

import application.data.model.Category;
import application.data.repository.CategoryRepository;
import application.model.viewmodel.ChartLabelDataVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category findOne(int categoryId){
        return categoryRepository.findOne(categoryId);
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public void addNewCategory(Category category){
        categoryRepository.save(category);
    }

    @Transactional
    public void delCategory(int CategoryId){
        categoryRepository.delete(CategoryId);
    }

    public List<Category> getListCategoryByName(String CategoryName){
        return categoryRepository.getListCategoryByName(CategoryName);
    }

    public List<ChartLabelDataVM> countProductByCategory(){
        return categoryRepository.countProductByName();
    }

}
