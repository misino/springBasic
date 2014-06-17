package com.misino.template.bootstrap.elements;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.element.AbstractMarkupSubstitutionElementProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InputFieldProcessor extends AbstractMarkupSubstitutionElementProcessor
{
    public InputFieldProcessor() {
        super( "input" );
    }

    @Override
    protected List<Node> getMarkupSubstitutes( Arguments arguments, Element element ) {

        final Map<String, Attribute> attributeMap = element.getAttributeMap();
        final Element container = new Element( "div" );
        final Element inputContainer = new Element( "div" );
        final Element input;

        container.setAttribute( "class", "form-group" );

        Integer nextId = arguments.getAndIncrementIDSeq( "inputForm" );
        String type, text, field;
        if ( attributeMap.containsKey( "type" ) )
        {
            type = attributeMap.get( "type" ).getValue();
        } else
        {
            type = "text";
        }

        if ( attributeMap.containsKey( "label" ) )
        {
            text = attributeMap.get( "label" ).getValue();
        } else
        {
            text = "";
        }

        if ( type.equals( "submit" ) )
        {
            input = new Element( "button" );
            inputContainer.setAttribute( "class", "col-sm-8 col-sm-offset-2" );
            input.setAttribute( "th:text", text );
            input.setAttribute( "type", type );
            input.setAttribute("class", "btn btn-primary");
            container.addChild( inputContainer );
        } else
        {

            input = new Element( "input" );
            final Element label = new Element( "label" );
            final Element errorContainer = new Element( "div" );
            final Element error = new Element( "div" );

            label.setAttribute( "class", "col-sm-2 control-label" );
            label.setAttribute( "for", "inputForm" + nextId );
            if ( !text.isEmpty() )
            {
                label.setAttribute( "th:text", text );
            }
            container.addChild( label );
            inputContainer.setAttribute( "class", "col-sm-10" );
            errorContainer.setAttribute( "class", "col-sm-offset-2 col-sm-10" );
            errorContainer.addChild( error );
            if ( attributeMap.containsKey( "field" ) )
            {
                field = attributeMap.get( "field" ).getValue();
                container.setAttribute( "th:classappend","${#fields.hasErrors('"+field+"')}? 'has-error'" );
                input.setAttribute( "th:field", "*{" + field + "}" );
                error.setAttribute( "th:if", "${#fields.hasErrors('" + field + "')}" );
                error.setAttribute( "th:errors", "*{" + field + "}" );
            }

            input.setAttribute( "id", "inputForm" + nextId );
            input.setAttribute("class", "form-control");

            if ( attributeMap.containsKey( "placeholder" ) )
            {
                input.setAttribute( "placeholder", attributeMap.get( "placeholder" ).getValue() );
            }
            container.addChild( inputContainer );
            container.addChild( errorContainer );
        }

        input.setAttribute( "type", type );

        inputContainer.addChild( input );
        container.addChild( inputContainer );

        List<Node> nodes = new ArrayList<Node>();
        nodes.add( container );
        return nodes;
    }

    @Override
    public int getPrecedence() {
        return 0;
    }
}
