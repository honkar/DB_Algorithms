import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * Sort-Join algorithm used on two relations R & S where B(R) > B(S) > M
 * B(R) = 8
 * B(S) = 7
 * Memory size = 6
 * 
 * Conditions Satisfied for Sort-Join: B(R)+B(S) < M*M
 * 8 + 7 < 36
 *
 */
public class SortJoin {
	
/**
 * This method is used to store sorted sublists to disk for Relation R
 * @param r_disk
 * @param mem_size
 * @param block_size
 * @param numSublistR
 * @return ArrayList<List<List<R>>>, an arraylist of sorted sublists
 */
public static ArrayList<List<List<R>>> getSortedSublists_R(ArrayList<List<R>> r_disk, int mem_size, int block_size, int numSublistR){
		
		  // 2 blocks of memory
	    ArrayList<List<R>> memory = new ArrayList<List<R>>(mem_size);
	    
	    ArrayList<List<List<R>>> sublistR = new ArrayList<List<List<R>>>(numSublistR);

	  
	    int i =0, j= 0, k=0, m=0;
	    
	    while (i< r_disk.size())
	    {
	    	while (j < mem_size && i < r_disk.size()) {
	    		memory.add(j, r_disk.get(i));
	    		i=i+1; 
	    		j=j+1;
	    	}
	    	
	    	List<R> memlist = memory.stream().flatMap(obj -> obj.stream()).collect(Collectors.toList());
	    	// main memory sort
			Collections.sort(memlist, Comparator.comparingInt(R :: getY));
		    List<List<R>> list = new ArrayList<List<R>>();
	    	// save sublists in disk
	    	while(m <  memlist.size())
	    	{
	    		List<R> block = memlist.subList(m, m+block_size);
	    		list.add(block);
	    		m =m+3;
	    	}
	    	sublistR.add(k, list);
	    	k= k+1;
	    	
            memory.clear();
	    	j=0;
	    	m=0;
	    }
		return sublistR;
}
/**
 * This method is used to store sorted sublists to disk for Relation S
 * @param s_disk
 * @param mem_size
 * @param block_size
 * @param numSublistS
 * @return ArrayList<List<List<S>>>, an array list of sorted sublists
 */
public static ArrayList<List<List<S>>> getSortedSublists_S(ArrayList<List<S>> s_disk, int mem_size, int block_size, int numSublistS){
		
		  // 2 blocks of memory
	    ArrayList<List<S>> memory = new ArrayList<List<S>>(mem_size);
	    
	    ArrayList<List<List<S>>> sublistS = new ArrayList<List<List<S>>>(numSublistS);

	  
	    int i =0, j= 0, k=0, m=0;
	    
	    while (i< s_disk.size())
	    {
	    	while (j < mem_size && i < s_disk.size()) {
	    		memory.add(j, s_disk.get(i));
	    		i=i+1; 
	    		j=j+1;
	    	}
	    	
	    	List<S> memlist = memory.stream().flatMap(obj -> obj.stream()).collect(Collectors.toList());
	    	// main memory sort
			Collections.sort(memlist, Comparator.comparingInt(S :: getY));
		    List<List<S>> list = new ArrayList<List<S>>();
	    	// save sublists in disk
	    	while(m <  memlist.size())
	    	{
	    		List<S> block = memlist.subList(m, m+block_size);
	    		list.add(block);
	    		m =m+3;
	    	}
	    	sublistS.add(k, list);
	    	k= k+1;
	    	
          memory.clear();
	      j=0;
	      m=0;
	    }
		return sublistS;
}
	
/**
 * This method is used to load a block from sublist to corresponding memory buffer
 * @param mem
 * @param sublist
 * @return boolean
 */
public static boolean load_sublistToMem_R(List<List<R>> mem, List<List<R>> sublist) {
		
		if (!sublist.isEmpty()) {
			mem.add(sublist.get(0));
			sublist.remove(0);
			return true;
		}
		return false;
}
/**
 * This method is used to load a block from sublist to corresponding memory buffer
 * @param mem
 * @param sublist
 * @return boolean
 */	
public static boolean load_sublistToMem_S(List<List<S>> mem, List<List<S>> sublist) {
		
		if (!sublist.isEmpty()) {
			mem.add(sublist.get(0));
			sublist.remove(0);
			return true;
		}
		return false;
	}
/**
 * 	Main method - Sort-Join implementation
 *
 */
public static void main(String[] args) {
		
				int block_size = 3; // 3 tuples per block
				int mem_size = 6;  // no. of blocks in memory				
				
				//STEP 1: Representation of R and S on Disk
				
				//Represents Relation R on disk which consists of 8 blocks B(R) = 8
				ArrayList<List<R>> r_disk = new ArrayList<List<R>>(8);
				
				//Represents 8 blocks of R 
				ArrayList<R> r1 = new ArrayList<R>(3);
				ArrayList<R> r2 = new ArrayList<R>(3);
				ArrayList<R> r3 = new ArrayList<R>(3);
				ArrayList<R> r4 = new ArrayList<R>(3);
				ArrayList<R> r5 = new ArrayList<R>(3);
				ArrayList<R> r6 = new ArrayList<R>(3);
				ArrayList<R> r7 = new ArrayList<R>(3);
				ArrayList<R> r8 = new ArrayList<R>(3);
			   
			    
			    r1.addAll(Arrays.asList(
		                new R(1, 1, 3, 1),
		                new R(1, 1, 0, 1),
		                new R(1, 1, 3, 1)
		        ));
			    
			    r2.addAll(Arrays.asList(
		                new R(2, 2, 1, 2),
		                new R(2, 2, 1, 2),
		                new R(2, 2, 1, 2)
		        ));
			    
			    r3.addAll(Arrays.asList(
		                new R(3, 3, 4, 3),
		                new R(3, 3, 1, 3),
		                new R(3, 3, 4, 3)
		        ));
			    
			    r4.addAll(Arrays.asList(
		                new R(4, 4, 2, 4),
		                new R(4, 4, 1, 4),
		                new R(4, 4, 2, 4)
		        ));
			    r5.addAll(Arrays.asList(
		                new R(5, 5, 3, 5),
		                new R(5, 5, 3, 5),
		                new R(5, 5, 666, 5)
		        ));
			    r6.addAll(Arrays.asList(
		                new R(6, 6, 666, 6),
		                new R(6, 6, 777, 6),
		                new R(6, 6, 777, 6)
		        ));
			    r7.addAll(Arrays.asList(
		                new R(7, 7, 555, 7),
		                new R(7, 7, 4, 7),
		                new R(7, 7, 5, 7)
		        ));
			    
			    r8.addAll(Arrays.asList(
		                new R(8, 8, 2, 8),
		                new R(8, 8, 0, 8),
		                new R(8, 8, 0, 8)
		        ));
			    
			    
			    r_disk.addAll(Arrays.asList(r1,r2,r3,r4,r5,r6,r7,r8));
			    System.out.println("");
			    System.out.println("Relation R on Disk");
			    System.out.println(r_disk);
			    System.out.println("");
			    
				// no. of sublists of R
			    int numSublistR = (int) Math.ceil(r_disk.size()/mem_size);
			    
			    // get sorted sublists of R which are stored on disk after performing a main memory sort
			    ArrayList<List<List<R>>> sortedSublistsR = getSortedSublists_R(r_disk, mem_size, block_size, numSublistR);
		
			    //---------------------------------------------------------------------------------------------------------
				//Represents Relation S on disk which consists of 7 blocks B(S) = 7
				ArrayList<List<S>> s_disk = new ArrayList<List<S>>(7);

				//Represents 7 blocks of R 
				ArrayList<S> s1 = new ArrayList<S>(3);
				ArrayList<S> s2 = new ArrayList<S>(3);
				ArrayList<S> s3 = new ArrayList<S>(3);
				ArrayList<S> s4 = new ArrayList<S>(3);
				ArrayList<S> s5 = new ArrayList<S>(3);
				ArrayList<S> s6 = new ArrayList<S>(3);
				ArrayList<S> s7 = new ArrayList<S>(3);
			   
			    
			    s1.addAll(Arrays.asList(
		                new S(2, 1, 1, 1),
		                new S(1, 1, 1, 1),
		                new S(3, 1, 1, 1)
		        ));
			    
			    s2.addAll(Arrays.asList(
		                new S(1, 2, 2, 2),
		                new S(3, 2, 2, 2),
		                new S(2, 2, 2, 2)
		        ));
			    
			    s3.addAll(Arrays.asList(
		                new S(3, 3, 3, 3),
		                new S(2, 3, 3, 3),
		                new S(1, 3, 3, 3)
		        ));
			    
			    s4.addAll(Arrays.asList(
		                new S(4, 4, 4, 4),
		                new S(2, 4, 4, 4),
		                new S(3, 4, 4, 4)
		        ));
			    s5.addAll(Arrays.asList(
		                new S(888, 5, 5, 5),
		                new S(1, 5, 5, 5),
		                new S(888, 5, 5, 5)
		        ));
			    s6.addAll(Arrays.asList(
		                new S(99, 6, 6, 6),
		                new S(1, 6, 6, 6),
		                new S(0, 6, 6, 6)
		        ));
			    s7.addAll(Arrays.asList(
		                new S(5, 7, 7, 7),
		                new S(0, 7, 7, 7),
		                new S(888, 7, 7, 7)
		        ));
			    
			    s_disk.addAll(Arrays.asList(s1,s2,s3,s4,s5,s6,s7));
			    System.out.println("");
			    System.out.println("Relation S on Disk");
			    System.out.println(s_disk);
			    System.out.println("");
			    
			    // no. of sublists of S
			    int numSublistS = (int) Math.ceil(s_disk.size()/mem_size);
			    
			    // get sorted sublists of S which are stored on disk after performing a main memory sort
			    ArrayList<List<List<S>>> sortedSublistsS = getSortedSublists_S(s_disk, mem_size, block_size, numSublistS);
			   
			    
			    // memory buffers to process each sublist of R
			    List<List<R>> memR1= new ArrayList<List<R>>();
			    List<List<R>> memR2= new ArrayList<List<R>>();
			    
			    // memory buffers to process each sublist of S
			    List<List<S>> memS1 = new ArrayList<List<S>>();
			    List<List<S>> memS2 = new ArrayList<List<S>>();
			    
			    // reference to each sorted sublists of R
			    List<List<R>> subR1 = sortedSublistsR.get(0);
			    List<List<R>> subR2 = sortedSublistsR.get(1);
			    
			    // reference to each sorted sublists of S
			    List<List<S>> subS1 = sortedSublistsS.get(0);
			    List<List<S>> subS2 = sortedSublistsS.get(1);
			    
			    System.out.println("----------------------------------------------------------------------------------------------");
			    System.out.println("Sorted sublists of Relation R on Disk");
			    System.out.println("");
			    System.out.println("Sublist 1 of R");
			    System.out.println(subR1);
			    System.out.println("");
			    System.out.println("Sublist 2 of R");
			    System.out.println(subR2);
			    System.out.println("");
			    
			    System.out.println("----------------------------------------------------------------------------------------------");
			    System.out.println("Sorted sublists of Relation S on Disk");
			    System.out.println("");
			    System.out.println("Sublist 1 of S");
			    System.out.println(subS1);
			    System.out.println("");
			    System.out.println("Sublist 2 of S");
			    System.out.println(subS2);
			    System.out.println("");

			    // load the first blocks of each sublist into memory
			    load_sublistToMem_R(memR1, subR1);
			    load_sublistToMem_R(memR2, subR2);
			    load_sublistToMem_S(memS1, subS1);
			    load_sublistToMem_S(memS2, subS2);
			    
			    // separate pointers for each sublist
			    int indexR1 = 0, indexR2 = 0, indexS1 = 0, indexS2= 0;
			    int blocksProcessed_R1 = 0,blocksProcessed_R2 = 0, blocksProcessed_S1 = 0, blocksProcessed_S2 = 0;
		    	ArrayList<R> bucket_R = new ArrayList<R>(100);
		    	ArrayList<S> bucket_S = new ArrayList<S>(100);
			    
			    // to track when all the blocks in sublist are processed 
			    boolean doneR1 = false, doneR2 = false, doneS1 = false, doneS2 = false;
			    
			    // get references to the first tuple loaded into memory buffers
			    R tuple_R1 = memR1.get(blocksProcessed_R1).get(indexR1);
			    R tuple_R2 = memR2.get(blocksProcessed_R2).get(indexR2);
			    
			    S tuple_S1 = memS1.get(blocksProcessed_S1).get(indexS1);
			    S tuple_S2 = memS2.get(blocksProcessed_S2).get(indexS2);
			    
			    // store the first y value of each tuple of each memory buffer
			    int smallestR1 = tuple_R1.getY();
			    int smallestR2 = tuple_R2.getY();
			    
			    int smallestS1 = tuple_S1.getY();
			    int smallestS2 = tuple_S2.getY();
			    
			    // find the first smallest Y among all the y 
			    int smallest = Math.min(Math.min(smallestR1, smallestR2), Math.min(smallestS1, smallestS2));
		    	
		    	//output buffer used to stored joined result
		    	ArrayList<Result> output_buffer = new ArrayList<Result>(3);
		    	// disk represents final output of the sort-join join on disk
		    	ArrayList<List<Result>> disk = new ArrayList<List<Result>>();
			    
		    	// loop as long as any one of the relations is completely processed and all its sublist are empty
			    while(!(subR1.isEmpty() && subR2.isEmpty()) || !(subS1.isEmpty() && subS2.isEmpty())) {

			    	// Processing of Memory Buffer 1 and R sublist1
			    	while((!(subR1.isEmpty()) || blocksProcessed_R1 < memR1.size()))
				    {	
			    			//if smallest == current_y and it is the last element of the block then load next block 
					    	if(smallest == tuple_R1.getY() && indexR1 == block_size -1)
					    	{
					    		bucket_R.add(tuple_R1);
					    		load_sublistToMem_R(memR1, subR1);
					    		blocksProcessed_R1 = blocksProcessed_R1+1;
					    		indexR1=0;
					    	}
					    	//if smallest == current y but not last element in block then increment counter and add to bucket
					    	else if(tuple_R1.getY() == smallest) {
					    		bucket_R.add(tuple_R1);
					    		indexR1++;
					    	}
					    	//if current y > smallest stop, break out of loop
					    	else if(tuple_R1.getY() > smallest) 
					    	{
					    		smallestR1 = memR1.get(blocksProcessed_R1).get(indexR1).getY();
					    		break;
					    	}
					    	
					    	//check is the sublist is completely processed or not 
					    	if (blocksProcessed_R1 < memR1.size())
					    	{
					    		tuple_R1 = memR1.get(blocksProcessed_R1).get(indexR1);
					    	}
					    	else
					    	{
					    		doneR1 = true;
					    	}
				    }
			    	
			    	// Processing of Memory Buffer 2 and R sublist2 
			    	while((!(subR2.isEmpty()) || blocksProcessed_R2 < memR2.size()))
				    {	
			    			//if smallest == current_y and it is the last element of the block then load next block 
					    	if(smallest == tuple_R2.getY() && indexR2 == block_size -1)
					    	{
					    		bucket_R.add(tuple_R2);
					    		load_sublistToMem_R(memR2, subR2);
					    		blocksProcessed_R2 = blocksProcessed_R2+1;
					    		indexR2=0;
					    	}
					    	//if smallest == current y but not last element in block then increment counter and add to bucket
					    	else if(tuple_R2.getY() == smallest) {
					    		bucket_R.add(tuple_R2);
					    		indexR2++;
					    	}
					    	//if current y > smallest stop, break out of loop
					    	else if(tuple_R2.getY() > smallest) 
					    	{
					    		smallestR2 = memR2.get(blocksProcessed_R2).get(indexR2).getY();
					    		break;
					    	}
					    	
					    	//check is the sublist is completely processed or not 
					    	if (blocksProcessed_R2 < memR2.size())
					    	{
					    		tuple_R2 = memR2.get(blocksProcessed_R2).get(indexR2);
					    	}
					    	else
					    	{
					    		doneR2 = true;
					    	}
				    }
			    	
			    	// Processing of Memory Buffer 1 and s sublist1
			    	while((!(subS1.isEmpty()) || blocksProcessed_S1 < memS1.size()))
				    {	
			    			//if smallest == current_y and it is the last element of the block then load next block 
					    	if(smallest == tuple_S1.getY() && indexS1 == block_size -1)
					    	{
					    		bucket_S.add(tuple_S1);
					    		load_sublistToMem_S(memS1, subS1);
					    		blocksProcessed_S1 = blocksProcessed_S1+1;
					    		indexS1=0;
					    	}
					    	//if smallest == current y but not last element in block then increment counter and add to bucket
					    	else if(tuple_S1.getY() == smallest) {
					    		bucket_S.add(tuple_S1);
					    		indexS1++;
					    	}
					    	//if current y > smallest stop, break out of loop
					    	else if(tuple_S1.getY() > smallest) 
					    	{
					    		smallestS1 = memS1.get(blocksProcessed_S1).get(indexS1).getY();
					    		break;
					    	}
					    	
					    	//check is the sublist is completely processed or not 
					    	if (blocksProcessed_S1 < memS1.size())
					    	{
					    		tuple_S1 = memS1.get(blocksProcessed_S1).get(indexS1);
					    	}
					    	else
					    	{
					    		doneS1=true;
					    	}
				    }
			    	
			    	// Processing of Memory Buffer 2 and s sublist2
			    	while((!(subS2.isEmpty()) || blocksProcessed_S2 < memS2.size()))
				    {	
			    			//if smallest == current_y and it is the last element of the block then load next block 
					    	if(smallest == tuple_S2.getY() && indexS2 == block_size -1)
					    	{
					    		bucket_S.add(tuple_S2);
					    		load_sublistToMem_S(memS2, subS2);
					    		blocksProcessed_S2 = blocksProcessed_S2+1;
					    		indexS2=0;
					    	}
					    	//if smallest == current y but not last element in block then increment counter and add to bucket
					    	else if(tuple_S2.getY() == smallest) {
					    		bucket_S.add(tuple_S2);
					    		indexS2++;
					    	}
					    	//if current y > smallest stop, break out of loop
					    	else if(tuple_S2.getY() > smallest) 
					    	{
					    		smallestS2 = memS2.get(blocksProcessed_S2).get(indexS2).getY();
					    		break;
					    	}
					    	
					    	//check is the sublist is completely processed or not 
					    	if (blocksProcessed_S2 < memS2.size())
					    	{
					    		tuple_S2 = memS2.get(blocksProcessed_S2).get(indexS2);
					    	}
					    	else
					    	{
					    		doneS2 =true;
					    	}
				    }
			    	
			    	System.out.println("*********************************************************************************************************************************");
			    	System.out.println("Smallest Y-value:"+ smallest);
			    	System.out.println();
			    	System.out.println("Matching tuples of R");
			    	System.out.println(bucket_R);
			    	System.out.println("Matching tuples of S");
			    	System.out.println(bucket_S);
			    	System.out.println("----------------------------------------------------------------------------------------------");
			    	
			    	String outBuffer_status = (bucket_R.isEmpty() || bucket_S.isEmpty()) ? "Empty" : "";
			    	System.out.println("Output buffer contents: "+ outBuffer_status);
			    	//join R & S put the result into output buffer 
			    	for(R r_tuple : bucket_R) {
			    		for(S s_tuple : bucket_S) {
			    			
			    			if(output_buffer.size() == block_size)
			    			{
			    				System.out.println(output_buffer);
			    				ArrayList<Result> temp = new ArrayList<Result>(output_buffer);
			    				disk.add(temp);
			    				output_buffer.clear();
			    			}
			    			Result res= new Result(r_tuple, s_tuple);
			    			output_buffer.add(res);
			    		}
			    	}
			    	System.out.println("----------------------------------------------------------------------------------------------");
			    	System.out.println("Disk contents: ");
			    	System.out.println(disk);
			    	System.out.println();
			    	
			    	// if any of the sublist done variable is set to true then no need consider the smallest from that sublist
			    	// else consider smallest of the sublist for next run
			    	smallestR1 = doneR1 ? 9999 : smallestR1;
			    	smallestR2 = doneR2 ? 9999 : smallestR2;
			    	
			    	smallestS1 = doneS1 ? 9999 : smallestS1;
			    	smallestS2 = doneS2 ? 9999 : smallestS2;
			    	
			    	//find the next smallest y
			    	smallest = Math.min(Math.min(smallestR1, smallestR2), Math.min(smallestS1, smallestS2));
			    	bucket_R.clear();
			    	bucket_S.clear();
			    }
			    // add any remaining tuples in output buffer to disk
			    disk.add(output_buffer);
			    System.out.println("*********************************************************************************************************************************");
			    System.out.println("Final Disk Result");
			    System.out.println();
			    System.out.println(disk);
			    System.out.println();
			    
		}
}
