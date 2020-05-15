import java.io.*;
public class Sam
{
	public static void main(String args[])throws IOException{
		for(int row=1;row<=5;row++)
		{
			for(int data=1;data<=5;data++)
			{
				if(data%2==1)
					System.out.print(row);
				else
					
					System.out.print("*");
			}
			System.out.println("");
		}
	}
}