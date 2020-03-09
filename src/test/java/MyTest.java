import com.shizy.utils.auth.SecurityUtil;

public class MyTest {


    public static void main(String[] args) throws Exception {

//        System.out.println("Hello world!");

        for (int i = 0; i < 10; i++) {
            System.out.println(SecurityUtil.genAESKey());
        }

    }
}
