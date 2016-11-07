package com.dpi.ui.core;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * The most important points are:
 * <p>
 * 1. Define componentType, componentFamily and rendererType adding its
 * constants (like below) or directly in the annotation.
 * <p>
 * 2. If the component has a jsp tag, define the "name" and the "tagClass"
 * If the tagClass does not exists a new file is generated if make-tags
 * goal is set on pom.xml
 */
@FacesComponent
public class SayHello extends UIOutput {

    @Override
    public String getFamily() {
        // return super.getFamily();
        return "my.custom.component";
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        // super.encodeBegin(context);
        String value = (String) getAttributes().get("value");

        if (value != null) {
            ResponseWriter writer = context.getResponseWriter();
            writer.write("Hello :" + value.toUpperCase());
        }
    }
}
