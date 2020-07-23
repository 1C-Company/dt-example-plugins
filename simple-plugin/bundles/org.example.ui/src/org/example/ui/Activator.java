/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example.ui;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.BundleContext;

/**
 * Данный класс представляет собой начальную точку в работе плагина.
 * В нем следует реализовывать логику создания плагина,
 * а так же необходимые действия при завершении работы плагина. <br>
 *
 * Так же данный класс содержит в себе ряд методов для удобного логирования ошибок
 */
public class Activator
    extends Plugin
{
    public static final String PLUGIN_ID = "com.example.dt.plugin.ui"; //$NON-NLS-1$
    private static Activator plugin;

    private BundleContext bundleContext;

    /**
     * Получить экземпляр плагина. Через экземпляр плагина можно получать доступ к разнообразным механизмам Eclipse,
     * таким как:
     * <ul>
     * <li>Журнал логирования ошибок плагина</li>
     * <li>Механизм настройки свойств плагина</li>
     * <li>Механизм дескрипторов</li>
     * </ul>
     *
     * @return экземпляр плагина, никогда не должен возвращать <code>null</code>
     */
    public static Activator getDefault()
    {
        return plugin;
    }

    /**
     * Запись статуса события в лог журнал плагина.
     *
     * @param status статус события для логирования, не может быть <code>null</code>.
     * Данный статус содержит в себе информацию о произошедшем событии (ошибка выполнения,
     * разнообразные предупреждения), которые были зафиксированы в логике работы плагина.
     */
    public static void log(IStatus status)
    {
        plugin.getLog().log(status);
    }

    /**
     * Запись исключения в лог журнал плагина
     *
     * @param throwable выкинутое исключение, не может быть <code>null</code>
     */
    public static void logError(Throwable throwable)
    {
        log(createErrorStatus(throwable.getMessage(), throwable));
    }

    /**
     * Создание записи с описанием ошибки в лог журнале плагина по выкинотому исключению и сообщению, его описывающим
     *
     * @param message описание выкинутого исключения, не может быть <code>null</code>
     * @param throwable выкинутое исключение, может быть <code>null</code>
     * @return созданное статус событие, не может быть <code>null</code>
     */
    public static IStatus createErrorStatus(String message, Throwable throwable)
    {
        return new Status(IStatus.ERROR, PLUGIN_ID, 0, message, throwable);
    }

    /**
     * Создание записи с описанием предупреждения в лог журнале плагина
     *
     * @param message описание предупреждения, не может быть <code>null</code>
     * @return созданное статус событие, не может быть <code>null</code>
     */
    public static IStatus createWarningStatus(String message)
    {
        return new Status(IStatus.WARNING, PLUGIN_ID, 0, message, null);
    }

    /**
     * Создание записи с описанием предупреждения в лог журнале плагина по выкинотому исключению и сообщению,
     * его описывающим
     *
     * @param message описание выкинутого исключения, не может быть <code>null</code>
     * @param throwable выкинутое исключение, может быть <code>null</code>
     * @return созданное статус событие, не может быть <code>null</code>
     */
    public static IStatus createWarningStatus(final String message,
        Exception throwable)
    {
        return new Status(IStatus.WARNING, PLUGIN_ID, 0, message, throwable);
    }

    /**
     * Данный метод является начальной точкой работы плагина
     *
     * @param bundleContext объект, создаваемый OSGi Framework, для доступа к разнообразным сервисам, например,
     * по работе с файловыми ресурсами внутри проекта
     */
    @Override
    public void start(BundleContext bundleContext) throws Exception
    {
        super.start(bundleContext);

        this.bundleContext = bundleContext;
        plugin = this;
    }

    /**
     * Данный метод вызывается при завершении работы плагина
     *
     * @param bundleContext объект, создаваемый OSGi Framework, для доступа к разнообразным сервисам, например,
     * по работе с файловыми ресурсами внутри проекта
     */
    @Override
    public void stop(BundleContext bundleContext) throws Exception
    {
        plugin = null;
        super.stop(bundleContext);
    }

    /**
     * Получить объект, создаваемый OSGi Framework, для доступа к разнообразным сервисам, например, по работе с
     * файловыми ресурсами внутри проекта
     *
     * @return объект, создаваемый OSGi Framework, для доступа к разнообразным сервисам, например, по работе с
     * файловыми ресурсами внутри проекта
     */
    protected BundleContext getContext()
    {
        return bundleContext;
    }
}
