package formatfa.craw.andcrawclient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import formatfa.craw.client.Connection;
import formatfa.craw.protocol.Request;
import formatfa.craw.protocol.Response;

public class LayoutView extends View {

//    private Connection connection;

    Paint paint;
    private int fontSize=18;
    private JSONObject layouts;

    public JSONObject getLayouts() {
        return layouts;
    }

    public void setLayouts(JSONObject layouts) {
        this.layouts = layouts;
    }

    public LayoutView(Context context) {
        super(context);
//        this.connection = connection;
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(fontSize);
        paint.setStyle(Paint.Style.STROKE);
    }



    @Override
    protected void onDraw(Canvas canvas) {

        try {

            drawRecursize(canvas,layouts);
//            System.out.println("layout:"+layouts);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }



    }
    private Rect rect=new Rect();

    private void drawRecursize(Canvas canvas, JSONObject node) {

        if(node==null)return;
        int left = node.getIntValue("left");
        int right = node.getIntValue("right");
        int top = node.getIntValue("top");
        int bottom = node.getIntValue("bottom");
        rect.left=left;
        rect.right=right;
        rect.top=top;
        rect.bottom=bottom;

        canvas.drawRect(rect,paint);
        //绘制文字
        if(node.containsKey("text"))
        {
            canvas.drawText(node.getString("text"), left, top+fontSize,paint);
        }
        JSONArray children = node.getJSONArray("children");
        for(int i=0;i<children.size();i+=1)
        {
            drawRecursize(canvas,children.getJSONObject(i));
        }

    }



}
