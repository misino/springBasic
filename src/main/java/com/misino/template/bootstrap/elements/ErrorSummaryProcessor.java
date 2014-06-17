package com.misino.template.bootstrap.elements;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by misino on 12/29/13.
 */
public class ErrorSummaryProcessor extends AbstractMarkupSubstitutionElementProcessor
{
    public ErrorSummaryProcessor() {
        super("errorsummary");
    }

    @Override
    protected List<Node> getMarkupSubstitutes( Arguments arguments, Element element ) {

        final Map<String, Attribute> attributeMap = element.getAttributeMap();
        final Element container = new Element("div");
        final Element list = new Element("ul");
        final Element item = new Element("li");
        container.setAttribute("class", "alert alert-danger");
        container.setAttribute( "th:if", "${#fields.hasGlobalErrors()}" );
        item.setAttribute("th:each", "err : ${#fields.globalErrors()}");
        item.setAttribute( "th:text", "${err}" );

        list.addChild( item );
        container.addChild( list );


        List<Node> nodes = new ArrayList<Node>();
        nodes.add( container );
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
