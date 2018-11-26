public class Rectangles {

    public Rectangle[] rectangles;
    protected int[][] coordinates;
    public int x = 0;
    public int y;
    public int area;
    public int area_min;
    public int x_temp;
    public int y_temp;
    
    public Rectangles(int x, Rectangle... rects){
        rectangles = rects;
    }
    
    public void sort(){
        Rectangle rectangle;
        for (int b = 0; b<rectangles.length-1; b++){
            for (int a = 0; a<rectangles.length-1; a++){
                if (rectangles[a].height > rectangles[a+1].height){
                    rectangle = rectangles[a];
                    rectangles[a] = rectangles[a+1];
                    rectangles[a+1] = rectangle; 
                }
            }
        }
        y = rectangles[0].height;
        y_temp = y;
        for (int a = 0; a<rectangles.length-1; a++){
            x = x + rectangles[a].width;
            area_min = area_min + rectangles[a].area;
        }
        x_temp = x;
        area = x * y;
        
    }
    
    public void solve(){
        if (area_min == area){
            x = x_temp;
            y = y_temp;
        } else if (x_temp == 1) {
        }else {
            x_temp = x_temp-1;
            y_temp = area_min/x_temp;
            int z = 0;
            while (z== 0){
                if (x_temp*y_temp >= area){
                    solve();
                    z=1;
                } else {
                    if (x_temp * y_temp < area){
                        if (place(0)){
                            x= x_temp;
                            y= y_temp;
                            area = x*y;
                            coordinates = new int[rectangles.length][2];
                            int[] coordinate;
                            int t=0;
                            for (Rectangle rectangle: rectangles){
                                coordinate = new int[]{rectangle.x, rectangle.y};
                                coordinates[t]= coordinate;
                                t++;
                            } 
                        }
                    
                    }
                
                }
                y_temp = y_temp +1;
            }
        }
    }
    
    public Boolean place(int a){
        int x_min = area;
        int y_min = area;
        int x_min_temp = 0;
        int y_min_temp = 0;
        int z = 0;
        if (a != 0){
            for (int b= 0; b<a; b++){
                y_min_temp = rectangles[b].y + rectangles[b].height;
                if (y_min_temp + rectangles[a].height > y_temp){
                    y_min_temp = 0;
                } else if (!check(rectangles[b].x, y_min_temp, b)){
                    y_min_temp = 0;
                } else {
                    if (rectangles[b].x < x_min) {
                        x_min = x_min_temp;
                        y_min = y_min_temp;
                        z=1;
                    } else if (rectangles[b].x == x_min){
                        if (y_min_temp < y_min){
                            x_min = x_min_temp;
                            y_min = y_min_temp;
                            z=1;
                        } else {
                            y_min_temp = 0;
                        }
                    } else {
                        y_min_temp = 0;
                    }
                }
            }
            if (z==1){
                rectangles[a].x = x_min;
                rectangles[a].y = y_min;
            }
            for (int b= 0; b<a; b++){
                x_min_temp = rectangles[b].x + rectangles[b].width;
                if (x_min_temp + rectangles[a].width > x_temp){
                    x_min_temp = 0;
                } else if (!check(x_min_temp, rectangles[b].y, b)){
                    x_min_temp = 0;
                } else {
                    if (rectangles[b].y < y_min) {
                        x_min = x_min_temp;
                        y_min = y_min_temp;
                        z=1;
                    } else if (rectangles[b].y == y_min){
                        if (x_min_temp < x_min){
                            x_min = x_min_temp;
                            y_min = y_min_temp;
                            z=1;
                        } else {
                            x_min_temp = 0;
                        }
                    } else {
                        x_min_temp = 0;
                    }
                }
            }
            if (z==1){
                if (x_min < rectangles[a].x){
                    rectangles[a].x = x_min;
                    rectangles[a].y = y_min;
                    return place(a+1);
                }  else if (x_min == rectangles[a].x){
                    if (y_min < rectangles[a].y){
                        rectangles[a].x = x_min;
                        rectangles[a].y = y_min;
                        return place(a+1);
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            rectangles[a].x = 0;
            rectangles[a].y = 0;
            return place(a+1);
        }
    }
    
    public Boolean check (int x, int y, int b){
        for (int a = b; a<rectangles.length; a++){
            if (rectangles[a].x == x && rectangles[a].y == y){
                return false;
            }
        }
        return true;
    }
}
