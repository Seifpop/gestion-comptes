package com.formation.jee.utils;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import java.util.Set;

/**
 * Provides a static method to resolve and retrieve a bean instance from the CDI (Contexts
 * and Dependency Injection) container.
 */
public final class BeanInjection {
    /**
     * Obtained from the current CDI context.
     */
    private static final BeanManager BEAN_MANAGER = CDI.current().getBeanManager();

    private BeanInjection() {
        throw new IllegalStateException("Utility class.");
    }

    /**
     * Resolves and retrieves a bean instance of the specified class from the CDI container.
     *
     * @param <T>   The type of the bean to be resolved.
     * @param clazz The class of the bean to be resolved.
     * @return The resolved bean instance, or `null` if no bean of the specified class is found.
     */
    @SuppressWarnings({"unchecked"})
    public static <T> T resolve(final Class<T> clazz) {
        // Get the set of beans of the specified class from the BeanManager
        Set<Bean<?>> beans = BEAN_MANAGER.getBeans(clazz);
        if (beans.isEmpty()) {
            return null;
        }

        // Get the first/default bean from the set
        Bean<?> bean = beans.iterator().next();
        final CreationalContext<?> ctx = BEAN_MANAGER.createCreationalContext(bean);
        return (T) BEAN_MANAGER.getReference(bean, clazz, ctx);
    }
}
