package br.com.felipedias.novaToDoList.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.validation.ObjectError;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyName(source));
    }

    public static String[] getNullPropertyName(Object source){
        final BeanWrapper src  = new BeanWrapperImpl(source);

       PropertyDescriptor[] pds = src.getPropertyDescriptors();

       Set<String> emptyNames = new HashSet<>();

       for(PropertyDescriptor pd: pds){
           Object sourceValue = src.getPropertyValue(pd.getName());
           if(sourceValue == null){
               emptyNames.add(pd.getName());
           }
       }

       String[] result = new String[emptyNames.size()];

       return emptyNames.toArray(result);
    }
}
