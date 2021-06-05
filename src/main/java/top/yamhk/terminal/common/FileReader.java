package top.yamhk.terminal.common;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: YamHK
 * @Date: 2020/1/9 14:44
 */
public class FileReader<T> {


    private final LineMapper<T> lineMapper;

    /**
     * 构造方法
     *
     * @param lineMapper lineMapper
     */
    public FileReader(LineMapper<T> lineMapper) {
        this.lineMapper = lineMapper;
    }

    public List<T> read(File file) throws IOException {
        List<T> list = Lists.newArrayList();
        try (LineIterator iterator = FileUtils.lineIterator(file, "GBK")) {
            while (iterator.hasNext()) {
                list.add(lineMapper.mapLine(iterator.nextLine()));
            }
        }

        return list;
    }

    public interface LineMapper<T> {
        /**
         * 行映射为T类型的对象
         *
         * @param line line
         * @return T
         */
        T mapLine(String line);
    }
}
