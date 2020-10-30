package com.shizy.utils.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtil.class);

    /***********************************************************/

    /**
     * 复制map中的参数到实体类中，以entity中成员变量是否存在为准
     * 注：两个类的成员变量(或get方法)的名字、类型需一致
     * 成员变量建议定义成包装类，基本类型没有值默认是0，null与0是不同的
     *
     * @param paramMap 键值对与Entity成员变量对应的map
     * @param entity   被填充内容的Entity
     * @return entity 被填充内容的Entity 可以不处理这个返回，参数中的引用类型entity，其值已经被改变
     */
    public static <T> T copyMapParam2Entity(Map paramMap, T entity) {

        if (paramMap == null) {
            return entity;
        }

        Class<?> aClass = entity.getClass();
        //迭代set
        for (Method method : aClass.getMethods()) {
            if (method.getName().indexOf("set") != 0) {
                continue;
            }
            String field = method.getName().substring(3, 4).toLowerCase() +
                    method.getName().substring(4);
            try {
                method.invoke(entity, paramMap.get(field));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return entity;
    }

    public static <T> List<T> copyMapParam2EntityList(List<Map> paramList, T entity) {
        List<T> list = new ArrayList<>();
        for (Map map : paramList) {
            try {
                list.add(copyMapParam2Entity(map, (T) entity.getClass().newInstance()));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static <S, T> T copyProperties(S source, T target, String... ignoreFields) {
        if (source == null || target == null) {
            return target;
        }
        Class<?> aClass = target.getClass();
        //迭代set
        for (Method method : aClass.getMethods()) {
            if (method.getName().indexOf("set") != 0) {
                continue;
            }
            String field = method.getName().substring(3, 4).toLowerCase() +
                    method.getName().substring(4);
            if (isIgnoredField(ignoreFields, field)) {
                continue;
            }
            Object sourceFieldValue = null;
            try {
                Method crtMethod = source.getClass()
                        .getMethod("get" + field.substring(0, 1).toUpperCase() + field.substring(1));
                crtMethod.setAccessible(true);
                sourceFieldValue = crtMethod.invoke(source);
                if (sourceFieldValue == null) {
                    continue;
                }
                if (!method.getParameterTypes()[0].isAssignableFrom(sourceFieldValue.getClass())) {
                    continue;
                }
                method.invoke(target, sourceFieldValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                logger.error("反射调用ParamUtil.copyProperties方法失败[source={}],[target={}]", source, target);
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                //do nothing
//                e.printStackTrace();//
            }
        }
        return target;
    }

    /**
     * 复制entity中的参数到other entity中，以target中成员属性是否存在为准
     *
     * @param source 提供数据的entity 参数命名需与target一致
     * @param target 被填充内容的entity
     * @return target 被填充内容的target 可以不处理这个返回，参数中的引用类型target，其值已经被改变
     */
    public static <S, T> T copyProperties(S source, T target) {
        return copyProperties(source, target, (String[]) null);
    }

    private static boolean isIgnoredField(String[] ignoreFields, String field) {

        if (ignoreFields == null) {
            return false;
        }

        for (String ignoreField : ignoreFields) {
            if (ignoreField.equals(field)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 复制泛型S的List数据到泛型T的List中，以target中成员属性是否存在为准
     *
     * @param sourceList  泛型S的List
     * @param targetClass 用于确定结果List的泛型T
     * @return 泛型T的结果List
     */
    public static <S, T> List<T> copyPropertiesList(List<S> sourceList, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        for (S source : sourceList) {
            try {
                targetList.add(copyProperties(source, (T) targetClass.newInstance()));
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("反射调用newInstance方法失败[class={}],[field={}]", targetClass);
                throw new RuntimeException(e);
            }
        }
        return targetList;
    }

    /***********************************************************/

    public static <T> Map<? extends String, ?> genMapFromEntity(T entity, Map<String, Object> existMap) {

        Class<?> aClass = entity.getClass();
        //迭代get
        for (Method method : aClass.getMethods()) {
            if (method.getName().indexOf("get") != 0 || method.getName().indexOf("getClass") == 0) {
                //若不为get或为getClass
                continue;
            }
            String field = method.getName().substring(3, 4).toLowerCase() +
                    method.getName().substring(4);
            try {
                existMap.put(field, method.invoke(entity));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return existMap;
    }

    /**
     * entity to map
     */
    public static <T> Map<? extends String, ?> genMapFromEntity(T entity) {
        return genMapFromEntity(entity, new HashMap<>());
    }

    /**
     * 通过TableId/TableField，识别PO类的字段
     *
     * @param poClass
     * @return PO类的字段
     */
    public static List<Field> getFields(Class poClass) {
        List<Field> fieldNames = new ArrayList();
        for (Field field : poClass.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(TableId.class) == null && field.getAnnotation(TableField.class) == null) {
                continue;
            }
            if (field.getAnnotation(TableField.class) != null && !field.getAnnotation(TableField.class).exist()) {
                continue;
            }
            fieldNames.add(field);
        }
        return fieldNames;
    }

    /***********************************************************/

    /**
     * 反射调用实例的get方法
     *
     * @param obj        被调用的实例对象
     * @param field      字段名
     * @param fieldClass 字段类型
     * @return get方法返回的数据
     */
    public static <S, T> T get(S obj, String field, Class<T> fieldClass) {
        try {
            return (T) obj.getClass().getMethod(
                    "get" + field.substring(0, 1).toUpperCase() + field.substring(1)
            ).invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("反射调用get方法失败[obj={}],[field={}]", obj, field);
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    /**
     * 反射调用set
     */
    public static <S, V> void set(S obj, String field, V value, Class fieldClass) {
        Method m = null;
        //get Method
        try {
            m = obj.getClass().getMethod(
                    "set" + field.substring(0, 1).toUpperCase() + field.substring(1),
                    fieldClass
            );
        } catch (NoSuchMethodException e) {
            return;
        }
        //invoke
        try {
            m.invoke(obj, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("反射调用set方法失败[obj={}],[field={}]", obj, field);
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getSetTest() throws Exception {
        Archer entity = new Archer();

        BeanUtil.set(entity, "attack", 111, int.class);
        int z = BeanUtil.get(entity, "attack", int.class);

        System.out.println();
    }

    /***********************************************************/

    @Test
    public void copyMapParam2EntityTest() throws Exception {
        Map paramMap = new HashMap();
        paramMap.put("name", "ashe");
        paramMap.put("title", "ice archer");
        paramMap.put("attack", 67);

        Archer entity = new Archer();

        BeanUtil.copyMapParam2Entity(paramMap, entity);

        System.out.println();
    }

    @Test
    public void copyParam2EntityTest() throws Exception {
        Assassin source = new Assassin("zed", "Lord of Shadows", 80, "hehehe");
        Archer target = new Archer();

        BeanUtil.copyProperties(source, target);

        System.out.println();
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Archer {
    private String name;
    private String title;
    private int attack;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Assassin {
    private String name;
    private String title;
    private int attack;
    private String finalSkill;
}











