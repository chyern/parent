package com.chyern.parent.connect.processor;

import com.chyern.parent.connect.annotation.Connect;

import java.lang.annotation.Annotation;

/**
 * Description: TODO
 *
 * @author Chyern
 * @since 2022/7/29 15:41
 */
public class ConnectProcessor extends AbstractConnectProcessor {

    @Override
    public Class<? extends Annotation> getProcessorAnnotation() {
        return Connect.class;
    }
}
