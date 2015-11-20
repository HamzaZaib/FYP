package intramural.apptest1;

/**
 * Created by hamza on 11/15/2015.
 */
public class Line {
    float x,x1,y,y1;
    Line(float x, float y){
        this.x=x;
        this.y=y;
        this.x1=x;
        this.y1=y;
    }
    Line(float x, float x1, float y, float y1){
        this.x=x;
        this.y=y;
        this.x1=x1;
        this.y1=y1;
    }

    boolean update(float x1, float y1){
        this.x1=x1;
        this.y1=y1;
        return true;
    }
}
