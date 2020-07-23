/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import com._1c.g5.v8.dt.bsl.model.Method;
import com._1c.g5.v8.dt.bsl.validation.CustomValidationMessageAcceptor;
import com._1c.g5.v8.dt.bsl.validation.IExternalBslValidator;
import com._1c.g5.v8.dt.mcore.McorePackage;

/**
 * Пример расширения проверки модулей.
 * Для подробностей см. https://edt.1c.ru/dev/ru/docs/plugins/dev/lang/
 *
 */
public class ExampleValidator
    implements IExternalBslValidator
{
    public static final String ERROR_CODE = "Example_MethodNameStartWithCapitalLetter"; //$NON-NLS-1$

    @Override
    @Check(CheckType.FAST)
    public void validate(EObject object, CustomValidationMessageAcceptor messageAcceptor, CancelIndicator monitor)
    {
        Method method = (Method)object;
        if (!Character.isUpperCase(method.getName().charAt(0)))
        {
            messageAcceptor.warning(
                Messages.getString("ExampleValidator.MethodNameStartWithCapitalLetter"), //$NON-NLS-1$
                method,
                McorePackage.Literals.NAMED_ELEMENT__NAME, ERROR_CODE);
        }
    }

    @Override
    public boolean needValidation(EObject object)
    {
        return object instanceof Method;
    }

}
