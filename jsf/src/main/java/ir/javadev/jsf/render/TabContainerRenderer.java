package ir.javadev.jsf.render;

import ir.javadev.jsf.Util.RendererUtil;
import ir.javadev.jsf.component.UITab;
import ir.javadev.jsf.component.UITabContainer;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

@FacesRenderer(
        componentFamily = "ir.javadev.jsf.component",
        rendererType = "ir.javadev.jsf.renderer.TabContainerRenderer"
)
public class TabContainerRenderer extends Renderer {
    public TabContainerRenderer() {
    }

    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        RendererUtil.isNull(context, component);
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.startElement("ul", component);
            writer.writeAttribute("class", "nav nav-tabs", (String) null);
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        RendererUtil.isNull(context, component);
        if (component.isRendered()) {
            UITabContainer uiTabContainer = (UITabContainer) component;
            Object srcIdMap = uiTabContainer.getSrcMap();
            if (srcIdMap == null) {
                srcIdMap = new HashMap();
                uiTabContainer.setSrcMap((Map) srcIdMap);
            }

            ResponseWriter writer = context.getResponseWriter();
            boolean checkActivation = true;
            Iterator tab = component.getChildren().iterator();

            while (tab.hasNext()) {
                UIComponent uiComponent = (UIComponent) tab.next();
                if (uiComponent instanceof UITab) {
                    UITab uiTab = (UITab) uiComponent;
                    writer.startElement("li", uiTab);
                    UUID uuid = UUID.randomUUID();
                    if (checkActivation && uiTab.isActive().booleanValue()) {
                        checkActivation = false;
                        writer.writeAttribute("class", "active", (String) null);
                        uiTabContainer.setActiveId(uuid.toString());
                    }

                    writer.startElement("a", uiTab);
                    HashMap urlParameters = new HashMap();
                    Map parameters = RendererUtil.getParameterTagValue(uiComponent);
                    urlParameters.put(uiTab.getSrc(), parameters);
                    ((Map) srcIdMap).put(uuid.toString(), urlParameters);
                    writer.writeAttribute("href", "#" + uuid.toString(), (String) null);
                    writer.writeAttribute("data-toggle", "tab", (String) null);
                    writer.write(uiTab.getText());
                    writer.endElement("a");
                    writer.endElement("li");
                }
            }

        }
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        RendererUtil.isNull(context, component);
        if (component.isRendered()) {
            ResponseWriter writer = context.getResponseWriter();
            writer.endElement("ul");
            UITabContainer uiTabContainer = (UITabContainer) component;
            Map srcIdMap = uiTabContainer.getSrcMap();
            Set entries = srcIdMap.entrySet();
            StringBuilder scripts = new StringBuilder();
            if (entries.size() > 0) {
                writer.startElement("div", component);
                writer.writeAttribute("class", "tab-content", (String) null);
                scripts.append("<script type=\'text/javascript\'>$(function() {");
            }

            Iterator var8 = entries.iterator();

            while (var8.hasNext()) {
                Entry mapEntry = (Entry) var8.next();
                Set urlParameters = ((Map) mapEntry.getValue()).entrySet();
                Entry urlParameterEntry = (Entry) urlParameters.iterator().next();
                NavigationCase navigationCase = ((ConfigurableNavigationHandler) context.getApplication().getNavigationHandler()).getNavigationCase(context, (String) null, (String) urlParameterEntry.getKey());
                String toViewId = navigationCase.getToViewId(context);
                String uri = context.getApplication().getViewHandler().getBookmarkableURL(context, toViewId, (Map) null, false);
                StringBuilder urlParametersString = new StringBuilder();

                Entry parameter;
                for (Iterator absoluteUrl = ((Map) urlParameterEntry.getValue()).entrySet().iterator(); absoluteUrl.hasNext(); urlParametersString.append((String) parameter.getKey()).append("=").append((String) parameter.getValue())) {
                    parameter = (Entry) absoluteUrl.next();
                    if (urlParametersString.length() > 0) {
                        urlParametersString.append("&");
                    }
                }

                String absoluteUrl1 = ((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURL().toString().replace(((HttpServletRequest) context.getExternalContext().getRequest()).getRequestURI(), "") + uri + (urlParametersString.length() > 0 ? "?" + urlParametersString.toString() : "");
                scripts.append("ajaxRequest(\'" + absoluteUrl1 + "\',\'" + (String) mapEntry.getKey() + "\');");
                writer.startElement("div", component);
                writer.writeAttribute("id", mapEntry.getKey(), (String) null);
                writer.writeAttribute("class", "tab-pane fade in" + (((String) mapEntry.getKey()).equals(uiTabContainer.getActiveId()) ? " active" : ""), (String) null);
                writer.endElement("div");
            }

            uiTabContainer.setSrcMap((Map) null);
            if (entries.size() > 0) {
                writer.endElement("div");
                scripts.append("});</script>");
                writer.append(scripts);
            }

        }
    }
}
