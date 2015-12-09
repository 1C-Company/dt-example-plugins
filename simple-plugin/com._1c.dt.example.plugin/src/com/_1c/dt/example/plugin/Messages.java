/**
 * Copyright (C) 2015, 1C
 */
package com._1c.dt.example.plugin;

import org.eclipse.osgi.util.NLS;

/**
 * Данный класс - представитель локализации механизма строк в Eclipse.
 */
class Messages
    extends NLS
{
    private static final String BUNDLE_NAME = "com.example.dt.plugin.messages"; //$NON-NLS-1$

    public static String DataProcessingHandler_Error_not_document_object_module;
    public static String DataProcessingHandler_Error_no_accumulation_registers;
    public static String DataProcessingHandler_Error;

    public static String DataProcessingHandlerDialog_dialog_title;
    public static String DataProcessingHandlerDialog_dialog_message;
    public static String DataProcessingHandlerDialog_dialog_text;
    public static String DataProcessingHandlerDialog_Registers;
    public static String DataProcessingHandlerDialog_Document_attributes;
    public static String DataProcessingHandlerDialog_Field;
    public static String DataProcessingHandlerDialog_Expressions;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages()
    {
    }
}
