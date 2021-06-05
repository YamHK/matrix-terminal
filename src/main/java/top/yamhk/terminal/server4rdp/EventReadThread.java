package top.yamhk.terminal.server4rdp;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;

public class EventReadThread extends Thread {

    private final ObjectInputStream objins;

    public EventReadThread(ObjectInputStream objins) {
        this.objins = objins;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Object eventobj = objins.readObject();
                InputEvent e = (InputEvent) eventobj;
                actionEvent(e);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void actionEvent(InputEvent e) {
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
        if (e instanceof KeyEvent) {
            KeyEvent ke = (KeyEvent) e;
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                robot.keyPress(ke.getKeyCode());
            }
            if (e.getID() == KeyEvent.KEY_RELEASED) {
                robot.keyRelease(ke.getKeyCode());
            }
        }
        if (e instanceof MouseEvent) {
            MouseEvent me = (MouseEvent) e;
            int type = me.getID();
            if (type == MouseEvent.MOUSE_PRESSED) {
                robot.mousePress(getMouseClick(me.getButton()));
            }
            if (type == MouseEvent.MOUSE_RELEASED) {
                robot.mouseRelease(getMouseClick(me.getButton()));
            }
            if (type == MouseEvent.MOUSE_MOVED) {
                robot.mouseMove(me.getX(), me.getY());
            }
            if (type == MouseEvent.MOUSE_DRAGGED) {
                robot.mouseMove(me.getX(), me.getY());
            }
            if (type == MouseEvent.MOUSE_WHEEL) {
                robot.mouseWheel(getMouseClick(me.getButton()));
            }
        }

    }

    private int getMouseClick(int button) {
        if (button == MouseEvent.BUTTON1) {
            return InputEvent.BUTTON1_MASK;
        }
        if (button == MouseEvent.BUTTON2) {
            return InputEvent.BUTTON2_MASK;
        }
        if (button == MouseEvent.BUTTON3) {
            return InputEvent.BUTTON3_MASK;
        }
        return -1;
    }
}
