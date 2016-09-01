package fr.unice.polytech.mundus.protocol;

import java.io.Serializable;

/**
 * @author TANG Yi .
 * { type(information; status of modificaition),boolean: flag,string:content}
 */
public class Response implements Serializable {

    private Type type;
    private boolean flag;//success or not according to the command
    private String content;//result: information

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isInformation(){
        if(Type.information.compareTo(type)==0)
            return true;
        else return false;
    }

    public enum Type{
        information,modificaition
    }
}
