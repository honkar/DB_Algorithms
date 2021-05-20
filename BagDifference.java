import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
One pass algorithm implementation for Bag Difference R-S, giving two relations R(X,Y,Z) and S(X,Y,Z)
Assumption: S is the smaller relation and the size of S is less than memory [B(R) > M > B(S)]
*/
public class BagDifference {

	public static void main(String[] args) {
		
		int block_size = 3;
		
		//STEP 1: Representation of R and S on Disk
		
		//Represents Relation S on disk which consists of 3 blocks
		ArrayList<List<List<Integer>>> s_disk = new ArrayList<List<List<Integer>>>(3);
		
		//Represents blocks of S - s1, s2, s3
		ArrayList<List<Integer>> s1 = new ArrayList<List<Integer>>(3);
	    ArrayList<List<Integer>> s2 = new ArrayList<List<Integer>>(3);
	    ArrayList<List<Integer>> s3 = new ArrayList<List<Integer>>(3);
	    
	    s1.add(Arrays.asList(2,2,2));
	    s1.add(Arrays.asList(2,2,2));
	    s1.add(Arrays.asList(3,3,3));
	    
	    s2.add(Arrays.asList(1,1,1));
	    s2.add(Arrays.asList(2,2,2));
	    s2.add(Arrays.asList(1,1,1));
	    
	    s3.add(Arrays.asList(4,4,4));
	    s3.add(Arrays.asList(2,2,2));
	    s3.add(Arrays.asList(9,9,9));
	    
	    s_disk.addAll(Arrays.asList(s1,s2,s3));
	    
	    
	    //Represents Relation R on disk which consists of 4 blocks
	  	ArrayList<List<List<Integer>>> r_disk = new ArrayList<List<List<Integer>>>(4);
	  		
	  	//Represents blocks of R - r1,r2,r3,r4
	  	ArrayList<List<Integer>> r1 = new ArrayList<List<Integer>>(3);
  	    ArrayList<List<Integer>> r2 = new ArrayList<List<Integer>>(3);
  	    ArrayList<List<Integer>> r3 = new ArrayList<List<Integer>>(3);
  	    ArrayList<List<Integer>> r4 = new ArrayList<List<Integer>>(3);	
	  	    
  	    r1.add(Arrays.asList(1,2,3));
	    r1.add(Arrays.asList(2,2,2));
	    r1.add(Arrays.asList(2,4,8));
	    
	    r2.add(Arrays.asList(2,2,2));
	    r2.add(Arrays.asList(2,2,2));
	    r2.add(Arrays.asList(1,1,1));
	    
	    r3.add(Arrays.asList(5,5,5));
	    r3.add(Arrays.asList(2,2,2));
	    r3.add(Arrays.asList(1,1,1));
	    
	    r4.add(Arrays.asList(5,5,5));
	    r4.add(Arrays.asList(1,1,1));
	    r4.add(Arrays.asList(3,3,3));
	    
	    r_disk.addAll(Arrays.asList(r1,r2,r3,r4));
	    
	    System.out.println("------------------------------- STEP 1: R and S on Disk ------------------------------------");
	    System.out.println();
	    System.out.println("R ---> "+ r_disk);
	    System.out.println();
	    System.out.println("S ---> "+ s_disk);
	    System.out.println();
	    
	    System.out.println("---------------------------- STEP 2: Loading S into Memory -----------------------");
	    
	    // Memory Allocation for R
    	ArrayList<List<Integer>> r_mem= new ArrayList<>(3);
    	
	    // Memory Allocation for S - where search key -> tuple and count of occurrences -> value
        Map<List<Integer>, Long> s_mem = new HashMap<>();
        
        // Memory Allocation for output buffer
        ArrayList<List<Integer>> out_buffer= new ArrayList<>(3);
        
        // Represents Disk space for final results
        ArrayList<List<Integer>> disk = new ArrayList<List<Integer>>();
        
        System.out.println();
        System.out.println("Loading S into Memory (block by block)");
        System.out.println();
        
        for (int i = 0; i < s_disk.size(); i++) 
        {
           Map<List<Integer>, Long> key_count = s_disk.get(i).stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
           key_count.forEach((k, v) -> s_mem.merge(k, v, Long::sum));
           System.out.println("After Block "+(i+1)+":"+ s_mem);
        }
        System.out.println();
        
        System.out.println("------------------------- STEP 3: Loading R into Memory (block by block) and comparing each tuple in R with S -------------------");
        System.out.println();
        for (int i = 0; i < r_disk.size(); i++) 
        {
        	//load one block from disk to memory
        	r_mem.addAll(r_disk.get(i));
        	System.out.println("After loading Block_"+ (i+1) + " of R into memory");
        	System.out.println(r_mem);
        	System.out.println();
        	
        	//for each tuple in R, 
        	// if tuple not present in S or count == 0 then add the tuple to output buffer
        	// else decrease the count by 1 for that tuple in S and skip the tuple
        	r_mem.forEach(tuple ->
        	{
        		// first check if output buffer full, if yes dump the contents to disk
        		if(out_buffer.size() == 3) {
        			System.out.println("Output buffer and Disk Contents: BEFORE TRANSFER");
        			System.out.println();
        			System.out.println("Output buffer: "+ out_buffer);
        			System.out.println("Disk: "+ disk);
        			System.out.println();
        			
        			disk.addAll(out_buffer);
        			out_buffer.clear();
        			
        			System.out.println("Output buffer and Disk Contents: AFTER TRANSFER");
        			System.out.println();
        			System.out.println("Output buffer: "+ out_buffer);
        			System.out.println("Disk: "+ disk);
        			System.out.println();
        		}
        		// if the tuple is not in S, add the tuple to output buffer
        		if(!s_mem.containsKey(tuple)) {
        			
        			 out_buffer.add(tuple);
        		}
        		// else if the tuple is in S
        		else {
        			
        			Long count = s_mem.get(tuple);
        			
           		 // if count <= 0 add tuple to output buffer
           		 if (count <= 0) {
           			 out_buffer.add(tuple);
           		 }
           		 // else if count > 0 , then decrease the count to count-1
           		 else {
           			 s_mem.replace(tuple, count-1);
           		 }
        	   }
        	});
        	//after processing a block of R, clear r_mem to load next block
        	r_mem.clear();
        } 
        // add any remaining tuples 
        System.out.println("------------------------- STEP 4: Transferring any Remaining tuples in buffer to disk -------------------");
        System.out.println();
        System.out.println("Remaining tuples at the end: BEFORE TRANSFER");
		System.out.println();
		System.out.println("Output buffer: "+ out_buffer);
		System.out.println("Disk: "+ disk);
		System.out.println();
		
        disk.addAll(out_buffer);
        out_buffer.clear();
        
        System.out.println("Remaining tuples at the end: AFTER TRANSFER");
		System.out.println();
		System.out.println("Output buffer: "+ out_buffer);
		System.out.println("Disk: "+ disk);
		System.out.println();
		
		System.out.println("------------------------ STEP 5: Final Disk Contents -------------------");
        System.out.println();
        System.out.println("");
        System.out.println("Final Result on Disk: "+ disk);
        System.out.println("");
	}
}
