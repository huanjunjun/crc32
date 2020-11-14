package crc32;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;
import org.junit.Test;

public class CrcAlgorithm {
	public static void main(String[] args) throws IOException  {    
		
        File file = new File("D:\\crc.txt");//定义一个file对象，用来初始化FileReader
        FileReader reader = new FileReader(file);//定义一个fileReader对象，用来初始化BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//new一个BufferedReader对象，将文件内容读取到缓存
        StringBuilder sb = new StringBuilder();//定义一个字符串缓存，将字符串存放缓存中
        String s = "";
        while ((s =bReader.readLine()) != null) {//逐行读取文件内容，不读取换行符和末尾的空格
            sb.append(s + "\n");//将读取的字符串添加换行符后累加存放在缓存中
           // System.out.println(s);
        }
        bReader.close();
        String str = sb.toString();
      //  System.out.println(str );
        int str_length=str.length();
        int index_left=0;
        int index_right=0;
        
        String dataStr="";
        
        String gxStr="100000100100000010001110110110111";//100000100100000010001110110110111
        
        while(index_right<str_length)
        {
        	while(index_right<str_length && (index_right-index_left)<46)
        	{
        		index_right++;
        	}
        	dataStr=str.substring(index_left,index_right);
        	if(dataStr.length()<46)
        	{
        		dataStr=dataStr.trim();
        		int datastr_length=dataStr.length();
        		for(int i=0;i<(46-datastr_length);i++)
        		{
        			dataStr+='0';
        		}
        	}
        //	System.out.println(dataStr);
        	crcdomain(dataStr, gxStr);
        	index_left=index_right;
        }
        
        
        
        
        
    
     // crcdomain(dataStr,gxStr);
    }

	private static void print(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}
	public static void crcdomain(String dataStr,String gxStr) {
		 int dataStrLen = dataStr.length();
	        //获取多项式位数
	        int gxStrLen = gxStr.length();
	        //将二进制的字符串变为整型
	        int data =bittoint(dataStr);
	        //将多项式的字符串变为整型
	        int  gx =bittoint(gxStr);
	        //算出原始数据补零后的总位数
	        int sum = dataStrLen+gxStrLen-1;
	        //计算2的sum-1次幂
	        BigInteger bi = new BigInteger("2");
	        //将2的sum-1次幂转换为int型
	        int flag = bi.pow(sum-1).intValue();
	        //原始帧低位补零
	        data = data<<(gxStrLen-1);
	        //多项式低位补零,使其与补零后的帧位数一致，以便异或
	        gx = gx<<(dataStrLen-1);
	        //循环dataStrLen次
	        for(int i=0; i<(dataStrLen);i++){
	            //判断补零后的帧最高位为1还是零
	            if((data&flag)!=0) {
	                data = data^gx;
	                gx = gx>>1;
	            }else {
	                gx = gx>>1;
	            }
	            //flag最高位的1右移
	            flag = flag>>1;
	        }
	        String crc = Integer.toBinaryString(data);
	        //解决Java输出二进制时略去高位零的问题
	        while(crc.length()<(gxStrLen-1)) {
	            crc = "0"+crc;
	        }
	        print("冗余码:"+crc);
	}
	@Test
	public static int  bittoint(String str)
	{
		
		int str_length=str.length();
		int res=0;
		for(int i=0;i<str_length;i++)
		{
			res*=2;
			if(str.charAt(i)=='1')res+=1;
		}
		return res;
	}
}
