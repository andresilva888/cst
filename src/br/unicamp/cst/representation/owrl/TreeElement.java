/*******************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors to this module:
 *     S. M. de Paula and R. R. Gudwin 
 ******************************************************************************/

package br.unicamp.cst.representation.owrl;

import java.awt.Color;

/**
 * @author Suelen Mapa
 */
public class TreeElement {

    private String name;
    private Color color;
    private Object element;
    private int icon_type;
    public static final int NODE_NORMAL = 1;
    public static final int NODE_CHANGE = 2;
    public static final int NODE_EXCLUSION = 3;
    public static final int NODE_CREATION = 4;

    public static final int ICON_CONFIGURATION = 1;
    public static final int ICON_OBJECT = 2;
    public static final int ICON_PROPERTY = 3;
    public static final int ICON_QUALITYDIM = 4;
    public static final int ICON_VALUE = 5;
    public static final int ICON_MIND = 6;
    public static final int ICON_CODELET = 7;
    public static final int ICON_CODELETS = 8;
    public static final int ICON_MEMORY = 9;
    public static final int ICON_MEMORIES = 10;
    public static final int ICON_CONTAINER = 11;
    public static final int ICON_MO = 12;
    public static final int ICON_INPUT = 13;
    public static final int ICON_OUTPUT = 14;
    public static final int ICON_BROADCAST = 15;

    public TreeElement(String name, int node_type, Object element, int typeIcon) {
        setName(name);
        setColor(node_type);
        setIcon(typeIcon);
        this.element = element;
        this.icon_type = typeIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int node_type) {
        switch (node_type) {
            case NODE_NORMAL:
                color = Color.BLACK;
                break;
            case NODE_CHANGE:
                color = Color.ORANGE;
                break;
            case NODE_EXCLUSION:
                color = Color.RED;
                break;
            case NODE_CREATION:
                color = Color.GREEN;
                break;

        }
    }

    public void setIcon(int icon_type) {
        this.icon_type = icon_type;
    }

    public int getIcon() {
        return icon_type;
    }

    public Object getElement() {
        return element;
    }
}
