/**
 * Copyright (C) 2020, 1C-Soft LLC
 */
package org.example.ui;

import java.util.List;

import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com._1c.g5.v8.dt.mcore.Field;
import com._1c.g5.v8.dt.metadata.mdclass.AccumulationRegister;
import com._1c.g5.v8.dt.metadata.mdclass.AccumulationRegisterDimension;
import com._1c.g5.v8.dt.metadata.mdclass.AccumulationRegisterResource;
import com._1c.g5.v8.dt.metadata.mdclass.BasicRegister;
import com._1c.g5.v8.dt.metadata.mdclass.Document;
import com._1c.g5.v8.dt.metadata.mdclass.DocumentAttribute;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * Диалог конструктора движения регистра.
 * В данном диалоге пользователь может выбрать регистр накопления, для которого он хочет сделать обработку проведения.
 * А так же указать, состав полей, которые будут учавствовать в логике работы обработчика <br>
 * Данный конструктор не поддерживает работу с табличными частями документов
 */
public class DataProcessingHandlerDialog
    extends TitleAreaDialog
{
    private final Document document;
    private TableViewer expressionTable;
    private TableViewer registerTable;
    private String registerName;
    private List<Pair<String, String>> expressions;
    private List<BasicRegister> registers;

    /**
     * Конструктор
     * @param parentShell родительское окно диалога, не может быть <code>null</code>
     * @param document документ конфигурации, для модуля которого был вызван диалог, не может быть <code>null</code>
     * @param registers все регистраторы документа, типа Регистр накопления, не может быть <code>null</code>
     */
    public DataProcessingHandlerDialog(Shell parentShell, Document document, List<BasicRegister> registers)
    {
        super(parentShell);
        this.document = document;
        this.registers = registers;
        registerName = registers.get(0).getName();
    }

    /**
     * Получение выражений, заполненных пользователем, которые должны учавствовать в логике работы обработчки проведения
     * @return набор выражения обработчика проведения. Данный набор содержит пары значений.
     * Где первый элемент пары - это левая часть выражения, которое
     * нужно добавить в тело обработчика, а второй элемент пары - это правая часть выражения.
     * Не может быть <code>null</code>
     */
    public List<Pair<String, String>> getExpressions()
    {
        return expressions;
    }

    /**
     * Получение имени регистра накопления, для которого пользователь хочет создать обработчик проведения
     * @return имя регистра накопления, для которого создается обработчик проведения, не может быть <code>null</code>
     */
    public String getRegisterName()
    {
        return registerName;
    }

    /**
     * Данный метод будет вызван, когда пользователь нажмет кнопку "Ок" диалога.
     * В нем происходит наполнение набора выражений обработчика проведения, в соответствии с теми данными,
     * которые указал пользователь при работе с диалогом
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void okPressed()
    {
        expressions = Lists.newArrayList();
        for (Expression expr : (List<Expression>)expressionTable.getInput())
        {
            expressions.add(Tuples.create(expr.field, expr.expression));
        }
        super.okPressed();
    }

    /**
     * Данный метод создает и размещает все необходимые элементы в диалоге
     * {@inheritDoc}
     */
    @Override
    protected Control createDialogArea(Composite parent)
    {
        setTitle(Messages.DataProcessingHandlerDialog_dialog_title);
        setMessage(Messages.DataProcessingHandlerDialog_dialog_message);
        getShell().setText(Messages.DataProcessingHandlerDialog_dialog_text);
        Composite control = (Composite)super.createDialogArea(parent);
        createTableViewer(control);

        return control;
    }

    private void createTableViewer(Composite control)
    {
        Composite composite = new Composite(control, SWT.NONE);
        composite.setLayout(new GridLayout(2, true));
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
        registerTable = createDataTable(composite, Messages.DataProcessingHandlerDialog_Registers, getRegisters());
        registerTable.addSelectionChangedListener(new ISelectionChangedListener()
        {
            @Override
            public void selectionChanged(SelectionChangedEvent event)
            {
                //данный метод соответствует событию выбора регистра накопления из списка
                //при данном событии мы должны пересчитать выражения для обработчика проведения
                if (!registerName.equals(((IStructuredSelection)event.getSelection()).getFirstElement()))
                {
                    int index = registerTable.getTable().getSelectionIndex();
                    registerName = registers.get(index).getName();
                    expressionTable.setInput(createExpressionContent(index));
                    expressionTable.refresh();
                }
            }
        });
        final TableViewer documentTable = createDataTable(composite,
            Messages.DataProcessingHandlerDialog_Document_attributes, getDocumentAttributes());
        documentTable.addDoubleClickListener(new IDoubleClickListener()
        {
            @Override
            @SuppressWarnings("unchecked")
            public void doubleClick(DoubleClickEvent event)
            {
                //данный метод соответствует событию двойного клика на атрибуте документа
                //при данном событии мы заполняем правую часть активного выражения обработчика
                //проведения именем данного атрибута
                int index = documentTable.getTable().getSelectionIndex();
                String documentAttr = ((List<String>)documentTable.getInput()).get(index);
                int expressionIndex = expressionTable.getTable().getSelectionIndex();
                List<Expression> listOfExpressions = (List<Expression>)expressionTable.getInput();
                if (expressionIndex < 0 || expressionIndex >= listOfExpressions.size())
                {
                    expressionIndex = 0;
                }
                listOfExpressions.get(expressionIndex).expression = documentAttr;
                expressionTable.refresh();
            }
        });
        composite = new Composite(control, SWT.NONE);
        TableColumnLayout columnLayout = new TableColumnLayout();
        GridDataFactory.fillDefaults().grab(true, true).applyTo(composite);
        composite.setLayout(columnLayout);
        expressionTable = createExpressionTable(composite, columnLayout);
    }

    private TableViewer createExpressionTable(Composite composite, TableColumnLayout columnLayout)
    {
        final TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(tableViewer.getTable());
        int height = tableViewer.getTable().getItemHeight() * 5;
        tableViewer.getTable().setHeaderVisible(true);
        GridDataFactory.fillDefaults().hint(SWT.DEFAULT, height).grab(true, false).applyTo(tableViewer.getTable());
        TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.BORDER);
        columnLayout.setColumnData(column.getColumn(), new ColumnWeightData(5, ColumnWeightData.MINIMUM_WIDTH, true));
        column.getColumn().setResizable(true);
        column.getColumn().setText(Messages.DataProcessingHandlerDialog_Field);
        column.setLabelProvider(new DelegatingStyledCellLabelProvider(new ExpressionLabelProvider(true)));
        column = new TableViewerColumn(tableViewer, SWT.BORDER);
        columnLayout.setColumnData(column.getColumn(), new ColumnWeightData(5, ColumnWeightData.MINIMUM_WIDTH, true));
        column.getColumn().setResizable(true);
        column.getColumn().setText(Messages.DataProcessingHandlerDialog_Expressions);
        column.setLabelProvider(new DelegatingStyledCellLabelProvider(new ExpressionLabelProvider(false)));
        column.setEditingSupport(new CustomEditingSupport(column.getViewer()));
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        tableViewer.setInput(createExpressionContent(0));
        return tableViewer;
    }

    private List<Expression> createExpressionContent(int index)
    {
        List<String> documentAttr = getDocumentAttributes();
        List<String> registerAttr = getRegisterAttr(registers.get(index));
        List<Expression> expressionList = Lists.newArrayList();
        for (String attr : registerAttr)
        {
            if (documentAttr.contains(attr))
                expressionList.add(new Expression(attr, attr));
            else
                expressionList.add(new Expression(attr, "")); //$NON-NLS-1$
        }
        return expressionList;
    }

    private List<String> getRegisterAttr(BasicRegister registerRecords)
    {
        List<String> fields = Lists.newArrayList();
        if (registerRecords instanceof AccumulationRegister)
        {
            for (AccumulationRegisterDimension dimension : ((AccumulationRegister)registerRecords).getDimensions())
            {
                if (!Strings.isNullOrEmpty(dimension.getName()))
                    fields.add(dimension.getName());
            }

            for (AccumulationRegisterResource resource : ((AccumulationRegister)registerRecords).getResources())
            {
                if (!Strings.isNullOrEmpty(resource.getName()))
                    fields.add(resource.getName());
            }
            fields.add("Период"); //TODO: сделать поддержку генерации английского варианта обработчика //$NON-NLS-1$
        }
        return fields;
    }

    private TableViewer createDataTable(Composite composite, String headerName, List<String> content)
    {
        final TableViewer tableViewer = new TableViewer(composite, SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION);
        int height = tableViewer.getTable().getItemHeight() * 5;
        tableViewer.getTable().setHeaderVisible(true);
        GridDataFactory.fillDefaults().hint(SWT.DEFAULT, height).grab(true, false).applyTo(tableViewer.getTable());
        TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.BORDER);
        column.getColumn().setText(headerName);
        column.setLabelProvider(new DelegatingStyledCellLabelProvider(new CustomLabelProvider()));
        tableViewer.setContentProvider(ArrayContentProvider.getInstance());
        tableViewer.setInput(content);
        column.getColumn().pack();
        return tableViewer;
    }

    private List<String> getRegisters()
    {
        List<String> registerNames = Lists.newArrayList();
        for (BasicRegister register : this.registers)
        {
            if (!Strings.isNullOrEmpty(register.getName()))
                registerNames.add(register.getName());
        }
        return registerNames;
    }

    private List<String> getDocumentAttributes()
    {
        List<String> documentAttributes = Lists.newArrayList();
        for (DocumentAttribute attr : document.getAttributes())
        {
            if (!Strings.isNullOrEmpty(attr.getName()))
                documentAttributes.add(attr.getName());
        }
        for (Field field : document.getFields())
        {
            if (!Strings.isNullOrEmpty(field.getNameRu()))
                documentAttributes.add(field.getNameRu());
        }
        return documentAttributes;
    }

    private static class Expression
    {
        public String field;
        public String expression;

        Expression(String field, String expression)
        {
            this.field = field;
            this.expression = expression;
        }
    }

    private static class CustomLabelProvider
        extends LabelProvider
        implements IStyledLabelProvider
    {
        @Override
        public String getText(Object element)
        {
            return super.getText(element);
        }

        @Override
        public StyledString getStyledText(Object element)
        {
            return new StyledString(super.getText(element));
        }

        @Override
        public Image getImage(Object element)
        {
            return super.getImage(element);
        }
    }

    private static class ExpressionLabelProvider
        extends LabelProvider
        implements IStyledLabelProvider
    {
        private final boolean isFieldProvider;

        ExpressionLabelProvider(boolean isFieldProvider)
        {
            this.isFieldProvider = isFieldProvider;
        }

        @Override
        public String getText(Object element)
        {
            if (isFieldProvider)
                return super.getText(((Expression)element).field);
            else
                return super.getText(((Expression)element).expression);
        }

        @Override
        public StyledString getStyledText(Object element)
        {
            if (isFieldProvider)
                return new StyledString(super.getText(((Expression)element).field));
            else
                return new StyledString(super.getText(((Expression)element).expression));
        }

        @Override
        public Image getImage(Object element)
        {
            return super.getImage(element);
        }
    }

    private static class CustomEditingSupport
        extends EditingSupport
    {
        private final CellEditor editor;

        CustomEditingSupport(ColumnViewer viewer)
        {
            super(viewer);
            editor = new TextCellEditor((Composite)viewer.getControl());
        }

        @Override
        protected boolean canEdit(Object arg0)
        {
            return true;
        }

        @Override
        protected CellEditor getCellEditor(Object arg0)
        {
            return editor;
        }

        @Override
        protected Object getValue(Object arg0)
        {
            return ((Expression)arg0).expression;
        }

        @Override
        protected void setValue(Object arg0, Object arg1)
        {
            //no op
        }

        @Override
        protected void saveCellEditorValue(CellEditor cellEditor, ViewerCell cell)
        {
            String value = (String)cellEditor.getValue();
            ((Expression)cell.getElement()).expression = value;
            getViewer().refresh();
        }
    }
}
