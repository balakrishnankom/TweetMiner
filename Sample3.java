import java.io.*;
public class Sample3 {
     
    public static void main(String a[]){
     
        String str = "This is an example string:)";
        System.out.println("Replace char 's' with 'o':"
                    +str.replace('s', 'o'));
                     
        System.out.println("Replace first occurance of string\"is\" with \"ui\":"
                    +str.replaceFirst("is", "ui"));
                     
        System.out.println("Replacing :"+str.replaceAll("[^:):(]"," "));
    }
}