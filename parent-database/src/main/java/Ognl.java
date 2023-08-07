import com.chenyudan.parent.core.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.Collection;
import java.util.Map;

/**
 * Description: TODO
 * Ognl访问静态方法的表达式为: @Ognl@method(args)
 *
 * @author Chyern
 * @since 2023/3/1 17:27
 */
public class Ognl {

    public static boolean isEmpty(Object o) {
        if (o instanceof String) {
            return StringUtil.isEmpty((String) o);
        }
        if (o instanceof Collection) {
            return CollectionUtils.isEmpty((Collection<?>) o);
        }
        if (o instanceof Map) {
            return MapUtils.isEmpty((Map<?, ?>) o);
        }
        return false;
    }

    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isBlank(Object o) {
        if (o instanceof String) {
            return StringUtil.isBlank((String) o);
        }
        return false;
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }
}
