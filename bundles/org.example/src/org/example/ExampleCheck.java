/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example;

import static com._1c.g5.v8.dt.bsl.model.BslPackage.Literals.METHOD;

import java.text.MessageFormat;

import org.eclipse.core.runtime.IProgressMonitor;

import com._1c.g5.v8.dt.bsl.model.Method;
import com._1c.g5.v8.dt.mcore.McorePackage;
import com.e1c.g5.v8.dt.check.CheckComplexity;
import com.e1c.g5.v8.dt.check.ICheckParameters;
import com.e1c.g5.v8.dt.check.components.BasicCheck;
import com.e1c.g5.v8.dt.check.settings.IssueSeverity;
import com.e1c.g5.v8.dt.check.settings.IssueType;

/**
 * Пример расширения проверки модулей.
 * Для подробностей см. https://edt.1c.ru/dev/ru/docs/plugins/dev/lang/
 *
 */
public class ExampleCheck
    extends BasicCheck<Void>
{
    /**
     * This check identifier
     */
    public static final String CHECK_ID = "example-method-name-start-with-capital-letter"; //$NON-NLS-1$

    @Override
    public String getCheckId()
    {
        return CHECK_ID;
    }

    @Override
    protected void configureCheck(CheckConfigurer builder)
    {
        builder.title(Messages.ExampleCheck_Title)
            .complexity(CheckComplexity.NORMAL)
            .severity(IssueSeverity.MINOR)
            .issueType(IssueType.CODE_STYLE)
            .module()
            .checkedObjectType(METHOD);
    }

    @Override
    protected void check(Object object, ResultAcceptor resultAcceptor, ICheckParameters parameters,
        IProgressMonitor monitor)
    {
        if (monitor.isCanceled())
        {
            return;
        }

        Method method = (Method)object;
        if (!Character.isUpperCase(method.getName().charAt(0)))
        {
            String message = MessageFormat
                .format(Messages.ExampleCheck_Name_of_method__0__should_start_with_capital_letter, method.getName());
            resultAcceptor.addIssue(message, McorePackage.Literals.NAMED_ELEMENT__NAME);
        }
    }
}
