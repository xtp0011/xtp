package com.imooc.sell.entity.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.imooc.sell.entity.ProductCategory;

public interface ProductCategoryMapper {
	
	@Insert("insert into product_category(category_name,catagory_type) values(#{category_name,jdbcType=VARCHAR},#{catagory_type,jdbcType=INTEGER})")
	int insertByMap(Map<String,Object> map);
	
	@Insert("insert into product_category(category_name,catagory_type) values(#{categoryName,jdbcType=VARCHAR},#{categoryType,jdbcType=INTEGER})")
	int insertByObject(ProductCategory productCategory);

	@Select("select * from product_category where catagory_type=#{catagory_type}")
	@Results({
		@Result(column="catagory_type",property="categoryType"),
		@Result(column="category_name",property="categoryName"),
		@Result(column="catagory_id",property="categoryId")
	})
	ProductCategory findByCategoryType(Integer categoryType);
	
	@Select("select * from product_category where category_name=#{category_name}")
	@Results({
		@Result(column="catagory_type",property="categoryType"),
		@Result(column="category_name",property="categoryName"),
		@Result(column="catagory_id",property="categoryId")
	})
	List<ProductCategory> findByCategoryName(String categoryName);
	
	@Update("update product_category set category_name=#{categoryName} where catagory_type=#{categoryType}")
	int updateByCategoryType(@Param("categoryType")Integer categoryType,@Param("categoryName")String categoryName);
	
	@Update("update product_category set category_name=#{categoryName} where catagory_type=#{categoryType}")
	int updateByObject(ProductCategory productCategory);
	
	@Delete("delete from product_category where catagory_type=#{categoryType `q}")
	int deleteByCategoryType(Integer categoryType);
}
