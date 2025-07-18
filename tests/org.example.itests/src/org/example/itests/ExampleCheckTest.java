/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example.itests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.example.ExampleCheck;
import org.junit.Test;

import com._1c.g5.v8.dt.core.platform.IDtProject;
import com._1c.g5.v8.dt.validation.marker.Marker;
import com.e1c.g5.v8.dt.testing.check.SingleProjectReadOnlyCheckTestBase;

/**
 * Пример теста на проверку
 *
 * Больше информации о написании тестов см https://edt.1c.ru/dev/ru/docs/plugins/dev/testing/
 */
public class ExampleCheckTest
    extends SingleProjectReadOnlyCheckTestBase
{
    private static final String PROJECT_NAME = "test"; //$NON-NLS-1$

    private static final String MANAGED_APPLICATION_MODULE = "/src/Configuration/ManagedApplicationModule.bsl"; //$NON-NLS-1$

    private static final String MANAGED_APPLICATION_MODULE_URI = "/" + PROJECT_NAME + MANAGED_APPLICATION_MODULE; //$NON-NLS-1$

    private static final String LINE_KEY = "line"; //$NON-NLS-1$

    @Test
    public void testMethodStartsWithCapitalLetterWarning() throws Exception
    {
        IDtProject dtProject = dtProjectManager.getDtProject(getTestConfigurationName());
        assertNotNull(dtProject);

        Marker marker = getFirstMarker(ExampleCheck.CHECK_ID, MANAGED_APPLICATION_MODULE_URI, dtProject);
        assertNotNull(marker);
        assertEquals("1", marker.getExtraInfo().get(LINE_KEY)); //$NON-NLS-1$
    }

    @Override
    protected String getTestConfigurationName()
    {
        return PROJECT_NAME;
    }
}
