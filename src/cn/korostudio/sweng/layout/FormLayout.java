package cn.korostudio.sweng.layout;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public final class FormLayout implements LayoutManager2 {

    private final Map<Component, FormData> componentConstraints = new HashMap<Component, FormData>();

    public void addLayoutComponent(Component comp, Object constraints) {
        if (constraints == null) {
            throw new IllegalArgumentException("constraints can't be null");
        } else if (!(constraints instanceof FormData)) {
            throw new IllegalArgumentException("constraints must be a " + FormData.class.getName() + " instance");
        } else {
            synchronized (comp.getTreeLock()) {
                FormData formData = (FormData) constraints;
                if (formData.left == null || formData.top == null) {
                    throw new IllegalArgumentException("left FormAttachment and top FormAttachment can't be null");
                }
                componentConstraints.put(comp, (FormData) constraints);
            }
        }
    }

    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    /**
     * @deprecated
     */
    public void addLayoutComponent(String name, Component comp) {

    }

    public void layoutContainer(final Container parent) {
        synchronized (parent.getTreeLock()) {
            final int w = parent.getWidth();
            final int h = parent.getHeight();
            final Component[] components = parent.getComponents();
            for (final Component comp : components) {
                final FormData formData = componentConstraints.get(comp);
                if (formData == null) {
                    continue;
                }
                final FormAttachment left = formData.left;
                final FormAttachment right = formData.right;
                final FormAttachment top = formData.top;
                final FormAttachment bottom = formData.bottom;
                final int x = (int) (left.percentage * w) + left.offset;
                final int y = (int) (top.percentage * h) + top.offset;
                final int width;
                final int height;
                if (right == null || bottom == null) {
                    final Dimension size = comp.getPreferredSize();
                    if (size == null) {
                        throw new RuntimeException("If right FormAttachment or bottom FormAttachment is null,the component must have preferred-size");
                    } else {
                        width = size.width;
                        height = size.height;
                    }
                } else {
                    final int x2 = (int) (right.percentage * w) + right.offset;
                    final int y2 = (int) (bottom.percentage * h) + bottom.offset;
                    width = x2 - x;
                    height = y2 - y;
                }
                comp.setBounds(x, y, width, height);
            }
        }
    }

    public void invalidateLayout(Container target) {}

    public Dimension maximumLayoutSize(Container target) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    public Dimension minimumLayoutSize(Container target) {
        return new Dimension(0, 0);
    }

    public Dimension preferredLayoutSize(Container target) {
        return new Dimension(0, 0);
    }

    public void removeLayoutComponent(Component comp) {
        synchronized (comp.getTreeLock()) {
            componentConstraints.remove(comp);
        }
    }

}
