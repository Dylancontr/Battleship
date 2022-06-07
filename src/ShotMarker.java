import java.io.Serializable;
/**
 * class represent Shotmarker which means if  cell hit o miss
 * @author Dylan, Evgeniya, Ivan
 *
 */
public class ShotMarker implements IMapEntity, Serializable{
    
    private boolean type;
/**
 * @param t - type of square hit or miss 
 */
    public ShotMarker(boolean t){
        type = t;
    }
    
/**
 * @return true if it is hit
 */
    public boolean getType(){
        return type;
    }
}