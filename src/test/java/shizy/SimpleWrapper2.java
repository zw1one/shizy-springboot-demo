package shizy;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

/**
 * 重写EntityWrapper的eq
 * <p>
 * 因为EntityWrapper的eq，在params为null时会报错，这里加个判空则跳过
 */
public class SimpleWrapper2<T> extends EntityWrapper<T> {
    @Override
    public Wrapper<T> eq(String column, Object params) {
        if (ParamUtils.isBlack(params)) {
            return this;
        }
        return super.eq(column, params);
    }


    public static void main(String[] args) {

        Wrapper wrapper = new SimpleWrapper2();
        wrapper.eq("a", "a");
        wrapper.eq("b", null);


    }


}



















