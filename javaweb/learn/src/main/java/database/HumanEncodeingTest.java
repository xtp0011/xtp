package database;

public class HumanEncodeingTest {
    public static void main(String[] args) {
        HumanEncodeing humanEncodeing = new HumanEncodeing();
        String msg = "中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中中";
        byte[] bytes = msg.getBytes();
        System.out.println("压缩前的数据大小 >>>>>>>>>："+bytes.length);
        byte[] huffmanZip = humanEncodeing.huffmanZip(bytes);
        System.out.println("压缩后的数据大小 >>>>>>>>>："+huffmanZip.length);
        String decode = humanEncodeing.decode(huffmanZip);
        System.out.println(decode);
    }
}
