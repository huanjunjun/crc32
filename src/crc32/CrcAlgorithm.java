package crc32;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;
import org.junit.Test;

public class CrcAlgorithm {
	public static void main(String[] args) throws IOException  {    
		
        File file = new File("D:\\crc.txt");//����һ��file����������ʼ��FileReader
        FileReader reader = new FileReader(file);//����һ��fileReader����������ʼ��BufferedReader
        BufferedReader bReader = new BufferedReader(reader);//newһ��BufferedReader���󣬽��ļ����ݶ�ȡ������
        StringBuilder sb = new StringBuilder();//����һ���ַ������棬���ַ�����Ż�����
        String s = "";
        while ((s =bReader.readLine()) != null) {//���ж�ȡ�ļ����ݣ�����ȡ���з���ĩβ�Ŀո�
            sb.append(s + "\n");//����ȡ���ַ�����ӻ��з����ۼӴ���ڻ�����
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
	        //��ȡ����ʽλ��
	        int gxStrLen = gxStr.length();
	        //�������Ƶ��ַ�����Ϊ����
	        int data =bittoint(dataStr);
	        //������ʽ���ַ�����Ϊ����
	        int  gx =bittoint(gxStr);
	        //���ԭʼ���ݲ�������λ��
	        int sum = dataStrLen+gxStrLen-1;
	        //����2��sum-1����
	        BigInteger bi = new BigInteger("2");
	        //��2��sum-1����ת��Ϊint��
	        int flag = bi.pow(sum-1).intValue();
	        //ԭʼ֡��λ����
	        data = data<<(gxStrLen-1);
	        //����ʽ��λ����,ʹ���벹����֡λ��һ�£��Ա����
	        gx = gx<<(dataStrLen-1);
	        //ѭ��dataStrLen��
	        for(int i=0; i<(dataStrLen);i++){
	            //�жϲ�����֡���λΪ1������
	            if((data&flag)!=0) {
	                data = data^gx;
	                gx = gx>>1;
	            }else {
	                gx = gx>>1;
	            }
	            //flag���λ��1����
	            flag = flag>>1;
	        }
	        String crc = Integer.toBinaryString(data);
	        //���Java���������ʱ��ȥ��λ�������
	        while(crc.length()<(gxStrLen-1)) {
	            crc = "0"+crc;
	        }
	        print("������:"+crc);
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
