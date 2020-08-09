/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Данный класс - представитель локализации механизма строк в Eclipse.
 */
final class Messages
    extends NLS
{
    private static final String BUNDLE_NAME = "org.example.ui.messages"; //$NON-NLS-1$

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
