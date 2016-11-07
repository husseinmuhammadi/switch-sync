package ir.javadev.jsf.Util;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class RendererUtil {
    public RendererUtil() {
    }

    public static void isNull(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
    }

    public static JsonObjectBuilder jsonParameterParser(String json, FacesContext context) {
        StringReader stringReader = new StringReader(json);
        JsonParser jsonParser = Json.createParser(stringReader);
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();

        while (true) {
            Event event;
            do {
                if (!jsonParser.hasNext()) {
                    return jsonObjectBuilder;
                }

                event = jsonParser.next();
            } while (!event.equals(Event.START_OBJECT));

            String key = null;

            while (jsonParser.hasNext() && !(event = jsonParser.next()).equals(Event.END_OBJECT)) {
                if (event.equals(Event.KEY_NAME)) {
                    key = jsonParser.getString();
                } else if (event.equals(Event.VALUE_STRING)) {
                    String value = jsonParser.getString();
                    if (value.startsWith("#")) {
                        value = (String) context.getApplication().evaluateExpressionGet(context, value, String.class);
                    }

                    jsonObjectBuilder.add(key, value);
                }
            }
        }
    }

    public static void encodeRecursive(FacesContext context, UIComponent component) throws IOException {
        if (component.isRendered()) {
            component.encodeBegin(context);
            if (component.getRendersChildren()) {
                component.encodeChildren(context);
            } else {
                Iterator kids = getChildren(component);

                while (kids.hasNext()) {
                    UIComponent kid = (UIComponent) kids.next();
                    encodeRecursive(context, kid);
                }
            }

            component.encodeEnd(context);
        }
    }

    public static Iterator<UIComponent> getChildren(UIComponent component) {
        int childCount = component.getChildCount();
        return childCount > 0 ? component.getChildren().iterator() : Collections.<UIComponent>emptyList().iterator();
    }

    public static Map<String, String> getParameterTagValue(UIComponent uiComponent) {
        HashMap parameters = new HashMap();
        Iterator var2 = uiComponent.getChildren().iterator();

        while (var2.hasNext()) {
            UIComponent child = (UIComponent) var2.next();
            if (child instanceof UIParameter) {
                UIParameter uiParameter = (UIParameter) child;
                if (!uiParameter.isDisable() && uiParameter.getValue() != null) {
                    parameters.put(uiParameter.getName(), uiParameter.getValue().toString());
                }
            }
        }

        return parameters;
    }
}
