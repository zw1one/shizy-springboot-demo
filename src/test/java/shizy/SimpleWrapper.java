package shizy;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 重写EntityWrapper的eq
 * <p>
 * 因为EntityWrapper的eq，在params为null时会报错，这里加个判空则跳过
 */
public class SimpleWrapper<T> extends EntityWrapper<T> {

    interface SimpleWrapperInterface<T> {
        public Wrapper<T> eq(String column, Object params);
    }

    private SimpleWrapperInterface simpleWrapperInterface = (SimpleWrapperInterface) Proxy.newProxyInstance(SimpleWrapper.class.getClassLoader(), new Class[]{SimpleWrapperInterface.class}, (proxy, method, args) -> {
        //第二个参数为null就跳过
        if (args.length >= 2 && ParamUtils.isBlack(args[1])) {
            return proxy;
        }

        Method superMethod = proxy.getClass().getSuperclass().getMethod(method.getName(), method.getParameterTypes());

//        return method.invoke(proxy, args);
        return superMethod.invoke(proxy, args);
    });

    @Override
    public Wrapper<T> eq(String column, Object params) {
        return simpleWrapperInterface.eq(column, params);
    }


    public static void main(String[] args) {

        Wrapper wrapper = new SimpleWrapper();

        wrapper.eq("a", "a");
        wrapper.eq("b", null);

    }


}





















