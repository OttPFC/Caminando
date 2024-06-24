package com.caminando.Caminando.presentationlayer.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Stream;

@Component
@Slf4j
public class EntityUtils {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Ignore {
    }



    public <E> void copy(E source, E destination) {
        Stream.of(source.getClass().getMethods())
                .filter(m -> m.getName().startsWith("get") && !m.getName().equals("getClass"))
                .forEach(m -> {
                    try {
                        var setterName = String.format("set%s", m.getName().substring(3));
                        var dm = destination.getClass().getMethod(setterName, m.getReturnType());
                        if (!dm.isAnnotationPresent(Ignore.class)) {
                            var res = m.invoke(source);
                            if (res != null ) {
                                dm.invoke(destination, res);
                            }
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
                             | NoSuchMethodException | SecurityException e) {
                        log.error(String.format("Exception copying objects (%s)", source.getClass().getSimpleName()), e);
                        throw new RuntimeException(e);
                    }
                });
    }
}
