package fr.unice.polytech.mundus.protocol;

import java.io.Serializable;

/**
 * @author TANG Yi
 */
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;
    private Command cmd;//
    private Object detail;//data to send

    public void setCmd(Command cmd) {
        this.cmd = cmd;
    }

    public Command getCmd() {
        return cmd;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public Object getDetail() {
        return detail;
    }

    public enum Command{
        SIGN_UP, CONSULT, CHANGE;
    }
    /*

    */
}
