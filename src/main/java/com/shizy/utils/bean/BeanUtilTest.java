package com.shizy.utils.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanUtilTest
 *
 * @author by shizy19 shizy19@meicloud.com
 * @Date 2020/11/12 15:47
 */
public class BeanUtilTest {
    @Test
    public void getSetTest() throws Exception {
        Archer entity = new Archer();

        BeanUtil.set(entity, "attack", 111, int.class);
        int z = BeanUtil.get(entity, "attack", int.class);

        System.out.println();
    }

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
