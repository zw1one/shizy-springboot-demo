import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.shizy.utils.auth.SecurityUtil;
import lombok.Data;

public class MyTest {


    public static void main(String[] args) throws Exception {

//        System.out.println("Hello world!");

        for (int i = 0; i < 10; i++) {
            System.out.println(SecurityUtil.genAESKey());
        }

        LambdaQueryWrapper<ZZZ> wrapper1 = Wrappers.lambdaQuery();
        wrapper1.eq(ZZZ::getAa, "");

    }
}

@Data
class ZZZ{
    private String aa;
}