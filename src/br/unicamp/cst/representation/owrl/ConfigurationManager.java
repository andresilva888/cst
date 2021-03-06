/** *****************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * Contributors to this module:
 *     S. M. de Paula and R. R. Gudwin
 ***************************************************************************** */

package br.unicamp.cst.representation.owrl;

import br.unicamp.cst.util.Pair;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * @author Suelen Mapa
 */
public class ConfigurationManager {

    private List<Pair<String, Configuration>> listConfs;
    private int currentConfiguration = -1;

    public ConfigurationManager() {
        listConfs = new ArrayList<Pair<String, Configuration>>();
    }

    private void create(WorldObject obj) {
        listConfs.get(currentConfiguration).second.addObject(obj);
    }

    private boolean destroy(WorldObject obj, List<WorldObject> list) {
        for (WorldObject obj_search : list) {
            if (obj_search.getID() == obj.getID()) {
                list.remove(obj_search);
                return true;
            } else {
                if (destroy(obj, obj_search.getParts())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean applyCommands(List<Pair<String, List<WorldObject>>> commands) {
        newConfiguration(String.format("%04d", listConfs.size() + 1));
        for (Pair<String, List<WorldObject>> entry : commands) {
            switch (entry.first) {
                case "create":
                    for (WorldObject obj : entry.second) {
                        create(obj);
                    }
                    break;
                case "destroy":
                    for (WorldObject obj : entry.second) {
                        if (!(destroy(obj, listConfs.get(currentConfiguration).second.getObjects()))) {
                            String message = "Unknown object: " + obj.getID();
                            JOptionPane.showMessageDialog(null, message);
                            System.err.println(message);
                        }
                    }
                    break;
                case "modify":
                    for (WorldObject obj : entry.second) {
                        if (!destroy(obj, listConfs.get(currentConfiguration).second.getObjects())) {
                            String message = "Unknown object: " + obj.getID();
                            JOptionPane.showMessageDialog(null, message);
                            System.err.println(message);
                        } else {
                            create(obj);
                        }
                    }
                    break;
                default:
                    System.out.println("Unknown command: " + entry.first);
                    return false;
            }
            for (WorldObject wo : entry.second) {
                System.out.println(wo.getID() + ", " + wo.getName());
            }
        }
        if (listConfs.size() == 1 && listConfs.get(0).second.getObjects().isEmpty()) {
            removeConfiguration(0);
        }
        return true;
    }

    public boolean selectConfiguration(String name) {
        for (int i = 0; i < listConfs.size(); ++i) {
            if (listConfs.get(i).first.compareTo(name) == 0) {
                currentConfiguration = i;
                return true;
            }
        }
        return false;
    }

    public void newConfiguration(String name) {
        if (listConfs.isEmpty()) {
            listConfs.add(new Pair(name, new Configuration()));
        } else {
            listConfs.add(new Pair(name, listConfs.get(listConfs.size() - 1).second.clone()));
        }
        currentConfiguration = listConfs.size() - 1;
    }

    public void removeConfiguration(int index) {
        listConfs.remove(index);
    }

    public int size() {
        return listConfs.size();
    }

    public Configuration getConfiguration(int index) {
        return listConfs.get(index).second;
    }
}
