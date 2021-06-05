package top.yamhk.terminal.server4test.done;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import top.yamhk.core.kernel.BeanCopyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

/**
 * @Author: YingX
 * @Date: 2020/11/26 20:57
 */
@Slf4j
public class CopyTest {
    public void work() {
        testFunctionAndJudge(beanInstance -> BeanCopyUtils.copyBySpring(beanInstance, BeanInstance.class));
        testFunctionAndJudge(beanInstance -> BeanCopyUtils.copyByHuTool(beanInstance, BeanInstance.class));
        testFunctionAndJudge(beanInstance -> BeanCopyUtils.copyByGson(beanInstance, BeanInstance.class));
    }

    private boolean testFunctionAndJudge(Function<BeanInstance, BeanInstance> function) {
        BeanInstance beanInstance = new BeanInstance();
        beanInstance.bigDecimal = BigDecimal.TEN;
        beanInstance.list = Lists.newArrayList("a", "b", "c");
        beanInstance.person = new Person("liLei", 18);
        beanInstance.person.child = new Child();
        beanInstance.person.child.setUsername("xiaobaobao");
        beanInstance.person.child.setPassword("xiaobaobao");
        beanInstance.ints = new int[]{1, 2, 3};
        BeanInstance copy = function.apply(beanInstance);
//        Long t1 = System.currentTimeMillis();
//        for (int i = 0; i < 1000000; i++) {
//            copy = function.apply(beanInstance);
//        }
//        Long t2 = System.currentTimeMillis();
//        log.warn("拷贝一百万次,耗时:{}", t2 - t1);
        log.info("拷贝之后的对象-1:{}", copy);
        beanInstance.bigDecimal = beanInstance.bigDecimal.add(BigDecimal.TEN);
        beanInstance.list.add("d");
        beanInstance.person.setUsername("HMM");
        beanInstance.person.child.setUsername("ddd");
        log.info("拷贝之后的对象-2:{}", copy);
        boolean judgeResult = false;
        try {
            judgeResult = testSame(beanInstance, copy);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        log.error("========================================深拷贝测试结果:{}", judgeResult);
        return judgeResult;
    }

    private boolean testSame(BeanInstance beanInstance, BeanInstance copy) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Field[] declaredFieldsA = beanInstance.getClass().getDeclaredFields();
        Field[] declaredFieldsB = copy.getClass().getDeclaredFields();
        boolean result = true;
        for (Field field : declaredFieldsA) {
            log.warn("检测属性:{}", field.getName());
            String method1 = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            Object value1 = beanInstance.getClass().getMethod(method1).invoke(beanInstance);
            Object value2 = beanInstance.getClass().getMethod(method1).invoke(copy);
            if (value1 == value2) {
                log.error("相同属性{}", field);
                result = false;
            }
        }
        return result;
    }

    @Data
    public static class BeanInstance {
        private List<String> list;
        private Person person;
        private BigDecimal bigDecimal;
        private int[] ints;
    }

    @Data
    class Person {
        private String username;
        private Integer age;
        private Child child;

        public Person(String liLei, Integer s) {
            this.username = liLei;
            this.age = s;
        }
    }

    /**
     * @Author: YingX
     * @Date: 2020/11/27 11:35
     */
    @Data
    class Child {
        private String username;
        private String password;
    }
}
