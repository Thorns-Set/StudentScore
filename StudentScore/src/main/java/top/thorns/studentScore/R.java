package top.thorns.studentScore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Thorns
 * @Data:2022/11/1721:40
 * @PackageName:top.thorns.studentscore
 * @ClassName: R
 * - 通用结果Result
 * -用于SpringMVC的JSON消息返回
 * -消息属性：
 * - ok:true/false
 * - code:int 消息编号
 * - message:String 消息内容
 * - data:范型正文数据
 **/
public class R<T> {

    private Boolean ok;
    private String message;
    private Integer code;
    private T data;

    public R() {
        this.ok = true;
    }

    public R(boolean ok) {
        this.ok = ok;
    }

    public R(boolean ok, String message, Integer code, T data) {
        super();
        this.ok = ok;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public R(Integer code, String message, T data) {
        super();
        this.ok = false;
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static <T> R<T> ok() {
        return new R<T>();
    }

    public static <T> R<T> error() {
        return new R<T>(false);
    }


    public Boolean getOk() {
        return ok;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public R<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public R<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    /**
     * - 获取一个data类型为map的结果
     *
     * @param ok
     * @return
     */
    public static R<?> map(boolean ok) {
        Map<String, Object> map = new HashMap<>();
        return new R<Map<String, Object>>(ok, null, null, map);
    }

    /**
     * - 获取一个data类型为map的结果
     *
     * @return
     */
    public static R<?> map() {
        return map(true);
    }

    @SuppressWarnings("unchecked")
    public R<?> put(String key, Object value) {
        if (this.data instanceof Map) {
            ((Map<String, Object>) this.data).put(key, value);
            return this;
        } else {
            throw new RuntimeException("data does not belong to Map");
        }
    }


    public static R<?> list(boolean ok) {
        List<Object> list = new ArrayList<Object>();
        return new R<List<Object>>(ok, null, null, list);
    }

    public static R<?> list() {
        return list(true);
    }

    @SuppressWarnings("unchecked")
    public R<?> add(Object value) {
        if (this.data instanceof List) {
            ((List<Object>) this.data).add(value);
            return this;
        } else {
            throw new RuntimeException("data does not belong to List");
        }
    }

}