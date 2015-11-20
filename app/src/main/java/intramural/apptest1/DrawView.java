package intramural.apptest1;

/**
 * Created by hamza on 11/10/2015.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;

public class DrawView extends View {
    Paint paint = new Paint();
    public enum Side {North,South,East,West,SouthEast,SouthWest,NorthEast,NorthWest}
    Side current=Side.North,previous=Side.North;
    int size;
    //previousSteps=0,currentSteps=0;
    ArrayList<Line> lines;

    public DrawView(Context context) {
        super(context);
        lines = new ArrayList<Line>();
        size=0;
        lines.add(new Line(450,800));
        paint.setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        //int steps=currentSteps-previousSteps;
        //int val=steps*20,val1=steps*10;
        int val=20,val1=14;
        //previousSteps=currentSteps;
        size=lines.size()-1;
        Line last=lines.get(size);
        if(previous!=current){
            previous=current;
            lines.add(new Line(last.x1,last.y1));
            size++;
            last=lines.get(size);
        }
        switch(current) {
            case North:
                last.y1=last.y1-val;
             break;
            case South:
                last.y1=last.y1+val;
                break;
            case East:
                last.x1=last.x1+val;
                break;
            case West:
                last.x1=last.x1-val;
                break;
            case SouthEast:
                last.x1=last.x1+val1;
                last.y1=last.y1+val1;
                break;
            case SouthWest:
                last.x1=last.x1-val1;
                last.y1=last.y1+val1;
                break;
            case NorthEast:
                last.x1=last.x1+val1;
                last.y1=last.y1-val1;
                break;
            case NorthWest:
                last.x1=last.x1-val1;
                last.y1=last.y1-val1;
                break;
        }

        for(Line temp : lines){
            canvas.drawLine(temp.x,temp.y,temp.x1,temp.y1,paint);
        }
    }

}