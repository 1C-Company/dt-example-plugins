/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example;

import org.eclipse.osgi.util.NLS;

/**
 * Локализация
 */
final class Messages
{
    private static final String BUNDLE_NAME = "org.example.messages"; //$NON-NLS-1$

    public static String ExampleCheck_Title;
    public static String ExampleCheck_Name_of_method__0__should_start_with_capital_letter;

    static
    {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    private Messages()
    {
    }
}
