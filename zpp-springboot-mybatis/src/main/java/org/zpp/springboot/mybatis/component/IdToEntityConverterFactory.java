package org.zpp.springboot.mybatis.component;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;
import org.zpp.springboot.mybatis.converter.SupportConverter;

import java.util.HashMap;
import java.util.Map;

@Component
public class IdToEntityConverterFactory implements ConverterFactory<String, SupportConverter> {

    private static final Map<Class, Converter> CONVERTER_MAP=new HashMap<>();

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public <T extends SupportConverter> Converter<String, T> getConverter(Class<T> targetType) {
        if (CONVERTER_MAP.get(targetType) == null) {
            CONVERTER_MAP.put(targetType, new IdToEntityConverter(targetType));
        }
        return CONVERTER_MAP.get(targetType);
    }

    private class IdToEntityConverter<T extends Model> implements Converter<String, T> {
        private final Class<T> tClass;

        public IdToEntityConverter(Class<T> tClass) {
            this.tClass = tClass;
        }

        @Override
        public T convert(String source) {
            String[] beanNames = applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(BaseMapper.class, tClass));
            BaseMapper mapper = (BaseMapper) applicationContext.getBean(beanNames[0]);
            T result = (T) mapper.selectById(Long.parseLong(source));
            if (result == null) throw new RuntimeException(tClass.getSimpleName() + " not found");
            return result;
        }
    }
}