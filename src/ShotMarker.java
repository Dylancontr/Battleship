import java.io.Serializable;

public class ShotMarker implements IEntity, Serializable{
    
    boolean type;//true = hit, false = miss

    public ShotMarker(boolean t){
        type = t;
    }
    
    public boolean getType(){
        return type;
    }
}
