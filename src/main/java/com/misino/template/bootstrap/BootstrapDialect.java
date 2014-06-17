package com.misino.template.bootstrap;

import com.misino.template.bootstrap.elements.ErrorSummaryProcessor;
import com.misino.template.bootstrap.elements.InputFieldProcessor;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by misino on 12/29/13.
 */
public class BootstrapDialect extends AbstractDialect
{
    @Override
    public String getPrefix() {
        return "bootstrap";
    }

    @Override
    public Set<IProcessor> getProcessors() {
        final Set<IProcessor> processors = new HashSet<IProcessor>();
        processors.add(new InputFieldProcessor());
        processors.add(new ErrorSummaryProcessor());
        return processors;
    }
}
