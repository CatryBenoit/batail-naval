package model;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BattleBoard extends ContainerElement{

    public BattleBoard(int x, int y, GameStageModel gameStageModel, String name) {
            super(name, x, y, 10 , 10, gameStageModel);
        }


    public void setValidCells(int number) {
        Logger.debug("called",this);
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }


    public List<Point> computeValidCells(int n) {
        List<Point> lst = new ArrayList<>();
        Missille m = null;
        if (isEmpty()) {
            for(int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    lst.add(new Point(j, i));
                }
            }
            return lst;
        }

        for(int i = 0; i < 10; i++) {
            for(int j = 0; j < 10; j++) {
                if (isEmptyAt(i, j)) {
                    if (i - 1 >= 0) {
                        if (j - 1 >= 0) {
                            m = (Missille) getElement(i - 1, j - 1);
                            if (m != null && m.getColor() == n) {
                                lst.add(new Point(j, i));
                                continue;
                            }
                        }
                        m = (Missille) getElement(i - 1, j);
                        if (m != null && m.getColor() == n) {
                            lst.add(new Point(j, i));
                            continue;
                        }
                        if (j + 1 <= 2) {
                            m = (Missille) getElement(i - 1, j + 1);
                            if (m != null && m.getColor() == n) {
                                lst.add(new Point(j, i));
                                continue;
                            }
                        }
                    }
                    if (i + 1 <= 2) {
                        if (j - 1 >= 0) {
                            m = (Missille) getElement(i + 1, j - 1);
                            if (m != null && m.getColor() == n) {
                                lst.add(new Point(j, i));
                                continue;
                            }
                        }
                        m = (Missille) getElement(i + 1, j);
                        if (m != null && m.getColor() == n) {
                            lst.add(new Point(j, i));
                            continue;
                        }
                        if (j + 1 <= 2) {
                            m = (Missille) getElement(i + 1, j + 1);
                            if (m != null && m.getColor() == n) {
                                lst.add(new Point(j, i));
                                continue;
                            }
                        }
                    }
                    if (j - 1 >= 0) {
                        m = (Missille) getElement(i, j - 1);
                        if (m != null && m.getColor() == n) {
                            lst.add(new Point(j, i));
                            continue;
                        }
                    }
                    if (j + 1 <= 2) {
                        m = (Missille) getElement(i, j + 1);
                        if (m != null && m.getColor() == n) {
                            lst.add(new Point(j, i));
                            continue;
                        }
                    }
                }
            }
        }
        return lst;
    }






    public BattleBoard getGet() {
        return this;
    }














}
