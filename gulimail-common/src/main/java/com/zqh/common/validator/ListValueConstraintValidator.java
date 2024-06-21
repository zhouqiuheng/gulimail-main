package com.zqh.common.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {

    private Set<Integer> set = new HashSet<>();


    //初始化ListValue的详细信息ListValue constraintAnnotation及（@ListValue(vals={0,1})）
    @Override
    public void initialize(ListValue constraintAnnotation) {

        int[] vals = constraintAnnotation.vals();
        if (vals != null && vals.length != 0) {
            for (int val : vals) {
                set.add(val);
            }
        }
    }

    // 验证逻辑
    //Integer value是需要校验的值
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);// 如果set length==0，会返回false
    }
}
