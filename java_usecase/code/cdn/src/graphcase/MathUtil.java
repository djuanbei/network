package graphcase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MathUtil {

	/** 生成生成[0,max)或者[min,max]范围内的正整数。 nextInt(max)生成[0,max)之间的数 */
	public static int randomMinMaxInt(int min, int max) {
		Random random = new Random();
		int s = random.nextInt(max) % (max - min + 1) + min;
		return s;
	}
	/** 生成生成List随机数*/
	public static List<Integer> randomMinMaxList(int min, int max,int n) {
		List<Integer> randomList = new ArrayList<Integer>();
		for (int i = 0; i < n; i++) {
			int random = randomMinMaxInt(min, max);
			randomList.add(random);
		}
		return randomList;
	}

	/** 求一个数的组合：Cnk */
	public static int combinateNum(int n, int k) {
		int molecular_take = 1;// 分子乘积
		int denominator_take = 1;// 分母乘积
		for (int i = 0; i < k; i++) {
			molecular_take = molecular_take * (n - i);
			denominator_take = denominator_take * (k - i);

		}
		int result = molecular_take / denominator_take;
		return result;
	}

	
	
	/** 
     * 读取文件并按行输出
     * @param filePath
     * @param spec 允许解析的最大行数， spec==null时，解析所有行
     * @return
     * @author
     * @since 2016-3-1
     */
    public static String[] read(final String filePath, final Integer spec)
    {
        File file = new File(filePath);
        // 当文件不存在或者不可读时
        if ((!isFileExists(file)) || (!file.canRead()))
        {
            System.out.println("file [" + filePath + "] is not exist or cannot read!!!");
            return null;
        }
        
        List<String> lines = new LinkedList<String>();
        BufferedReader br = null;
        FileReader fb = null;
        try
        {
            fb = new FileReader(file);
            br = new BufferedReader(fb);

            String str = null;
            int index = 0;
            while (((spec == null) || index++ < spec) && (str = br.readLine()) != null)
            {
                lines.add(str);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeQuietly(br);
            closeQuietly(fb);
        }

        return lines.toArray(new String[lines.size()]);
    }
    /** 
     * 写文件
     * @param filePath 输出文件路径
     * @param content 要写入的内容
     * @param append 是否追加
     * @return
     * @author s00274007
     * @since 2016-3-1
     */
    public static int write(final String filePath, final String[] contents, final boolean append)
    {
        File file = new File(filePath);
        if (contents == null)
        {
            System.out.println("file [" + filePath + "] invalid!!!");
            return 0;
        }

        // 当文件存在但不可写时
        if (isFileExists(file) && (!file.canRead()))
        {
            return 0;
        }

        FileWriter fw = null;
        BufferedWriter bw = null;
        try
        {
            if (!isFileExists(file))
            {
                file.createNewFile();
            }

            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            for (String content : contents)
            {
                if (content == null)
                {
                    continue;
                }
                bw.write(content);
                bw.newLine();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            closeQuietly(bw);
            closeQuietly(fw);
        }

        return 1;
    }

    private static void closeQuietly(Closeable closeable)
    {
        try
        {
            if (closeable != null)
            {
                closeable.close();
            }
        }
        catch (IOException e)
        {
        }
    }

    private static boolean isFileExists(final File file)
    {
        if (file.exists() && file.isFile())
        {
            return true;
        }

        return false;
    }
	
}
