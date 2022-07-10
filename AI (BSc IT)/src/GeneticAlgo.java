
import java.util.*;
import java.math.*;
import java.io.File;
import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFRow.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook.*;

import com.microsoft.schemas.office.visio.x2012.main.CellType;
public class GeneticAlgo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
	
	//	System.out.println("Enter the no. of Array ");
		//int no_of_array=sc.nextInt();
		try
		{
		String f="ai.xlsx";
		FileInputStream fis = new FileInputStream(new File(f));
		Workbook wb = new XSSFWorkbook(fis);
		Sheet firstsheet = wb.getSheetAt(0);
		
		FormulaEvaluator fe = wb.getCreationHelper().createFormulaEvaluator();
		int rows = firstsheet.getPhysicalNumberOfRows();
		//System.out.println("no. of Rows = "+rows);
		int colm = firstsheet.getRow(0).getPhysicalNumberOfCells();
		//System.out.println("colm = "+colm);
		
		float jarr[][] = new float[rows-1][];
		
		int rowcount=0;
		int colmcount=0;
		int i=0,j=0;
		for(org.apache.poi.ss.usermodel.Row row : firstsheet)
		{	float arr[] = new float[colm-1];
		j=0;
		colmcount=0;
			for(org.apache.poi.ss.usermodel.Cell cell : row)
			{		
				if(rowcount>0)
				{
					if(colmcount>0)
					{
					arr[j]=(float) cell.getNumericCellValue();
					j++;
					if(arr.length==j)
						break;
				    }
				
				}
				colmcount++;
			}
			
			if(i<jarr.length&&rowcount!=0)
			{jarr[i]=arr;
			i++;
			}
			rowcount++;
		}
		int no_of_array=rows-1;
		if((no_of_array)==1)
		{
			System.out.println("With 1 generation , crossover not possible , system exiting");
			System.exit(0);
		}
			//float[][] jarr = new float[no_of_array][];
		//System.out.println("Enter the no. of elements in array");
		//int l=sc.nextInt();
			int l=colm-1;
	/*	for(int i=0;i<no_of_array;i++)
		{
			System.out.print("Enter the elements for array : "+(i+1));
			System.out.println();
			float arr[] = new float[l]; 
			for(int j=0;j<l;j++)
			{
				arr[j]=sc.nextFloat();
			}
			jarr[i]=arr;
		}
	*/
	
		float[] farr = avg(jarr); //orig avgs
		float[] origarray = farr;
		System.out.println("\t\t\t\tPrinting of orignal arrays\n\n");
		display(jarr);
	System.out.println("\t\t\t\tBefore sorting avgs of the arrays \n");
		disparray(farr);
		float[] sortarr = sort(avg(jarr)); //sorted avgs
	System.out.println("\t\t\t\tAfter sorting in order of highest avgs\n");	
		disparray(sortarr);
	float[][] sortedjarray = sortjarr(jarr,origarray,sortarr);
	System.out.println("\t\t\t\tArrays after sorting in order of highest avgs\n");	
	display(sortedjarray);
	float[][] aftercrossover = mutation(crossover(sortedjarray,sortedjarray[0]));
	//System.out.println("Printing of arrays after crossover and mutation performed");

	//display(aftercrossover);
	
	
	//display(mutation(aftercrossover));
	float[] mutavg = avg(aftercrossover);
	System.out.println("\t\t\t\tAvgs after mutation\n");
	disparray(mutavg);
	int loc = findloc(mutavg);
	float max=getmax(mutavg);
	System.out.println("\t\t\t\tArray with max avg = "+max+"\n");
	
	disparray(aftercrossover[loc]);
	
}
	
	catch(Exception e)
	{
		e.printStackTrace();
	}

}//End of main
	static float[] avg(float[][] arr)
{
	int l = arr.length;
	float arrf[] = new float[l];
	for(int i=0;i<l;i++)
	{
		float iarr[] = new float  [arr[i].length];
		iarr=arr[i];
		float sum=0;
		for(int j=0;j<arr[i].length;j++)
		{
			sum=sum+iarr[j];
		}
		float avg = sum/(arr[i].length);
		arrf[i]=avg;
	
	}
	
	return arrf;
}

static void display(float[][] jarr)
{

	for(int i=0;i<jarr.length;i++)
	{
		float arr[] = new float[jarr[i].length];
		arr=jarr[i];
		System.out.println(" \t\t\t\tArray "+(i+1));
		System.out.println("\n**************************************************************************************************************\n\n");
		for(int j=0;j<arr.length;j++)
		{
			System.out.print(arr[j]+"    ");
			
		}
		System.out.println("\n**************************************************************************************************************\n\n");
		System.out.println("\n");
	}
}

static float[] sort(float[] arr)
{
	float temp;
	for(int i=0;i<arr.length;i++)
	{
		for(int j=0;j<arr.length;j++)
		{
			if(arr[i]>arr[j])
			{
				temp = arr[j];//big
				arr[j]=arr[i];
				arr[i]=temp;
			}
		}
	}
	return arr;
}

static void disparray(float[] arr)
{
	for(int i=0;i<arr.length;i++)
	{
		System.out.print(arr[i]+"    ");
	}
	System.out.println("\n*********************************************************************************************************************\n\n");
	System.out.println("\n");
}

static float[][] sortjarr(float[][] jarr , float[] befsort , float[] aftsort)
{
	int i=0,j=0,k=0;
	for(i=0;i<jarr.length;i++)
	{
		for(j=i;j<befsort.length;j++)
		{
			for(k=0;k<befsort.length;k++)
			{
				//System.out.println("k = "+k);
				if(befsort[k]==aftsort[j])
				{
				
				float temp=befsort[j];
				befsort[j]=befsort[k];
				befsort[k]=temp;
			
				break;
				}
				
			}
		//	System.out.println("k = "+k);
			break;
		}
	//	System.out.println("k = "+k);
	//	System.out.println("j = "+j);
	//	System.out.println("length of befsort = "+befsort.length);
	//	System.out.println("length of aftsort = "+aftsort.length);
		float[] temparr = new float[befsort.length];
		temparr=jarr[k];
		jarr[k]=jarr[j];
		jarr[j]=temparr;
	
		
	
	}

	return jarr;
}
static float[][] crossover(float jarr[][],float firstarr[])
{
	float newjarr[][] = new float[jarr.length][];
	float tempjarr[][] = new float[jarr.length][];
	tempjarr=jarr;
	Random r = new Random();
	
	float[] temparray = new float[jarr[0].length];
	for(int z=0;z<jarr.length;z++)
	{
		newjarr[z]=temparray;
	}
	
	
float[] array = new float[jarr[0].length];
	for(int i=0;i<array.length;i++)
	{
		array[i]=firstarr[i];
	}

	

	int var =0;
	if((jarr.length)%2==0)
	{
	for(int i=(jarr.length)-1;i>1;i--)
	{
		if(jarr.length%i==0)
		{		var=i;
			break;
		}
	}
	}
	
	else
	{
		for(int i=(jarr.length);i>1;i--)
		{
			if(jarr.length%i==0)
			{		var=i;
				break;
			}
		}
	}
	
	//System.out.println("var = "+var);
	int m=0;
	for(int i=1;i<jarr.length-(jarr.length-var-1);i++)
	{
		if(m>=jarr.length)
		{	break;
		}
		
		float[] f1 = new float[jarr[0].length];
		for(int z=0;z<jarr[0].length;z++)
		{
			f1[z]=array[z];
		}
	
		int n = r.nextInt(jarr[0].length-1);
		if(n==0)
			n=1;
		System.out.println("\n*********************************************************************************************************************\n\n");
		System.out.println("\t\t\t\tCrossover Point : "+n);
		System.out.println("\n*********************************************************************************************************************\n\n");
			for(int k=n;k<jarr[0].length;k++)
			{
				
				float temp1 ,temp2;
				temp1=f1[k]; //5
				temp2=(tempjarr[i])[k];//8
				
				f1[k]=temp2;
				tempjarr[i][k]=temp1;
			}
			
			//System.out.println(" m  = "+m);
			

		
			newjarr[m]=f1;
			m++;
		
			newjarr[m]=tempjarr[i];
			m++;		
	}
	System.out.println("****************************************************************************************************************************\n\n");
	System.out.println("\t\t\t\tAfter Crossover");
	System.out.println("****************************************************************************************************************************\n\n");
	display(newjarr);
	return newjarr;
}

static  float[][] mutation (float[][] aftercross)
{
	Random ra = new Random();
int cntr=0;
	float mutarray[] = new float[aftercross[0].length];
	for(int i=0;i<aftercross.length;i++)
	{
		System.out.println("****************************************************************************************************************************\n\n");
		System.out.println("\t\t\t\tArray : "+(i+1));
		mutarray=aftercross[i];
	  int rand =ra.nextInt(2);		
		//System.out.println("rand : "+rand);
		
	 
	if(rand==1){
		System.out.println("\t\t\t\tPerform mutation");
		cntr++;
	int rand1 = ra.nextInt(aftercross[0].length);
		System.out.println("\t\t\t\tMutaion at location : "+rand1); 
		//System.out.println("*****************************************************************************************************************************\n\n");
			  float value = mutarray[rand1];
			  mutarray[rand1]=(float) ((value)+(value*0.05));
		  
		  
	  aftercross[i]=mutarray;
	  
	}
	else
	
		System.out.println("\t\t\t\tNo Mutation");
	
	}
	
	double prob = (double)cntr/aftercross.length;
	System.out.println("****************************************************************************************************************************\n\n");
	System.out.println("\t\t\t\tProbablity of mutation : "+prob+"\n");
	System.out.println("****************************************************************************************************************************\n\n");
	
	System.out.println("\t\t\t\tAfter Mutation");
	System.out.println("****************************************************************************************************************************\n\n");
	display(aftercross);
	return aftercross;
	
}

static int findloc(float[] arr)
{
	float max = arr[0];
	int c=0;
	for(int i=0;i<arr.length;i++)
	{
		if(max<arr[i])
		{
			max=arr[i];
		}
		
	}

	for(int i=0;i<arr.length;i++)
	{
		if(max==arr[i])
		{
			break;
		}
		else
			c++;
	}
	
	return c;
}

static float getmax(float[] arr)
{
	float max = arr[0];
	int c=0;
	for(int i=0;i<arr.length;i++)
	{
		if(max<arr[i])
		{
			max=arr[i];
		}
		
	}
return max;
}

}




