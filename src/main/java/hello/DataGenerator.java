package hello;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 数据生成器
 *
 * @author Freeman
 */
public class DataGenerator {
    public static void run(HashMap<Integer, Object> node, Integer level, Integer size, Integer elements) {
        if (level <= 0) {
            return;
        }
        HashMap<Integer, Object> data = new HashMap<>(elements);
        node.put(0, data);
        fill(node, elements - 1, size);
        run(data, level - 1, size, elements);
    }

    public static void fill(HashMap<Integer, Object> node, Integer index, Integer size) {
        if (index <= 0) {
            return;
        }
        ArrayList<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add(String.valueOf(index%10));
        }
        String value = StringUtils.join(data, "");
        node.put(index, value);
        fill(node, index - 1, size);
    }
}
