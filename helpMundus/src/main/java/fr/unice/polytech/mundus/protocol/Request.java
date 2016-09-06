package fr.unice.polytech.mundus.protocol;

import java.io.Serializable;

/**
 * Created by TY on 2016/8/26.
 */
public class Request implements Serializable {
    //class request  { id (Mundus; futurMundus; Responsable) , type (signUp,signIn,check,modification,delete;), object detail}

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
        signUp,consult,change;//check,modification,delete;
    }
    /*

    */
}
