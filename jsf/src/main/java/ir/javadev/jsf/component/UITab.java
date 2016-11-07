package ir.javadev.jsf.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;

@FacesComponent("ir.javadev.jsf.component.Tab")
public class UITab extends UIOutput {
    public static final String FAMILY = "ir.javadev.jsf.component";
    public static final String RENDERER_TYPE = null;

    public UITab() {
    }

    public String getRendererType() {
        return RENDERER_TYPE;
    }

    public String getFamily() {
        return FAMILY;
    }

    public Boolean isActive() {
        return (Boolean)this.getStateHelper().eval(UITab.PropertyKeys.active);
    }

    public void setActive(Boolean active) {
        this.getStateHelper().put(UITab.PropertyKeys.active, active);
    }

    public String getSrc() {
        return (String)this.getStateHelper().eval(UITab.PropertyKeys.src);
    }

    public void setSrc(String src) {
        this.getStateHelper().put(UITab.PropertyKeys.src, src);
    }

    public String getText() {
        return (String)this.getStateHelper().eval(UITab.PropertyKeys.text);
    }

    public void setText(String text) {
        this.getStateHelper().put(UITab.PropertyKeys.text, text);
    }

    static enum PropertyKeys {
        active,
        src,
        text;

        private PropertyKeys() {
        }
    }
}
