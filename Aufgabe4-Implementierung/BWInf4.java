import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class BWInf4 {

    public static void main(String[] args) throws IOException {
        System.out.println("If you want to see one of the examples please type ex, if you want to see another case, please type own.");
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        System.out.println("");
	Rectangles frec = null;	
        if (s.equals("ex")){
            System.out.println("Please choose which example you want to see (1, 2 or 3).");
            s = sc.nextLine();
            System.out.println("");
            if (s.equals("1")){
                frec = new Rectangles(4,new Rectangle(25,15),new Rectangle(15,30),new Rectangle(15,25),new Rectangle(25,20));
            } else if (s.equals("2")){
                frec = new Rectangles(5,new Rectangle(6,3),new Rectangle(2,2),new Rectangle(3,1),new Rectangle(4,4),new Rectangle(4,4));
            } else if (s.equals("3")){
                frec = new Rectangles(5,new Rectangle(4,4),new Rectangle(2,3),new Rectangle(6,1), new Rectangle(5,2),new Rectangle(3,5));
            }
        } else if (s.equals("own")){
            System.out.println("Please choose how many rectangles you want.");
            int x = sc.nextInt();
            System.out.println("");
            Rectangle[] rec = new Rectangle[x];
            for (int a = x; a>0; a--){
                System.out.print("Please choose the width of rectangle ");
                System.out.println(x + 1 - a);
                int width = sc.nextInt();
                System.out.print("Please choose the height of rectangle ");
                System.out.println(x + 1 - a);
                int height = sc.nextInt();
                System.out.println("");
              	rec[a-1] = new Rectangle(width,height);
            }
	   frec = new Rectangles(x,rec);
        }
        frec.sort();
        frec.solve();
        System.out.print("The area that the enclosing Rectangle covers  is ");
        System.out.print(frec.area);
        System.out.print(" (");
        System.out.print(frec.x);
        System.out.print("x");
        System.out.print(frec.y);
        System.out.println(")");
        
        String fileContent = Integer.toString(frec.x) + "," + Integer.toString(frec.y) + ",\n";
        for (Rectangle rectangle: frec.rectangles){
            fileContent = fileContent +  Integer.toString(rectangle.x) + "," + Integer.toString(rectangle.y) + "," + Integer.toString(rectangle.width) + "," + Integer.toString(rectangle.height) + ",\n";
            }
        
        try(FileWriter fileWriter = new FileWriter("coordinates.txt")) {
            fileWriter.write(fileContent);
        }
    }
}
