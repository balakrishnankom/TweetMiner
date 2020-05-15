//Advance problem accepted by PCT
import java.util.PriorityQueue;
import java.util.Scanner;

class Tree {
	int value = -1;
//	Tree root;
	Tree left;
	Tree right;
	PriorityQueue p = new PriorityQueue();

	void insert(Tree root, int val)
  { if(root.value ==-1)
     { Tree t = new Tree();
       root.left = null;
       root.right = null;
 	   root.value = val;
 	   
 	   
 //	   this.root = t;
     }
    else if(root.value >= val)
    {  if(root.left ==null)
       { Tree t = new Tree();
         t.left = null;
         t.right = null;
	     t.value = val;
	     root.left = t;     //insert it to the left f the parent
       } 
      else
      {insert(root.left,val);
      }
    
    }    	
    else if(root.value < val)
    { if(root.right ==null)
       {  Tree t = new Tree();
          t.left = null;
          t.right = null;
	      t.value = val;
	      root.right = t;        //insert it to the right f the parent
       }
      else
      {insert(root.right,val);
      }
    }    
  }
	
	void traverse(Tree r)
	{ 
		if(r ==null)
		return;	
		
	/*  System.out.println(r.value); 
	  traverse(r.left);
	     traverse(r.right);
	*/  
	  if(r.left ==null)
   	   { if(r.right ==null)
   	      {return;
   	      }
   	     else
   	     {//System.out.println(r.right.value);
   	      p.add(r.right.value);
   	      traverse(r.right);
   	     }
		
   	   } 
	  else if(r.right ==null)
	   { if(r.left ==null)
	      {return;
	      }
	     else
	     {//System.out.println(r.left.value);
	      p.add(r.left.value);
	   	    
	     traverse(r.left);
	     }   
	   }
	   else
	   { traverse(r.left);
	     traverse(r.right);
	   }
	   
	}
	
	void print()
	{
		if(p.isEmpty())
			System.out.println("NO");
		else
			System.out.print(p.poll());
			
		while(!p.isEmpty())
		  {System.out.print(" "+p.poll());
		  }
		System.out.println();	
	}

}

public class prob2 {

	public static void main(String str[])
	{
     Scanner s = new Scanner(System.in);
		
	  int n=Integer.parseInt(s.nextLine());
       Tree t= new Tree();
	  for(int i=0;i<n;i++)
	  { int a= s.nextInt();
	//  System.out.println("insert:"+a);
	    t.insert(t,a);
		  
	  } 
	  
	//  System.out.println("insert successfull");
	  t.traverse(t);
	  t.print();
	  
	}

}