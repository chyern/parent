package com.chyern.connect.analysis;

import com.chyern.connect.constant.MediaType;
import com.chyern.connect.constant.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2023/2/28 15:25
 */
@Getter
@AllArgsConstructor
public class MethodAnalysis {

    private String url;

    private Method method;

    private MediaType mediaType;
}
