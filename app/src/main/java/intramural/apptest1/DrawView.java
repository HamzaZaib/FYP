package intramural.apptest1;

/**
 * Created by hamza on 11/10/2015.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {
    Paint paint = new Paint();
    enum Side {North,South,East,West};
    Side current=Side.North,previous=Side.North;
    int x=450;int y=800;
    public DrawView(Context context) {
        super(context);
        paint.setColor(Color.WHITE);
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(previous!=current){
            x=450;y=800;
            previous=current;
        }
        switch(current) {
            case North:
                y=y+20;
                canvas.drawLine(x, y, 450, 800, paint);
             break;
            case South:
                y=y-20;
                canvas.drawLine(x, y, 450, 800, paint);
                break;
            case East:
                x=x+20;
                canvas.drawLine(x, y, 450, 800, paint);
                break;
            case West:
                x=x-20;
                canvas.drawLine(x, y, 450, 800, paint);
                break;
        }
    }

}